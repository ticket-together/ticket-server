package com.tickettogether.global.config.security.oauth.handler;

import com.tickettogether.global.config.security.jwt.JwtConfig;
import com.tickettogether.global.config.security.oauth.repository.OAuth2AuthorizationRequestRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;
import org.springframework.web.util.UriComponentsBuilder;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@RequiredArgsConstructor
public class MyOauth2FailureHandler extends SimpleUrlAuthenticationFailureHandler {
    private final OAuth2AuthorizationRequestRepository oAuth2AuthorizationRequestRepository;

    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException, ServletException {
        JwtConfig jwtConfig = new JwtConfig();
        String targetUrl = jwtConfig.getAuthorizedRedirectUri();
        exception.printStackTrace();

        targetUrl = UriComponentsBuilder.fromUriString(targetUrl)
                .queryParam("error", exception.getLocalizedMessage())
                .build().toUriString();

        oAuth2AuthorizationRequestRepository.removeAuthorizationRequestCookie(request, response);

        getRedirectStrategy().sendRedirect(request, response, targetUrl);
    }
}
