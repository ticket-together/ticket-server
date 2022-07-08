package com.tickettogether.domain.culture.exception;

import com.tickettogether.global.error.ErrorCode;
import com.tickettogether.global.error.exception.EntityNotFoundException;

public class CultureEmptyException extends EntityNotFoundException {
    public CultureEmptyException() {
        super(ErrorCode.EMPTY_SITE_INFO);
    }
}