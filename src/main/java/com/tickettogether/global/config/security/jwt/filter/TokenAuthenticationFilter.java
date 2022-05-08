package com.tickettogether.global.config.security.jwt.filter;

import com.tickettogether.global.config.redis.util.RedisUtil;
import com.tickettogether.global.config.security.CustomUserDetailsService;
import com.tickettogether.global.config.security.exception.TokenValidFailedException;
import com.tickettogether.global.config.security.jwt.token.AuthTokenProvider;
import com.tickettogether.global.config.security.utils.HeaderUtil;
import com.tickettogether.global.config.security.jwt.token.AuthToken;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
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
    private final CustomUserDetailsService customUserDetailsService;
    private final RedisUtil<String, String> redisUtil;
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        Assert.notNull(request, "request cannot be null");

        String accessToken = HeaderUtil.getAccessToken(request);
        if(accessToken != null){
            if(redisUtil.hasKeyBlackList(accessToken)){
                throw new TokenValidFailedException("access token in blacklist");
            }

            AuthToken authToken = authTokenProvider.convertToAuthToken(accessToken);

            if(authToken.validate() && isEqualToUser(authToken)) {
                Authentication authentication = authTokenProvider.getAuthentication(authToken);
                SecurityContextHolder.getContext().setAuthentication(authentication);
            }
        }
        filterChain.doFilter(request, response);
    }

    private boolean isEqualToUser(AuthToken authToken){
        String userEmail = authToken.getTokenClaims().getSubject();
        try {
            UserDetails user = customUserDetailsService.loadUserByUsername(userEmail);
            return user != null;
        }catch (UsernameNotFoundException e){
            e.printStackTrace();
        }
        return false;
    }
}
