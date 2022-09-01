package com.tickettogether.domain.parts.exception;

import com.tickettogether.global.error.ErrorCode;
import com.tickettogether.global.error.exception.BusinessException;

public class PartsCloseDeniedException extends BusinessException {
    public PartsCloseDeniedException() {
        super(ErrorCode.PARTS_CLOSE_DENIED);
    }
}