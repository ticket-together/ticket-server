package com.tickettogether.domain.chat.listener;

import com.tickettogether.domain.chat.dto.ChatSendEvent;
import com.tickettogether.domain.chat.service.ChatRoomServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ChatMessageEventListener {
    private final ChatRoomServiceImpl chatRoomService;

    @EventListener
    @Async
    public void receiveMessage(final ChatSendEvent chatSendEvent){
        chatRoomService.saveChatMessage(chatSendEvent.getResponse());
    }
}
