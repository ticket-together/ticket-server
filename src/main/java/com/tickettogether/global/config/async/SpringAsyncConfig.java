package com.tickettogether.global.config.async;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.Executor;

@EnableAsync
@Configuration
public class SpringAsyncConfig {

    @Bean
    public Executor threadPoolTaskExecutor(){
        ThreadPoolTaskExecutor threadPoolTaskExecutor = new ThreadPoolTaskExecutor();
        threadPoolTaskExecutor.setCorePoolSize(3);       // 기본 쓰레드 사이즈
        threadPoolTaskExecutor.setMaxPoolSize(30);       // 최대 쓰레드 사이즈
        threadPoolTaskExecutor.setQueueCapacity(10);     // Max 쓰레드가 동작하는 경우 대기하는 queue 사이즈
        return threadPoolTaskExecutor;
    }
}
