package com.tickettogether.domain.calendar.exception;

import com.tickettogether.global.error.ErrorCode;
import com.tickettogether.global.error.exception.BusinessException;

public class CalendarBusinessException extends BusinessException {

    public CalendarBusinessException() {
        super(ErrorCode.POST_CALENDAR_ERROR);
    }
}
