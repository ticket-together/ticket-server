package com.tickettogether.domain.member.controller;

import com.tickettogether.global.config.security.oauth.dto.SessionMember;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;

@Controller
@RequiredArgsConstructor
@RequestMapping("/api/v1/member")
@Slf4j
public class MemberController {
    private final HttpSession httpSession;

    @GetMapping
    @ResponseBody
    public String test(){
        //현재 로그인한 사용자 정보 테스트
        DefaultOAuth2User user2 = (DefaultOAuth2User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if(user2 != null) log.info("login user security = {}", user2.getName());
        return "login success!";
    }
}
