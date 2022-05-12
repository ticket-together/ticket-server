package com.tickettogether.domain.test;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class testController {
    private final TestService testService;
    @RequestMapping("/test")
    public String sample(){
        testService.redisString();
        return "ticket together deploy 확인입니당.";
    }
}
