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


@Controller
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/api/v1")
public class LoginController {
    private final MemberRepository memberRepository;
    private final String DEFAULT_REDIRECT_URL = "/main";
    private final boolean invalidateMember = true;

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
        log.info("header = {}", request.getQueryString());
    }

}
