package com.tickettogether.domain.chat.domain;

import lombok.*;
import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
@Builder
@AllArgsConstructor
public class ChatMessage {

    private String roomId;
    private MessageType type;
    private String sender;

    private Object data;

    private LocalDateTime createdAt;

    public enum MessageType{
        CHAT,
        JOIN,
        LEAVE
    }

    public void setMessageData(Object data){
        this.data = data;
    }
}
