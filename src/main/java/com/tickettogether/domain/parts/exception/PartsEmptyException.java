package com.tickettogether.domain.parts.exception;

import com.tickettogether.global.error.ErrorCode;
import com.tickettogether.global.error.exception.EntityNotFoundException;

public class PartsEmptyException extends EntityNotFoundException {

    public PartsEmptyException() {
        super(ErrorCode.EMPTY_PARTS);
    }
}