package com.tickettogether.global.aop;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

@Slf4j
@Aspect
@Component
public class LogAspect {
    @Before("com.tickettogether.global.aop.PointCuts.logPointcut()")
    public void doLog(JoinPoint joinPoint) {
        log.info("[trace] {} args={}", joinPoint.getSignature(), joinPoint.getArgs());
    }
}
