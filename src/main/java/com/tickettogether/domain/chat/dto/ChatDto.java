package com.tickettogether.domain.chat.dto;

import com.tickettogether.domain.chat.domain.ChatMessage;
import com.tickettogether.domain.chat.domain.ChatRoom;
import lombok.*;

import java.util.ArrayList;
import java.util.List;


@Getter
public class ChatDto {

    @Getter
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    @AllArgsConstructor
    public static class ChatEnterRequest{
        private Long roomId;
        private String roomName;
        private Long potId;
    }


    @Getter
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    @AllArgsConstructor
    @Builder
    public static class ChatEnterResponse{
        private Long roomId;
    }

    @Getter
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    @AllArgsConstructor
    @Builder
    public static class ChatMessageResponse{
        private String sender;
        private String data;
        private String createdAt;

        public void setData(String data){
            this.data = data;
        }
    }

    @Getter
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    @AllArgsConstructor
    @Builder
    public static class ChatSearchResponse{
        private Long roomId;
        private String roomName;
        private List<ChatMessageResponse> messageList = new ArrayList<>();
    }

    @Getter
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    @AllArgsConstructor
    public static class ChatStompRequest {
        private Long roomId;
        private String sender;
        private String data;
        private String type;

        public ChatMessage toEntity(ChatRoom chatRoom){
            return ChatMessage.builder()
                    .sender(sender)
                    .type(ChatMessage.MessageType.valueOf(type))
                    .data(data)
                    .chatRoom(chatRoom)
                    .build();
        }
    }
}
