package com.tickettogether.global.config.security.oauth.handler;

import com.tickettogether.domain.member.domain.Role;
import com.tickettogether.domain.member.repository.MemberRepository;
import com.tickettogether.global.config.security.CookieUtils;
import com.tickettogether.global.config.security.jwt.JwtConfig;
import com.tickettogether.global.config.security.jwt.token.AuthTokenProvider;
import com.tickettogether.global.config.security.jwt.token.AuthToken;
import com.tickettogether.global.config.security.oauth.dto.KakaoOAuthAttributes;
import com.tickettogether.global.config.security.oauth.dto.OAuthAttributes;
import com.tickettogether.global.config.security.oauth.repository.OAuth2AuthorizationRequestRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.RedirectStrategy;
import org.springframework.security.web.WebAttributes;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.savedrequest.HttpSessionRequestCache;
import org.springframework.security.web.savedrequest.RequestCache;
import org.springframework.security.web.savedrequest.SavedRequest;
import org.springframework.util.Assert;
import org.springframework.web.util.UriComponentsBuilder;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.net.URI;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

import static com.tickettogether.global.config.security.oauth.repository.OAuth2AuthorizationRequestRepository.REDIRECT_URI_PARAM_COOKIE_NAME;

@RequiredArgsConstructor
@Slf4j
public class MyOauth2SuccessHandler implements AuthenticationSuccessHandler {
    private final AuthTokenProvider authTokenProvider;
    private final JwtConfig jwtConfig;
    private RequestCache requestCache = new HttpSessionRequestCache();
    private RedirectStrategy redirectStrategy = new DefaultRedirectStrategy();

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException {
        String targetUrl = determineTargetUrl(request, response, authentication);
        clearAuthenticationAttributes(request);
        createTokenAndRedirect(request, response, targetUrl);
    }

    protected final void createTokenAndRedirect(HttpServletRequest request, HttpServletResponse response, String targetUrl) throws IOException {
        SavedRequest savedRequest = this.requestCache.getRequest(request, response);
        if (savedRequest == null){     //최초 로그인
            redirectStrategy.sendRedirect(request, response, targetUrl);
        }else{                        //허용되지 않은 자원에 접근 후 예외 발생
            redirectStrategy.sendRedirect(request, response, savedRequest.getRedirectUrl());
        }
    }

    protected final void clearAuthenticationAttributes(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        if (session != null) {
            session.removeAttribute(WebAttributes.AUTHENTICATION_EXCEPTION);
        }
    }

    private String determineTargetUrl(HttpServletRequest request, HttpServletResponse response, Authentication authentication){
        //1. 쿠키에서 redirect uri 정보 가져오기
        Optional<String> redirectUri = CookieUtils.getCookie(request, REDIRECT_URI_PARAM_COOKIE_NAME)
                .map(Cookie::getValue);

        if(redirectUri.isPresent() && !isAuthorizedRedirectUri(redirectUri.get())) {
            throw new IllegalArgumentException("Sorry! We've got an Unauthorized Redirect URI");
        }

        //2. authentication 가공해서 토큰 생성
        DefaultOAuth2User principal = ((DefaultOAuth2User) authentication.getPrincipal());
        OAuthAttributes userInfo = KakaoOAuthAttributes.of(principal.getName(), principal.getAttributes());
        Assert.notNull(userInfo, "userInfo cannot be null");

        Collection<? extends GrantedAuthority> authorities = principal.getAuthorities();
        if (!hasAuthority(authorities, Role.USER.getKey())){
            throw new IllegalArgumentException("grant authority does not exist");
        }

        AuthToken authToken = authTokenProvider.createAuthToken(userInfo.getNameKey(), Role.USER.getKey());

        //3. redirect 경로로 토큰 값 전송
        return UriComponentsBuilder.fromUriString(redirectUri.get())
                .queryParam("token", authToken.getToken())
                .build().toUriString();
    }

    private boolean hasAuthority(Collection<? extends GrantedAuthority> authorities, String authority) {
        if (authorities == null) return false;

        for (GrantedAuthority grantedAuthority : authorities) {
            if (authority.equals(grantedAuthority.getAuthority())) {
                return true;
            }
        }
        return false;
    }

    private boolean isAuthorizedRedirectUri(String uri) {
        URI clientRedirectUri = URI.create(uri);

        return jwtConfig.getAuthorizedRedirectUris()
                .stream()
                .anyMatch(authorizedRedirectUri -> {
                    // Only validate host and port. Let the clients use different paths if they want to
                    URI authorizedURI = URI.create(authorizedRedirectUri);
                    if(authorizedURI.getHost().equalsIgnoreCase(clientRedirectUri.getHost())
                            && authorizedURI.getPort() == clientRedirectUri.getPort()) {
                        return true;
                    }
                    return false;
                });
    }
}
