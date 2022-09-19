package com.tickettogether.global.aop;

import org.aspectj.lang.annotation.Pointcut;

public class PointCuts {
    @Pointcut("execution(* *..*Controller*.*(..))")
    public void allController() {
    }

    @Pointcut("allController()")
    public void logPointcut() {
    }
}