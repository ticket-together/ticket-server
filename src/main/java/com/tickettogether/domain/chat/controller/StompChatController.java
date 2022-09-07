package com.tickettogether.domain.chat.controller;

import com.tickettogether.domain.chat.dto.ChatSendEvent;
import com.tickettogether.global.config.redis.util.RedisUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.stereotype.Controller;

import java.time.LocalDateTime;
import java.util.Map;
import java.util.Objects;

import static com.tickettogether.domain.chat.dto.ChatDto.*;

@Controller
@RequiredArgsConstructor
@Slf4j
public class StompChatController {

    private final RedisUtil<String, String> redisUtil;

    private final ApplicationEventPublisher applicationEventPublisher;

    @MessageMapping("chat.enter.{roomId}")
    @SendTo("/topic/room.{roomId}")
    public ChatMessageResponse enter(@Payload ChatStompRequest message,
                                     @DestinationVariable String roomId, SimpMessageHeaderAccessor headerAccessor){
        setSessionAttributes(message.getSender(), roomId, headerAccessor);
        ChatMessageResponse sendEnterMessage = ChatMessageResponse.create(message, Long.parseLong(roomId));

        Map<String, String> enterMemberList = redisUtil.getHashKeys(roomId);
        if (enterMemberList.containsKey(message.getSender())) return null;
        redisUtil.setValue(roomId, message.getSender(), LocalDateTime.now().toString());

        sendEnterMessage.setData(message.getSender() + "님이 들어왔습니다.");
        applicationEventPublisher.publishEvent(new ChatSendEvent(sendEnterMessage));
        return sendEnterMessage;
    }

    @MessageMapping("chat.message.{roomId}")
    @SendTo("/topic/room.{roomId}")
    public ChatMessageResponse message(@Payload ChatStompRequest message,
                                       @DestinationVariable String roomId, SimpMessageHeaderAccessor headerAccessor){
        setSessionAttributes(message.getSender(), roomId, headerAccessor);
        ChatMessageResponse sendChatMessage = ChatMessageResponse.create(message, Long.parseLong(roomId), LocalDateTime.now());
        applicationEventPublisher.publishEvent(new ChatSendEvent(sendChatMessage));
        return sendChatMessage;
    }

    private void setSessionAttributes(String username, String roomId, SimpMessageHeaderAccessor headerAccessor){
        Objects.requireNonNull(headerAccessor.getSessionAttributes());
        headerAccessor.getSessionAttributes().put("username", username);
        headerAccessor.getSessionAttributes().put("roomId", roomId);
    }
}
