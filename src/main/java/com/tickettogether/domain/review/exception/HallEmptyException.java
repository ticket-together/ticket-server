package com.tickettogether.domain.review.exception;

import com.tickettogether.global.error.ErrorCode;
import com.tickettogether.global.error.exception.EntityNotFoundException;

public class HallEmptyException extends EntityNotFoundException {
    public HallEmptyException() {
        super(ErrorCode.EMPTY_HALL);
    }
}
