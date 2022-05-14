package com.tickettogether.global.config.security;

import com.tickettogether.global.config.redis.util.RedisUtil;
import com.tickettogether.global.config.security.jwt.JwtConfig;
import com.tickettogether.global.config.security.jwt.filter.TokenAuthenticationFilter;
import com.tickettogether.global.config.security.jwt.token.AuthTokenProvider;
import com.tickettogether.global.config.security.oauth.handler.MyOauth2LogoutSuccessHandler;
import com.tickettogether.global.config.security.oauth.repository.OAuth2AuthorizationRequestRepository;
import com.tickettogether.global.config.security.oauth.service.CustomOAuth2UserService;
import com.tickettogether.global.config.security.oauth.handler.MyOauth2SuccessHandler;
import com.tickettogether.global.exception.BaseExceptionHandlerFilter;
import lombok.RequiredArgsConstructor;
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

@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private final CustomOAuth2UserService customOAuth2UserService;
    private final CustomUserDetailsService customUserDetailsService;
    private final AuthTokenProvider authTokenProvider;
    private final RedisUtil<String, String> redisUtil;
    private final JwtConfig jwtConfig;
    private final BaseExceptionHandlerFilter baseExceptionHandlerFilter;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .csrf().disable()
//                .exceptionHandling()
//                .authenticationEntryPoint(new RestAuthenticationEntryPoint())
//                .accessDeniedHandler(tokenAccessDeniedHandler)
//                .and()
                .headers().frameOptions().disable()     //h2 DB
                .and()
                .authorizeRequests()
                .antMatchers("/oauth2/**").permitAll()
                .antMatchers("/api/v1/login", "/api/v1/logout", "/main", "/api/v1/oauth/redirect", "/test", "/api/v1/refresh", "/api/v1/reviews").permitAll()
                .anyRequest().authenticated()
//                .and()
//                .logout().logoutUrl("/api/v1/logout")
//                .logoutSuccessHandler(myLogoutHandler())
                .and()
                .oauth2Login()
                .authorizationEndpoint()
                .authorizationRequestRepository(myAuthorizationRequestRepository())
                .and()
                .userInfoEndpoint()
                .userService(customOAuth2UserService)
                .and()
                .successHandler(myOauth2SuccessHandler());  //성공 핸들러;

        http.addFilterBefore(tokenAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class);
        http.addFilterBefore(baseExceptionHandlerFilter, TokenAuthenticationFilter.class);
    }

    @Override
    public void configure(WebSecurity web) throws Exception {
        super.configure(web);
        web.httpFirewall(defaultHttpFirewall());
    }

    @Bean
    public TokenAuthenticationFilter tokenAuthenticationFilter(){
        return new TokenAuthenticationFilter(authTokenProvider, customUserDetailsService, redisUtil);
    }
    @Bean
    public AuthenticationSuccessHandler myOauth2SuccessHandler(){
        return new MyOauth2SuccessHandler(redisUtil, authTokenProvider, jwtConfig);
    }

    @Bean
    public LogoutSuccessHandler myLogoutHandler(){
        return new MyOauth2LogoutSuccessHandler();
    }

    @Bean
    public OAuth2AuthorizationRequestRepository myAuthorizationRequestRepository(){
        return new OAuth2AuthorizationRequestRepository();
    }

    @Bean public HttpFirewall defaultHttpFirewall() {
        return new DefaultHttpFirewall();
    }
}


