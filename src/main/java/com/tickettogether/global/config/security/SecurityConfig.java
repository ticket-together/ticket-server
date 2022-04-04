package com.tickettogether.global.config.security;

import com.tickettogether.domain.member.repository.MemberRepository;
import com.tickettogether.global.config.security.oauth.handler.MyOauth2LogoutSuccessHandler;
import com.tickettogether.global.config.security.oauth.service.CustomOAuth2UserService;
import com.tickettogether.global.config.security.oauth.handler.MyOauth2SuccessHandler;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;

@RequiredArgsConstructor
@EnableWebSecurity
@Slf4j
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private final CustomOAuth2UserService customOAuth2UserService;
    private final MemberRepository memberRepository;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .csrf().disable()
                .headers().frameOptions().disable()     //h2 DB
                .and()
                .authorizeRequests()
                .antMatchers("/oauth2/**").permitAll()
                .antMatchers("/api/v1/login", "api/v1/logout", "/main").permitAll()
                .anyRequest().authenticated()
                .and()
                .logout().logoutUrl("/api/v1/logout")
                .logoutSuccessHandler(myLogoutHandler())
                .and()
                .oauth2Login()
                .redirectionEndpoint()
                .and()
                    .successHandler(myOauth2SuccessHandler())    //성공 핸들러
                    .userInfoEndpoint()
                        .userService(customOAuth2UserService);
    }

    @Bean
    public AuthenticationSuccessHandler myOauth2SuccessHandler(){
        return new MyOauth2SuccessHandler(memberRepository);
    }

    @Bean
    public LogoutSuccessHandler myLogoutHandler(){
        return new MyOauth2LogoutSuccessHandler();
    }
}


