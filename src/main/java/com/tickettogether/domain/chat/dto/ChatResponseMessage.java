package com.tickettogether.domain.chat.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ChatResponseMessage {

    CREATE_CHATROOM_SUCCESS("채팅방을 생성하는데 성공했습니다."),
    GET_CHATROOM_SUCCESS("채팅 목록을 가져오는데 성공했습니다."),

    QUIT_CHATROOM_SUCCESS("채팅방을 나가는데 성공하였습니다.");


    private final String message;
}
