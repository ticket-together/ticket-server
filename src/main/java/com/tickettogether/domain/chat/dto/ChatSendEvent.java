package com.tickettogether.domain.chat.dto;

import lombok.Getter;

import static com.tickettogether.domain.chat.dto.ChatDto.*;

@Getter
public class ChatSendEvent {
    private final ChatMessageResponse response;

    public ChatSendEvent(ChatMessageResponse response){
        this.response = response;
    }
}
