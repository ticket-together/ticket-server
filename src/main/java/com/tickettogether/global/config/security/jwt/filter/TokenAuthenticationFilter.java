package com.tickettogether.global.config.security.jwt.filter;

import com.tickettogether.global.config.redis.util.RedisUtil;
import com.tickettogether.global.config.security.exception.TokenBlackListException;
import com.tickettogether.global.config.security.exception.TokenExpiredException;
import com.tickettogether.global.config.security.exception.TokenRefreshException;
import com.tickettogether.global.config.security.exception.TokenValidFailedException;
import com.tickettogether.global.config.security.jwt.token.AuthTokenProvider;
import com.tickettogether.global.config.security.utils.HeaderUtil;
import com.tickettogether.global.config.security.jwt.token.AuthToken;
import io.jsonwebtoken.ExpiredJwtException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.util.Assert;
import org.springframework.web.filter.OncePerRequestFilter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Slf4j
@RequiredArgsConstructor
public class TokenAuthenticationFilter extends OncePerRequestFilter {
    private final AuthTokenProvider authTokenProvider;
    private final RedisUtil<String, String> redisUtil;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException, TokenRefreshException {
        Assert.notNull(request, "request cannot be null");
        String accessToken = HeaderUtil.getAccessToken(request);

        if (redisUtil.hasKeyBlackList(accessToken)) {
            throw new TokenBlackListException();
        }

        AuthToken authToken = authTokenProvider.convertToAuthToken(accessToken);

        try {
            authToken.parseClaims();
            Authentication authentication = authTokenProvider.getAuthentication(authToken);
            SecurityContextHolder.getContext().setAuthentication(authentication);
        } catch (ExpiredJwtException e) {
            log.info(e.getMessage());
            throw new TokenExpiredException();
        } catch (Exception e) {
            log.info(e.getMessage());
            throw new TokenValidFailedException();
        }

        filterChain.doFilter(request, response);
    }
}
