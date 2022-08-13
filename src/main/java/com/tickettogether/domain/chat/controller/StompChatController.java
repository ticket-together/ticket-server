package com.tickettogether.domain.chat.controller;

import com.tickettogether.domain.chat.service.ChatRoomServiceImpl;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.stereotype.Controller;

import java.util.Objects;

import static com.tickettogether.domain.chat.dto.ChatDto.*;

@Controller
@RequiredArgsConstructor
@Slf4j
public class StompChatController {

    private final RabbitTemplate rabbitTemplate;
    
    private final ChatRoomServiceImpl chatRoomService;

    private static final String CHAT_QUEUE_NAME = "chat.queue";

    @MessageMapping("chat.enter.{roomId}")
    @SendTo("/topic/room.{roomId}")
    public ChatMessageResponse enter(@Payload ChatStompRequest message, @DestinationVariable String roomId, SimpMessageHeaderAccessor headerAccessor){
        Objects.requireNonNull(headerAccessor.getSessionAttributes()).put("username", message.getSender());
        ChatMessageResponse sendMessage = chatRoomService.createChatMessageOrSave(message, Long.parseLong(roomId));
        rabbitTemplate.convertAndSend(CHAT_QUEUE_NAME, sendMessage);
        return sendMessage;
    }

    @MessageMapping("chat.message.{roomId}")
    @SendTo("/topic/room.{roomId}")
    public ChatMessageResponse message(@Payload ChatStompRequest message, @DestinationVariable String roomId){
        ChatMessageResponse sendMessage = chatRoomService.createChatMessageOrSave(message, Long.parseLong(roomId));
        rabbitTemplate.convertAndSend(CHAT_QUEUE_NAME, sendMessage);
        return sendMessage;
    }
}
