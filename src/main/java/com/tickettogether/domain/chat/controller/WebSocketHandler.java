package com.tickettogether.domain.chat.controller;

import com.tickettogether.domain.chat.domain.ChatMessage;
import com.tickettogether.domain.chat.domain.ChatRoom;
import com.tickettogether.domain.chat.service.ChatRoomService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.time.LocalDateTime;
import java.util.Objects;

@Slf4j
@Component
@RequiredArgsConstructor
public class WebSocketHandler extends TextWebSocketHandler {
    private final JSONParser parser = new JSONParser();

    private final ChatRoomService chatRoomService;

    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        log.info("connection success : {}", session.getId());
    }

    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
        log.info("Accepted connection from: {}:{}", Objects.requireNonNull(session.getRemoteAddress()).getHostString(), session.getRemoteAddress().getPort());
        JSONObject payload = (JSONObject) parser.parse(message.getPayload());

        ChatMessage chatMessage = ChatMessage.builder()
                        .roomId(payload.get("roomId").toString())
                        .data(payload.get("data"))
                        .type(ChatMessage.MessageType.valueOf(payload.get("type").toString()))
                        .sender(payload.get("sender").toString())
                        .createdAt(LocalDateTime.now()).build();

        ChatRoom chatRoom = chatRoomService.getChatRoomById(chatMessage.getRoomId());
        chatRoom.handleActions(session, chatMessage, chatRoomService);
    }

    @Override
    public void handleTransportError(WebSocketSession session, Throwable exception) throws Exception {
        super.handleTransportError(session, exception);
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
        log.info("connection closed by {} : {}", Objects.requireNonNull(session.getRemoteAddress()).getHostString(), session.getRemoteAddress().getPort());
    }
}
