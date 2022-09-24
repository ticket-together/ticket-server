package com.tickettogether.global.config.security.exception;

import com.tickettogether.global.error.ErrorCode;
import com.tickettogether.global.error.exception.EntityNotFoundException;

public class TokenEmptyException extends EntityNotFoundException {

    public TokenEmptyException() {
        super(ErrorCode.EMPTY_TOKEN);
    }
}
