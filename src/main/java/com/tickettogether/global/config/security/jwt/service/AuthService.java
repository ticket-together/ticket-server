package com.tickettogether.global.config.security.jwt.service;

import com.tickettogether.domain.member.domain.Role;
import com.tickettogether.global.config.redis.util.RedisUtil;
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
public class AuthService {
    private final RedisUtil<String, String> redisUtil;
    private final JwtConfig jwtConfig;
    private final AuthTokenProvider authTokenProvider;
    private final static long THREE_DAYS_MSEC = 259200000;
    private AuthToken newRefreshToken;
    private AuthToken newAccessToken;
    private String userId;
    private Role userRole;
    private long refreshExpiry;

    public void validateAccessToken(AuthToken authToken){
        if(authToken.validate()){        //valid
            throw new TokenValidFailedException();
        }

        Claims claims = authToken.getExpiredTokenClaims();
        if(claims == null){              //expired
            throw new TokenValidFailedException("Access Token must be expired");
        }
        this.userId = claims.getId();
        this.userRole =  Role.of(claims.get("role", String.class));
    }

    public boolean validateRefreshToken(AuthToken refreshAuthToken, String refreshToken, String accessToken){
        if(refreshAuthToken.getRefreshTokenClaims(accessToken) == null){
            throw new TokenRefreshException();    //expired
        }

        List<String> values = redisUtil.getValues(refreshToken);
        if(values != null) {
            if(values.get(0) == null || !StringUtils.equals(values.get(1),accessToken) ){
                throw new TokenRefreshException("Not equal to userId and access token");
            }
        }else throw new TokenRefreshException("No Data in Redis Server");
        return true;
    }

    public void renewAccessTokenAndRefreshToken(AuthToken authToken, String refreshToken, String accessToken){
        this.newAccessToken = authTokenProvider.createAuthToken(userId, userRole.getKey());   //액세스 토큰 재발급
        redisUtil.deleteValue(refreshToken);

        if (authToken.getRemainMilliSeconds(accessToken) < THREE_DAYS_MSEC){   //3일 이하로 남으면 리프레시 재발급
            this.refreshExpiry = Long.parseLong(jwtConfig.getRefreshExpiry());
            this.newRefreshToken = authTokenProvider.createRefreshToken(userId,
                    newAccessToken.getToken(),
                    refreshExpiry);

            setRefreshValue(newRefreshToken.getToken(), userId, this.newAccessToken.getToken());
        }
        setRefreshValue(refreshToken, userId, this.newAccessToken.getToken());
    }

    public void logoutAndDeleteToken(String accessToken, String refreshToken){
        AuthToken authToken = authTokenProvider.convertToAuthToken(accessToken);
        if(!authToken.validate()){
            throw new TokenValidFailedException();
        }

        AuthToken refreshAuthToken = authTokenProvider.convertToAuthToken(refreshToken);
        if(!validateRefreshToken(refreshAuthToken, refreshToken, accessToken)){
            throw new TokenRefreshException();
        }
        redisUtil.deleteValue(refreshToken);
        redisUtil.setValueBlackList(accessToken, "access_token", authToken.getRemainMilliSeconds());
    }

    private void setRefreshValue(String key, String... values){
        List<String> refreshValueList = new ArrayList<>(Arrays.asList(values));
        redisUtil.setValue(key, refreshValueList);
    }
}
