package com.tickettogether.domain.parts.exception;

import com.tickettogether.global.error.ErrorCode;
import com.tickettogether.global.error.exception.BusinessException;

public class PartsJoinDeniedException extends BusinessException {

    public PartsJoinDeniedException() {
        super(ErrorCode.PARTS_JOIN_DENIED);
    }
}
