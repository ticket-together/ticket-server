package com.tickettogether.domain.review.exception;

import com.tickettogether.global.error.ErrorCode;
import com.tickettogether.global.error.exception.EntityNotFoundException;

public class ReviewEmptyException extends EntityNotFoundException {

    public ReviewEmptyException() {
        super(ErrorCode.EMPTY_REVIEW);
    }
}