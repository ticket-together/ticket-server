package com.tickettogether.domain.chat.controller;

import com.tickettogether.domain.chat.dto.ChatDto;
import com.tickettogether.domain.chat.service.ChatRoomServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

@Controller
@RequiredArgsConstructor
public class StompChatController {
    
    private final ChatRoomServiceImpl chatRoomService;

    private final SimpMessagingTemplate template;

    @MessageMapping(value = "/chat/message")
    public void message(ChatDto.ChatStompRequest message){
        ChatDto.ChatMessageResponse sendMessage = chatRoomService.createChatMessageOrSave(message);
        template.convertAndSend("/sub/chat/room/" + message.getRoomId(), sendMessage);
    }
}
