package com.tickettogether.domain.chat;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.WebSocketMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.WebSocketHandlerDecorator;

@Slf4j
public class CustomWebSocketHandlerDecorator extends WebSocketHandlerDecorator {
    public CustomWebSocketHandlerDecorator(WebSocketHandler delegate) {
        super(delegate);
    }

    @Override
    public void handleMessage(WebSocketSession session, WebSocketMessage<?> message) throws Exception {
        if (message instanceof TextMessage) {
            TextMessage msg = (TextMessage) message;
            String payload = msg.getPayload();

            if (payload.contains("DISCONNECT")) {
                log.info("ignore disconnect frame to avoid double disconnect: {}", message);
                return;
            }
        }
        //forward 과정 패스
        super.handleMessage(session, message);
    }
}

