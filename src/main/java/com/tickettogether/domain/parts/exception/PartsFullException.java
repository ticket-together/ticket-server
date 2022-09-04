package com.tickettogether.domain.parts.exception;

import com.tickettogether.global.error.ErrorCode;
import com.tickettogether.global.error.exception.BusinessException;

public class PartsFullException extends BusinessException {
    public PartsFullException() {
        super(ErrorCode.PARTS_FULL);
    }
}