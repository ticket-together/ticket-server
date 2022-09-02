package com.tickettogether.domain.chat.listener;


import com.tickettogether.global.config.redis.util.RedisUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.context.event.EventListener;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.socket.messaging.SessionConnectedEvent;
import org.springframework.web.socket.messaging.SessionDisconnectEvent;

import java.util.Map;
import java.util.Objects;

import static com.tickettogether.domain.chat.domain.ChatMessage.*;
import static com.tickettogether.domain.chat.dto.ChatDto.*;

@Component
@RequiredArgsConstructor
@Slf4j
public class WebSocketEventListener {

    private final RabbitTemplate rabbitTemplate;

    private final RedisUtil<String, String> redisUtil;

    @EventListener
    public void handleWebSocketConnectListener(SessionConnectedEvent event) {
        StompHeaderAccessor headerAccessor = StompHeaderAccessor.wrap(event.getMessage());
        String sessionId = headerAccessor.getSessionId();
        log.info("Received a new web socket connection from Session Id :  {}", sessionId);
    }

    @EventListener
    public void handleWebSocketDisconnectedListener(SessionDisconnectEvent event){
        StompHeaderAccessor headerAccessor = StompHeaderAccessor.wrap(event.getMessage());

        String sessionId = headerAccessor.getSessionId();
        String username = (String) Objects.requireNonNull(headerAccessor.getSessionAttributes()).get("username");
        String roomId = (String) Objects.requireNonNull(headerAccessor.getSessionAttributes()).get("roomId");
        log.info("User Disconnected : {} ( Session ID : {})", username, sessionId);

        Map<String, String> enterUserList = redisUtil.getHashKeys(roomId);
        if(StringUtils.hasText(username) && !enterUserList.containsKey(username)){
            ChatMessageResponse disconnectMessage = ChatMessageResponse.builder()
                    .type(MessageType.LEAVE.name())
                    .data(username + "님이 나갔습니다.")
                    .sender(username).build();

            rabbitTemplate.convertAndSend("amq.topic", "room." + roomId , disconnectMessage);
        }
    }
}
