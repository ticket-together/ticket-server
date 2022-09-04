package com.tickettogether.domain.parts.exception;

import com.tickettogether.global.error.ErrorCode;
import com.tickettogether.global.error.exception.BusinessException;

public class PartsDeleteDeniedException extends BusinessException {
    public PartsDeleteDeniedException() {
        super(ErrorCode.PARTS_DELETE_DENIED);
    }
}