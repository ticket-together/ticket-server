package com.tickettogether.domain.member.exception;

import com.tickettogether.global.error.ErrorCode;
import com.tickettogether.global.error.exception.BusinessException;

public class SiteBusinessException extends BusinessException {
    public SiteBusinessException() {
        super(ErrorCode.POST_SIZE_ERROR);
    }
}