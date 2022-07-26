package com.tickettogether.domain.chat.service;

import com.tickettogether.domain.chat.domain.ChatRoom;
import com.tickettogether.domain.chat.dto.ChatDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;


import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Service
@RequiredArgsConstructor
@Slf4j
public class ChatRoomService {
    private final Map<String, ChatRoom> chatRoomMap = new ConcurrentHashMap<>();  // 모든 채팅방 정보 저장

    public ChatDto.ChatEnterResponse createChatRoom(ChatDto.ChatEnterRequest request){
        ChatRoom chatRoom = ChatRoom.builder()
                        .name(request.getRoomName())
                        .potId(request.getPotId()).build();

        chatRoomMap.put(chatRoom.getId().toString(), chatRoom);
        return ChatDto.ChatEnterResponse.builder().roomId(chatRoom.getId().toString()).build();
    }

    public ChatDto.ChatSearchResponse getChatListByRoomId(String roomId){
        ChatRoom chatRoom = getChatRoomById(roomId);
        return ChatDto.ChatSearchResponse.builder()
                .roomId(roomId)
                .roomName(chatRoom.getName()).build();
    }

    public ChatRoom getChatRoomById(String roomId){
        return chatRoomMap.get(roomId);
    }
}
