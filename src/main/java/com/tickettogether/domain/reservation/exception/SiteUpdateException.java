package com.tickettogether.domain.reservation.exception;

import com.tickettogether.global.error.ErrorCode;
import com.tickettogether.global.error.exception.BusinessException;

public class SiteUpdateException extends BusinessException {
    public SiteUpdateException() {
        super(ErrorCode.UPDATE_SITE_FAIL);
    }
}
