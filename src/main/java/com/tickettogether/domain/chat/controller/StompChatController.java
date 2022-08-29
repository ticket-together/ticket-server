package com.tickettogether.domain.chat.controller;

import com.tickettogether.domain.chat.service.ChatRoomServiceImpl;
import com.tickettogether.global.common.Constant;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.stereotype.Controller;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Objects;

import static com.tickettogether.domain.chat.dto.ChatDto.*;

@Controller
@RequiredArgsConstructor
@Slf4j
public class StompChatController {

    private final RabbitTemplate rabbitTemplate;

    @MessageMapping("chat.enter.{roomId}")
    @SendTo("/topic/room.{roomId}")
    public ChatMessageResponse enter(@Payload ChatStompRequest message, @DestinationVariable String roomId, SimpMessageHeaderAccessor headerAccessor){
        Objects.requireNonNull(headerAccessor.getSessionAttributes()).put("username", message.getSender());
        Objects.requireNonNull(headerAccessor.getSessionAttributes()).put("roomId", roomId);

        ChatMessageResponse sendEnterMessage = ChatMessageResponse.create(message, Long.parseLong(roomId));
        sendEnterMessage.setData(message.getSender() + "님이 들어왔습니다.");
        rabbitTemplate.convertAndSend(Constant.CHAT_QUEUE_NAME, sendEnterMessage);
        return sendEnterMessage;
    }

    @MessageMapping("chat.message.{roomId}")
    @SendTo("/topic/room.{roomId}")
    public ChatMessageResponse message(@Payload ChatStompRequest message, @DestinationVariable String roomId){
        ChatMessageResponse sendChatMessage = ChatMessageResponse.create(message, Long.parseLong(roomId), LocalDateTime.now());
        rabbitTemplate.convertAndSend(Constant.CHAT_QUEUE_NAME, sendChatMessage);
        return sendChatMessage;
    }
}
