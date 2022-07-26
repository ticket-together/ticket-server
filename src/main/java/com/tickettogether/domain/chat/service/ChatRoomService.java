package com.tickettogether.domain.chat.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tickettogether.domain.chat.domain.ChatMessage;
import com.tickettogether.domain.chat.domain.ChatRoom;
import com.tickettogether.domain.chat.dto.ChatDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;

import java.time.format.DateTimeFormatter;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

@Service
@RequiredArgsConstructor
@Slf4j
public class ChatRoomService {
    private final ObjectMapper objectMapper;
    private final Map<String, ChatRoom> chatRoomMap = new ConcurrentHashMap<>();  // 모든 채팅방 정보 저장

    public ChatDto.ChatEnterResponse enterOrCreateChatRoom(ChatDto.ChatEnterRequest request){
        String randomRoomId = UUID.randomUUID().toString();        // 채팅방 새로 생성
        ChatRoom chatRoom = ChatRoom.builder()
                .roomId(randomRoomId)
                .name(request.getRoomName())
                .potId(request.getPotId()).build();
        chatRoomMap.put(randomRoomId, chatRoom);
        return ChatDto.ChatEnterResponse.builder().roomId(randomRoomId).build();
    }

    public void sendMessage(WebSocketSession session, ChatMessage chatMessage){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        try{
            if(session != null && session.isOpen()){
                ChatDto.ChatMessageResponse<Object> chatDto = ChatDto.ChatMessageResponse.builder()
                        .sender(chatMessage.getSender())
                        .data(chatMessage.getData())
                        .createdAt(chatMessage.getCreatedAt().format(formatter)).build();

                session.sendMessage(new TextMessage(objectMapper.writeValueAsString(chatDto)));
            }
        }catch(Exception ex){
            log.error(ex.getMessage(), ex);
        }
    }

    public ChatDto.ChatSearchResponse getChatListByRoomId(String roomId){
        ChatRoom chatRoom = chatRoomMap.get(roomId);
        return ChatDto.ChatSearchResponse.builder()
                .roomId(roomId)
                .roomName(chatRoom.getName())
                .sessionList(chatRoom.getSessions().keySet()).build();
    }

    public ChatRoom getChatRoomById(String roomId){
        return chatRoomMap.get(roomId);
    }
}
