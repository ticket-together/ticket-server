package com.tickettogether.domain.member.controller;
import com.tickettogether.domain.member.domain.Member;
import com.tickettogether.domain.member.dto.TokenResponseDto;
import com.tickettogether.domain.member.repository.MemberRepository;
import com.tickettogether.global.config.redis.service.RedisService;
import com.tickettogether.global.config.security.exception.TokenRefreshException;
import com.tickettogether.global.config.security.exception.TokenValidFailedException;
import com.tickettogether.global.config.security.jwt.JwtConfig;
import com.tickettogether.global.config.security.jwt.service.RefreshService;
import com.tickettogether.global.config.security.jwt.token.AuthToken;
import com.tickettogether.global.config.security.jwt.token.AuthTokenProvider;
import com.tickettogether.global.config.security.oauth.dto.SessionMember;
import com.tickettogether.global.config.security.utils.CookieUtils;
import com.tickettogether.global.config.security.utils.HeaderUtil;
import com.tickettogether.global.exception.BaseException;
import com.tickettogether.global.exception.BaseResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

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
    private final RedisService<String, String> redisService;

    private final RefreshService refreshService;
    private final JwtConfig jwtConfig;
    private final String DEFAULT_REDIRECT_URL = "/main";
    private final boolean invalidateMember = true;

    @PostMapping("/login")
    public String login(){
        return null;
    }

    //액세스 + 리프레시 토큰 갱신
    @ResponseBody
    @PostMapping("/refresh")
    public BaseResponse<TokenResponseDto> renewAccessToken(HttpServletRequest request, HttpServletResponse response) throws BaseException {
        //access token
        String accessToken = HeaderUtil.getAccessToken(request);
        AuthToken authToken = authTokenProvider.convertToAuthToken(accessToken);
        refreshService.validateAccessToken(authToken);

        //refresh token
        String refreshToken = CookieUtils.getCookie(request, REFRESH_TOKEN_COOKIE_NAME)
                .map(Cookie::getValue)
                .orElse(null);

        AuthToken refreshAuthToken = authTokenProvider.convertToAuthToken(refreshToken);
        refreshService.validateRefreshToken(refreshAuthToken, refreshToken, authToken.getToken());
        refreshService.renewAccessTokenAndRefreshToken(refreshAuthToken, refreshToken, authToken.getToken());

        if(refreshService.getNewRefreshToken() != null){
            int cookieMaxAge = (int) refreshService.getRefreshExpiry()/ 60;
            CookieUtils.deleteCookie(request, response, REFRESH_TOKEN_COOKIE_NAME);
            CookieUtils.addCookie(response, REFRESH_TOKEN_COOKIE_NAME, refreshService.getNewRefreshToken().getToken(), cookieMaxAge);
        }
        return new BaseResponse<>(new TokenResponseDto(refreshService.getNewAccessToken().getToken()));
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

}
