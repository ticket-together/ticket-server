package com.tickettogether.global.config.security.jwt.service;

import com.tickettogether.domain.member.domain.Role;
import com.tickettogether.global.config.redis.service.RedisService;
import com.tickettogether.global.config.security.exception.TokenRefreshException;
import com.tickettogether.global.config.security.exception.TokenValidFailedException;
import com.tickettogether.global.config.security.jwt.JwtConfig;
import com.tickettogether.global.config.security.jwt.token.AuthToken;
import com.tickettogether.global.config.security.jwt.token.AuthTokenProvider;
import io.jsonwebtoken.Claims;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.binary.StringUtils;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
@RequiredArgsConstructor
@Getter
@Slf4j
public class RefreshService {
    private final RedisService<String, String> redisService;
    private final JwtConfig jwtConfig;
    private final AuthTokenProvider authTokenProvider;
    private final static long THREE_DAYS_MSEC = 259200000;
    private AuthToken newRefreshToken;
    private AuthToken newAccessToken;
    private String userEmail;
    private Role userRole;
    private long refreshExpiry;

    public void validateAccessToken(AuthToken authToken){
        if(authToken.validate()){        //expired
            throw new TokenValidFailedException();
        }

        Claims claims = authToken.getExpiredTokenClaims();
        if(claims == null){              //access token 만료가 안되거나 유효하지 않은 토큰인 경우
            throw new TokenValidFailedException("Access Token must be expired");
        }
        this.userEmail = claims.getId();
        this.userRole =  Role.of(claims.get("role", String.class));
    }

    public void validateRefreshToken(AuthToken refreshAuthToken, String refreshToken, String accessToken){
        if(refreshAuthToken.getRefreshTokenClaims(accessToken) == null){
            throw new TokenRefreshException();    //expired
        }

        List<String> values = redisService.getValues(refreshToken);
        if(values != null) {
            if(values.get(0) == null || !StringUtils.equals(values.get(1),accessToken) ){
                throw new TokenRefreshException("Not equal to user email and access token");
            }
        }else throw new TokenRefreshException("No Data in Redis Server");
    }

    public void renewAccessTokenAndRefreshToken(AuthToken authToken, String refreshToken, String accessToken){
        this.newAccessToken = authTokenProvider.createAuthToken(userEmail, userRole.getKey());   //액세스 토큰 재발급
        redisService.deleteValue(refreshToken);

        if (authToken.getRemainMilliSeconds(accessToken) < THREE_DAYS_MSEC){   //3일 이하로 남으면 리프레시 재발급
            this.refreshExpiry = Long.parseLong(jwtConfig.getRefreshExpiry());
            this.newRefreshToken = authTokenProvider.createRefreshToken(userEmail,
                    newAccessToken.getToken(),
                    refreshExpiry);

            setRefreshValue(newRefreshToken.getToken(), userEmail, this.newAccessToken.getToken());
        }
        setRefreshValue(refreshToken, userEmail, this.newAccessToken.getToken());
    }

    private void setRefreshValue(String key, String... values){
        List<String> refreshValueList = new ArrayList<>(Arrays.asList(values));
        redisService.setValue(key, refreshValueList);
    }
}
