package com.tickettogether.domain.member.controller;
import com.tickettogether.domain.member.domain.Member;
import com.tickettogether.domain.member.dto.TokenResponseDto;
import com.tickettogether.domain.member.repository.MemberRepository;
import com.tickettogether.global.config.redis.util.RedisUtil;
import com.tickettogether.global.config.security.jwt.service.AuthService;
import com.tickettogether.global.config.security.jwt.token.AuthToken;
import com.tickettogether.global.config.security.jwt.token.AuthTokenProvider;
import com.tickettogether.global.config.security.oauth.dto.SessionMember;
import com.tickettogether.global.config.security.utils.CookieUtils;
import com.tickettogether.global.config.security.utils.HeaderUtil;
import com.tickettogether.global.error.exception.BaseException;
import com.tickettogether.global.error.dto.BaseResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import static com.tickettogether.global.config.security.oauth.repository.OAuth2AuthorizationRequestRepository.REFRESH_TOKEN_COOKIE_NAME;


@Controller
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/api/v1")
public class LoginController {
    private final MemberRepository memberRepository;
    private final AuthTokenProvider authTokenProvider;
    private final RedisUtil<String, String> redisUtil;
    private final AuthService authService;
    private final String DEFAULT_REDIRECT_URL = "/main";
    private final boolean invalidateMember = true;

    //액세스 + 리프레시 토큰 갱신
    @ResponseBody
    @PostMapping("/refresh")
    public BaseResponse<TokenResponseDto> renewAccessToken(HttpServletRequest request, HttpServletResponse response) throws BaseException {
        //access token
        String accessToken = HeaderUtil.getAccessToken(request);
        AuthToken authToken = authTokenProvider.convertToAuthToken(accessToken);
        authService.validateAccessToken(authToken);

        //refresh token
        String refreshToken = getRefreshTokenFromCookie(request);
        AuthToken refreshAuthToken = authTokenProvider.convertToAuthToken(refreshToken);
        authService.validateRefreshToken(refreshAuthToken, refreshToken, authToken.getToken());
        authService.renewAccessTokenAndRefreshToken(refreshAuthToken, refreshToken, authToken.getToken());

        if(authService.getNewRefreshToken() != null){
            int cookieMaxAge = (int) authService.getRefreshExpiry()/ 60;
            CookieUtils.deleteCookie(request, response, REFRESH_TOKEN_COOKIE_NAME);
            CookieUtils.addCookie(response, REFRESH_TOKEN_COOKIE_NAME, authService.getNewRefreshToken().getToken(), cookieMaxAge);
        }
        return new BaseResponse<>(new TokenResponseDto(authService.getNewAccessToken().getToken()));
    }
    @ResponseBody
    @PostMapping("/logout")
    public BaseResponse<String> logout(HttpServletRequest request){
        String accessToken = HeaderUtil.getAccessToken(request);
        String refreshToken = getRefreshTokenFromCookie(request);
        authService.logoutAndDeleteToken(accessToken,refreshToken);
        return new BaseResponse<>("logout success");
    }

    @GetMapping("/quit")
    public String quitService(HttpServletRequest request){
        Assert.notNull(request, "HttpServletRequest required");
        HttpSession session = request.getSession(false);

        if (invalidateMember & session != null) {
            SessionMember loginMember = (SessionMember) session.getAttribute("user");
            Member member = memberRepository.findByEmail(loginMember.getEmail());
            member.changeStatus(member.getStatus());

            session.invalidate();
        }
        SecurityContext context = SecurityContextHolder.getContext();
        SecurityContextHolder.clearContext();
        context.setAuthentication(null);

        return "redirect:" + DEFAULT_REDIRECT_URL;
    }

    @GetMapping("/oauth/redirect")
    public void getTokenTest(HttpServletRequest request){
        log.info("header = {}", request.getQueryString());
    }

    private String getRefreshTokenFromCookie(HttpServletRequest request){
        return CookieUtils.getCookie(request, REFRESH_TOKEN_COOKIE_NAME)
                .map(Cookie::getValue)
                .orElse(null);
    }
}
