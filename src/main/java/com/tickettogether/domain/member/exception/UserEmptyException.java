package com.tickettogether.domain.member.exception;

import com.tickettogether.global.error.ErrorCode;
import com.tickettogether.global.error.exception.EntityNotFoundException;

public class UserEmptyException extends EntityNotFoundException {
    public UserEmptyException() {
        super(ErrorCode.EMPTY_USER_ID);
    }
}
