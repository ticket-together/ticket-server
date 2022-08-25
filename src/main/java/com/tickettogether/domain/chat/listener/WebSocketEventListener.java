package com.tickettogether.domain.chat.listener;

import com.tickettogether.domain.chat.domain.ChatMessage;
import com.tickettogether.domain.chat.dto.ChatDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.context.event.EventListener;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.socket.messaging.SessionConnectedEvent;
import org.springframework.web.socket.messaging.SessionDisconnectEvent;

import java.util.Objects;

import static com.tickettogether.domain.chat.domain.ChatMessage.*;
import static com.tickettogether.domain.chat.dto.ChatDto.*;

@Component
@RequiredArgsConstructor
@Slf4j
public class WebSocketEventListener {

    private final RabbitTemplate rabbitTemplate;

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

        if (StringUtils.hasText(username)){
            log.info("User Disconnected : {} ( Session ID : {})", username, sessionId);

            ChatMessageResponse disconnectMessage = ChatMessageResponse.builder()
                    .type(MessageType.LEAVE.name())
                    .data(username + "님이 채팅방에서 나가셨습니다.")
                    .sender(username).build();

            rabbitTemplate.convertAndSend("amq.topic", "room." + roomId , disconnectMessage);
        }
    }
}