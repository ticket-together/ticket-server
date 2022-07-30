package com.tickettogether.domain.chat.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ChatResponseMessage {
    ENTER_CHATROOM_SUCCESS("채팅방에 입장하는데 성공했습니다."),
    GET_CHATROOM_SUCCESS("채팅 목록을 가져오는데 성공했습니다.");

    private final String message;
}
