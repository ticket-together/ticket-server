package com.tickettogether.domain.member.controller;

import com.tickettogether.global.config.security.oauth.dto.SessionMember;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;

@Controller
@RequiredArgsConstructor
@RequestMapping("/")
@Slf4j
public class MemberController {
    private final HttpSession httpSession;

    @GetMapping
    @ResponseBody
    public String test(){
        //현재 로그인한 사용자 정보 테스트
        SessionMember user = (SessionMember) httpSession.getAttribute("user");
        log.info("login user = {}", user.getName());
        return "success!";
    }

}
