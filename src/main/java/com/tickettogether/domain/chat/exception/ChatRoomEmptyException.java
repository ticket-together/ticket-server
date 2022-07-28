package com.tickettogether.domain.chat.exception;

import com.tickettogether.global.error.ErrorCode;
import com.tickettogether.global.error.exception.EntityNotFoundException;

public class ChatRoomEmptyException extends EntityNotFoundException {

    public ChatRoomEmptyException() {
        super(ErrorCode.EMPTY_ROOM_ID);
    }
}
