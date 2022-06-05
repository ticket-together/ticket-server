package com.tickettogether.domain.reservation.exception;

import com.tickettogether.global.error.ErrorCode;
import com.tickettogether.global.error.exception.EntityNotFoundException;

public class SiteEmptyException extends EntityNotFoundException {
    public SiteEmptyException() {
        super(ErrorCode.EMPTY_SITE);
    }
}