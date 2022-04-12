package com.tickettogether.global.config.security.jwt;

import com.tickettogether.global.config.security.jwt.token.AuthTokenProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.ArrayList;
import java.util.List;

@Configuration
@RequiredArgsConstructor
public class JwtConfig {
    @Value("${jwt.secret}")
    private String secret;

    @Value("${jwt.token-expiry}")
    private String tokenExpiry;

    @Value("${jwt.authorized-redirect-uris}")
    private List<String> authorizedRedirectUris = new ArrayList<>();

    @Bean
    public AuthTokenProvider jwtProvider() {
        return new AuthTokenProvider(this.secret, this.tokenExpiry);
    }

    public List<String> getAuthorizedRedirectUris() {
        return authorizedRedirectUris;
    }
}
