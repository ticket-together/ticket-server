package com.tickettogether.domain.chat.config;

import com.tickettogether.domain.chat.CustomWebSocketHandlerDecorator;
import com.tickettogether.domain.chat.interceptor.AuthenticationChannelInterceptor;
import com.tickettogether.domain.member.repository.MemberRepository;
import com.tickettogether.global.config.redis.util.RedisUtil;
import com.tickettogether.global.config.security.jwt.token.AuthTokenProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.messaging.simp.config.ChannelRegistration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.messaging.support.ChannelInterceptor;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.socket.config.annotation.*;

@EnableWebSocketMessageBroker
@Configuration
@RequiredArgsConstructor
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {

    private final RedisUtil<String, String> redisUtil;
    private final AuthTokenProvider authTokenProvider;
    private final MemberRepository memberRepository;

    @Value("${spring.rabbitmq.host}")
    private String host;

    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        registry.addEndpoint("/ws")
                .setAllowedOriginPatterns("*")
                .withSockJS();

        registry.addEndpoint("/ws")
                .setAllowedOriginPatterns("*");
    }

    @Override
    public void configureMessageBroker(MessageBrokerRegistry registry) {
        registry.setPathMatcher(new AntPathMatcher("."));   // topic exchange
        registry.setApplicationDestinationPrefixes("/pub");
        registry.enableStompBrokerRelay("/topic")
                .setRelayHost(host)
                .setRelayPort(61613);
    }

    @Override
    public void configureWebSocketTransport(WebSocketTransportRegistration registry) {
        registry.addDecoratorFactory(CustomWebSocketHandlerDecorator::new);
    }

    @Override
    public void configureClientInboundChannel(ChannelRegistration registration) {
        registration.interceptors(authenticationChannelInterceptor());
    }

    @Bean
    public ChannelInterceptor authenticationChannelInterceptor() {
        return new AuthenticationChannelInterceptor(redisUtil, authTokenProvider, memberRepository);
    }
}
