package com.tickettogether.domain.chat.listener;

import com.tickettogether.domain.chat.dto.ChatDto;
import com.tickettogether.domain.chat.service.ChatRoomServiceImpl;
import com.tickettogether.global.common.Constant;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import static com.tickettogether.domain.chat.domain.ChatMessage.*;

@Slf4j
@Component
@RequiredArgsConstructor
public class ChatMessageListener {

    private final ChatRoomServiceImpl chatRoomService;

    @RabbitListener(queues = Constant.CHAT_QUEUE_NAME)
    public void receiveMessage(ChatDto.ChatMessageResponse chatMessage){
        if (MessageType.CHAT.name().equals(chatMessage.getType())){
            chatRoomService.saveChatMessage(chatMessage);
        }
    }
}
