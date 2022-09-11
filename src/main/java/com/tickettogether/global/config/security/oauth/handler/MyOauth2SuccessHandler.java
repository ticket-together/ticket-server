package com.tickettogether.global.config.security.oauth.handler;

import com.tickettogether.domain.member.domain.Role;
import com.tickettogether.global.config.redis.util.RedisUtil;
import com.tickettogether.global.config.security.UserPrincipal;
import com.tickettogether.global.config.security.jwt.JwtConfig;
import com.tickettogether.global.config.security.jwt.token.AuthToken;
import com.tickettogether.global.config.security.jwt.token.AuthTokenProvider;
import com.tickettogether.global.config.security.oauth.dto.KakaoOAuthAttributes;
import com.tickettogether.global.config.security.oauth.dto.OAuthAttributes;
import com.tickettogether.global.config.security.utils.CookieUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.RedirectStrategy;
import org.springframework.security.web.WebAttributes;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.savedrequest.HttpSessionRequestCache;
import org.springframework.security.web.savedrequest.RequestCache;
import org.springframework.security.web.savedrequest.SavedRequest;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;
import org.springframework.web.util.UriComponentsBuilder;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.*;

import static com.tickettogether.global.config.security.oauth.repository.OAuth2AuthorizationRequestRepository.REFRESH_TOKEN_COOKIE_NAME;

@RequiredArgsConstructor
@Slf4j
public class MyOauth2SuccessHandler implements AuthenticationSuccessHandler {
    private final RedisUtil<String, String> redisUtil;
    private final AuthTokenProvider authTokenProvider;
    private final JwtConfig jwtConfig;
    private final RequestCache requestCache = new HttpSessionRequestCache();
    private final RedirectStrategy redirectStrategy = new DefaultRedirectStrategy();

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
        //1. 설정 정보에서 redirect uri 정보 가져오기
        String redirectUri = jwtConfig.getAuthorizedRedirectUri();
        if (!StringUtils.hasText(redirectUri)){
            throw new IllegalArgumentException("redirect uri must not be empty");
        }

        //2. authentication 가공해서 토큰 생성
        UserPrincipal principal = ((UserPrincipal) authentication.getPrincipal());
        OAuthAttributes userInfo = KakaoOAuthAttributes.of(principal.getUserNameAttribute(), principal.getAttributes());
        Assert.notNull(userInfo, "userInfo cannot be null");

        String userId = principal.getUserId().toString();
        Collection<? extends GrantedAuthority> authorities = principal.getAuthorities();
        if (!hasAuthority(authorities, Role.USER.getKey())){
            throw new IllegalArgumentException("grant authority does not exist");
        }
        AuthToken authToken = authTokenProvider.createAuthToken(userId, Role.USER.getKey());

        //3. 리프레시 토큰 생성 후 쿠키와 redis 에 저장
        long refreshExpiry = Long.parseLong(jwtConfig.getRefreshExpiry());
        AuthToken refreshToken = authTokenProvider.createRefreshToken(userId, authToken.getToken(), refreshExpiry);
        setRefreshValue(refreshToken.getToken(), userId, authToken.getToken());

        int cookieMaxAge = (int) refreshExpiry / 60;
        CookieUtils.deleteCookie(request, response, REFRESH_TOKEN_COOKIE_NAME);
        CookieUtils.addCookie(response, REFRESH_TOKEN_COOKIE_NAME, refreshToken.getToken(), cookieMaxAge);

        //4. redirect 경로로 토큰 값 전송
        return UriComponentsBuilder.fromUriString(redirectUri)
                .queryParam("token", authToken.getToken())
                .build().toUriString();
    }

    private void setRefreshValue(String key, String... values){
        List<String> refreshValueList = new ArrayList<>(Arrays.asList(values));
        redisUtil.setValue(key, refreshValueList);
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
}
