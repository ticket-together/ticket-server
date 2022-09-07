package com.tickettogether.domain.parts.exception;

import com.tickettogether.global.error.ErrorCode;
import com.tickettogether.global.error.exception.BusinessException;

public class PartsLeaveDeniedException extends BusinessException {
    public PartsLeaveDeniedException() {
        super(ErrorCode.PARTS_LEAVE_DENIED);
    }
}