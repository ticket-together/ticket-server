package com.tickettogether.global.config.security.jwt;

import com.tickettogether.global.config.properties.JwtProperties;
import com.tickettogether.global.config.security.jwt.token.AuthTokenProvider;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class JwtConfig {
    @Value("${jwt.secret}")
    private String secret;

    @Value("${jwt.tokenExpiry}")
    private String tokenExpiry;

    @Bean
    public AuthTokenProvider jwtProvider() {
        return new AuthTokenProvider(this.secret, this.tokenExpiry);
    }
}
