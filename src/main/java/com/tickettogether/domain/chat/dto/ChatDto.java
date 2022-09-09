package com.tickettogether.domain.chat.dto;

import com.tickettogether.domain.chat.domain.ChatMessage;
import com.tickettogether.domain.chat.domain.ChatRoom;
import com.tickettogether.global.dto.PageDto;
import lombok.*;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import static com.tickettogether.domain.chat.domain.ChatMessage.*;
import static com.tickettogether.global.common.Constant.*;


@Getter
public class ChatDto {

    @Getter
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    @AllArgsConstructor
    public static class ChatEnterRequest{
        private Long roomId;
        private String roomName;
        private Long partsId;
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
        private Long roomId;
        private String sender;
        private String data;
        private String createdAt;
        private String type;

        public static ChatMessageResponse create(ChatMessage x){
            return chatMessageResponseBuilder(null, x.getSender(), x.getData(), x.getType().toString(), x.getCreatedAt());
        }

        public static ChatMessageResponse create(ChatStompRequest x, Long roomId){
            return chatMessageResponseBuilder(roomId, x.getSender(), x.getData(), x.getType(), null);
        }
        public static ChatMessageResponse create(ChatStompRequest x, Long roomId, LocalDateTime createdAt){
            return chatMessageResponseBuilder(roomId, x.getSender(), x.getData(), x.getType(), createdAt);
        }

        public void setData(String data){
            this.data = data;
        }

        private static ChatMessageResponse chatMessageResponseBuilder(Long roomId, String sender, String data, String type, LocalDateTime time){
            String createdAt = null;
            if (time != null) createdAt = time.format(DateTimeFormatter.ofPattern(DATETIME_FORMAT_PATTERN));

            return ChatDto.ChatMessageResponse.builder()
                    .roomId(roomId)
                    .sender(sender)
                    .data(data)
                    .type(type)
                    .createdAt(createdAt).build();
        }

        public ChatMessage toEntity(ChatRoom chatRoom){
            return ChatMessage.builder()
                    .chatRoom(chatRoom)
                    .sender(sender)
                    .type(MessageType.valueOf(MessageType.class, type))
                    .data(data)
                    .chatRoom(chatRoom)
                    .createdAt(LocalDateTime.parse(createdAt, DateTimeFormatter.ofPattern(DATETIME_FORMAT_PATTERN)))
                    .build();
        }
    }

    @Getter
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    @AllArgsConstructor
    @Builder
    public static class ChatSearchResponse{
        private Long roomId;
        private String roomName;
        private PageDto<ChatMessageResponse> messageList;

        public static ChatSearchResponse create(Long roomId, String roomName, PageDto<ChatMessageResponse> messageList){
            return ChatDto.ChatSearchResponse.builder()
                    .roomId(roomId)
                    .roomName(roomName)
                    .messageList(messageList).build();
        }
    }

    @Getter
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    @AllArgsConstructor
    public static class ChatStompRequest {
        private String sender;
        private String data;
        private String type;

        public ChatMessage toEntity(ChatRoom chatRoom){
            return builder()
                    .sender(sender)
                    .type(MessageType.valueOf(type))
                    .data(data)
                    .chatRoom(chatRoom)
                    .build();
        }
    }
}
