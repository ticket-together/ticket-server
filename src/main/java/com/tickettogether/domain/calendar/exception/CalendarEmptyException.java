package com.tickettogether.domain.calendar.exception;

import com.tickettogether.global.error.ErrorCode;
import com.tickettogether.global.error.exception.EntityNotFoundException;

public class CalendarEmptyException extends EntityNotFoundException {
    public CalendarEmptyException() {
        super(ErrorCode.EMPTY_CALENDAR_ID);
    }
}
