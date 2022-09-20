package com.tickettogether.global.config.web;

import com.tickettogether.global.config.security.annotation.CustomArgumentResolver;
import com.tickettogether.global.config.security.oauth.dto.CorsProperties;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.List;

@Configuration
@RequiredArgsConstructor
@Slf4j
public class WebMvcConfig implements WebMvcConfigurer {

    private final CorsProperties corsProperties;

    @Bean
    public CustomArgumentResolver customArgumentResolver(){
        return new CustomArgumentResolver();
    }

    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> argumentResolvers) {
        argumentResolvers.add(customArgumentResolver());
    }

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOrigins(corsProperties.getAllowedOrigins().split(","))
                .allowedMethods(corsProperties.getAllowedMethods().split(","))
                .allowedHeaders(corsProperties.getAllowedHeaders().split(","))
                .allowCredentials(true)
                .maxAge(corsProperties.getMaxAge());
    }
}
