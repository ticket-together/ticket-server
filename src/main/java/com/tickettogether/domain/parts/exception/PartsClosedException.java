package com.tickettogether.domain.parts.exception;

import com.tickettogether.global.error.ErrorCode;
import com.tickettogether.global.error.exception.BusinessException;

public class PartsClosedException extends BusinessException {
    public PartsClosedException() {
        super(ErrorCode.PARTS_CLOSED);
    }
}
