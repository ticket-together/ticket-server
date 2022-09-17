package com.tickettogether.domain.test;

import org.joda.time.DateTime;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class testController {
    @RequestMapping("/test")
    public String sample(){
        return DateTime.now() + "ticket together deploy 확인입니당.";
    }
}
