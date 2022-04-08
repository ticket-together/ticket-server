package com.tickettogether.domain.member.controller;
import com.tickettogether.domain.member.domain.Member;
import com.tickettogether.domain.member.repository.MemberRepository;
import com.tickettogether.global.config.security.oauth.dto.SessionMember;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;


//"http://localhost:8080/oauth2/authorization/kakao";
@Controller
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/api/v1")
public class LoginController {
    private final MemberRepository memberRepository;
    private final String DEFAULT_REDIRECT_URL = "/main";
    private final boolean invalidateMember = true;

    //이걸로 처음에 id, password 를 보냄(근데 이거는 백엔드 테스트잖아..)
    //만약 토큰이 만료되면, 카카오하고 연결은 되어있는 상태니까..여기서 토큰+리프레시 토큰 재발급 해줘야함
    //아래 /login 이 토큰과 리프레시 토큰 재발급 하는 부분
    @PostMapping("/login")
    public String login(){
        //토큰이 만료된 경우 재발급
        return null;
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
        log.info("token = {}", request.getQueryString());
    }

}
