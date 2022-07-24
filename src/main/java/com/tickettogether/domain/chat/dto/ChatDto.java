package com.tickettogether.domain.chat.dto;

import lombok.*;

import java.util.HashSet;
import java.util.Set;

@Getter
public class ChatDto {

    @Getter
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    @AllArgsConstructor
    public static class ChatEnterRequest{
        private String roomId;
        private String roomName;
        private Long potId;
    }


    @Getter
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    @AllArgsConstructor
    @Builder
    public static class ChatEnterResponse{
        private String roomId;
    }

    @Getter
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    @AllArgsConstructor
    @Builder
    public static class ChatMessageResponse<T>{
        private String sender;
        private T data;
        private String createdAt;
    }

    @Getter
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    @AllArgsConstructor
    @Builder
    public static class ChatSearchResponse{
        private String roomId;
        private String roomName;
        private Set<String> sessionList = new HashSet<>();
    }
}
