package com.tickettogether.domain.member.exception;

import com.tickettogether.global.error.ErrorCode;
import com.tickettogether.global.error.exception.EntityNotFoundException;

public class KeywordEmptyException extends EntityNotFoundException {
    public KeywordEmptyException() {
        super(ErrorCode.EMPTY_KEYWORD_ID);
    }
}
