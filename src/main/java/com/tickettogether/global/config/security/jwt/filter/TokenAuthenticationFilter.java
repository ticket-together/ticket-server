package com.tickettogether.global.config.security.jwt.filter;

import com.tickettogether.global.config.security.jwt.token.AuthTokenProvider;
import com.tickettogether.global.config.security.jwt.HeaderUtil;
import com.tickettogether.global.config.security.jwt.token.AuthToken;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.util.Assert;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@RequiredArgsConstructor
public class TokenAuthenticationFilter extends OncePerRequestFilter {
    private final AuthTokenProvider authTokenProvider;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        Assert.notNull(request, "request cannot be null");

        //토큰 가져오기
        String accessToken = HeaderUtil.getAccessToken(request);
        if(accessToken != null){
            AuthToken authToken = authTokenProvider.convertToAuthToken(accessToken);

            if(authToken.validate()) {
                Authentication authentication = authTokenProvider.getAuthentication(authToken);
                SecurityContextHolder.getContext().setAuthentication(authentication);
                return;
            }
        }
        filterChain.doFilter(request, response);
    }
}
