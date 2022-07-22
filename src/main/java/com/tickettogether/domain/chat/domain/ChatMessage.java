package com.tickettogether.domain.chat.domain;

import lombok.*;

@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class ChatMessage {
    private MessageType type;
    private String sender;
    private String receiver;
    private Object data;

    public enum MessageType{
        CHAT,
        JOIN,
        LEAVE
    }

    public void setSender(String sender){
        this.sender = sender;
    }

    public void newConnect(){
        this.type = MessageType.JOIN;
    }

    public void closeConnect(){
        this.type = MessageType.LEAVE;
    }
}
