package com.tickettogether.global.config.security;

import com.tickettogether.global.config.redis.util.RedisUtil;
import com.tickettogether.global.config.security.jwt.JwtConfig;
import com.tickettogether.global.config.security.jwt.filter.TokenAuthenticationFilter;
import com.tickettogether.global.config.security.jwt.token.AuthTokenProvider;
import com.tickettogether.global.config.security.oauth.handler.MyOauth2LogoutSuccessHandler;
import com.tickettogether.global.config.security.oauth.repository.OAuth2AuthorizationRequestRepository;
import com.tickettogether.global.config.security.oauth.service.CustomOAuth2UserService;
import com.tickettogether.global.config.security.oauth.handler.MyOauth2SuccessHandler;
import com.tickettogether.global.error.BaseExceptionHandlerFilter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;
import org.springframework.security.web.firewall.DefaultHttpFirewall;
import org.springframework.security.web.firewall.HttpFirewall;
import org.springframework.web.cors.CorsUtils;


@EnableWebSecurity
@RequiredArgsConstructor
@Slf4j
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private final CustomOAuth2UserService customOAuth2UserService;
    private final AuthTokenProvider authTokenProvider;
    private final RedisUtil<String, String> redisUtil;
    private final JwtConfig jwtConfig;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .csrf().disable()
                .headers().frameOptions().disable()     //h2 DB
                .and()
                .authorizeRequests()
                .requestMatchers(CorsUtils::isPreFlightRequest).permitAll()
                .antMatchers("/oauth2/**", "/ws/**").permitAll()
                .antMatchers("/api/v1/login", "/main",
                        "/test", "/api/v1/refresh", "/v3/api-docs", "/swagger*/**").permitAll()
                .anyRequest().authenticated()
                .and()
                .addFilterBefore(new TokenAuthenticationFilter(authTokenProvider, redisUtil), UsernamePasswordAuthenticationFilter.class)
                .addFilterBefore(new BaseExceptionHandlerFilter(), TokenAuthenticationFilter.class)
                .oauth2Login()
                .authorizationEndpoint()
                .authorizationRequestRepository(myAuthorizationRequestRepository())
                .and()
                .userInfoEndpoint()
                .userService(customOAuth2UserService)
                .and()
                .successHandler(myOauth2SuccessHandler());  //성공 핸들러;
    }

    @Override
    public void configure(WebSecurity web) throws Exception {
        super.configure(web);
        web.httpFirewall(defaultHttpFirewall());
        // TODO 로그인 도입 이후 삭제
        web.ignoring()
                .antMatchers("/ws/**", "/api/v1/login", "/main",
                        "/test", "/api/v1/chat/1/test/**", "/api/v1/refresh", "/v3/api-docs",
                        "/swagger*/**");
    }

    @Bean
    public AuthenticationSuccessHandler myOauth2SuccessHandler() {
        return new MyOauth2SuccessHandler(redisUtil, authTokenProvider, jwtConfig);
    }

    @Bean
    public LogoutSuccessHandler myLogoutHandler() {
        return new MyOauth2LogoutSuccessHandler();
    }

    @Bean
    public OAuth2AuthorizationRequestRepository myAuthorizationRequestRepository() {
        return new OAuth2AuthorizationRequestRepository();
    }

    @Bean
    public HttpFirewall defaultHttpFirewall() {
        return new DefaultHttpFirewall();
    }
}




