package com.tickettogether.domain.chat.domain;

import com.tickettogether.domain.chat.service.ChatRoomService;
import lombok.Builder;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.socket.WebSocketSession;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Getter
@Slf4j
public class ChatRoom {
    private final String roomId;

    private final String name;

    private final Long potId;

    private final Map<String, WebSocketSession> sessions = new ConcurrentHashMap<>();     //채팅방에 접속해있는 세션 목록

    @Builder
    public ChatRoom(String roomId, Long potId, String name){
        this.roomId = roomId;
        this.potId = potId;
        this.name = name;
    }

    public void handleActions(WebSocketSession session, ChatMessage chatMessage, ChatRoomService chatRoomService){
        if(chatMessage.getType().name().equals(ChatMessage.MessageType.JOIN.name())){  // 입장한 거면
            sessions.put(session.getId(), session);
            chatMessage.setMessageData(chatMessage.getSender() + "님이 입장했습니다.");
        }
        sendMessage(session.getId(), chatMessage, chatRoomService);
    }

    private void sendMessage(String sessionId, ChatMessage message, ChatRoomService chatRoomService){
        sessions.values().parallelStream().forEach(s -> {
            try {
                if (!s.getId().equals(sessionId)) {
                    chatRoomService.sendMessage(s, message);
                }
            }catch(Exception ex){
                log.error(ex.getMessage(), ex);
            }
        });
    }
}
