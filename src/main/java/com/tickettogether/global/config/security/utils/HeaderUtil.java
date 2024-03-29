package com.tickettogether.global.config.security.utils;

import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.util.StringUtils;

import javax.servlet.http.HttpServletRequest;

public class HeaderUtil {
    private final static String HEADER_AUTHORIZATION = "Authorization";
    private final static String TOKEN_PREFIX = "Bearer ";

    public static String getAccessToken(HttpServletRequest request) {
        String headerValue = request.getHeader(HEADER_AUTHORIZATION);
        return extractJWT(headerValue);
    }

    public static String getAccessTokenFromWebSocket(StompHeaderAccessor accessor) {
        String headerValue = accessor.getFirstNativeHeader(HEADER_AUTHORIZATION);
        return extractJWT(headerValue);
    }

    private static String extractJWT(String headerValue){
        if (!StringUtils.hasText(headerValue)) {
            return null;
        }

        if (headerValue.startsWith(TOKEN_PREFIX)) {
            return headerValue.substring(TOKEN_PREFIX.length());
        }
        return null;
    }
}
