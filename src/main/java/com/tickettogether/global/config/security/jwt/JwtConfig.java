package com.tickettogether.global.config.security.jwt;

import com.tickettogether.global.config.security.jwt.token.AuthTokenProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class JwtConfig {
    @Value("${jwt.secret}")
    private String secret;

    @Value("${jwt.token-expiry}")
    private String tokenExpiry;

    @Value("${jwt.refresh-token-expiry}")
    private String refreshExpiry;

    @Value("${jwt.authorized-redirect-uris}")
    private String authorizedRedirectUri;

    @Bean
    public AuthTokenProvider jwtProvider() {
        return new AuthTokenProvider(this.secret, this.tokenExpiry);
    }

    public String getAuthorizedRedirectUri() {
        return authorizedRedirectUri;
    }

    public String getRefreshExpiry() { return refreshExpiry;}
}
