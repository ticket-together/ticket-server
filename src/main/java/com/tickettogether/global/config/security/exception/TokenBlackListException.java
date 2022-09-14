package com.tickettogether.global.config.security.exception;

import com.tickettogether.global.error.ErrorCode;
import com.tickettogether.global.error.exception.AuthorityException;

public class TokenBlackListException extends AuthorityException{
    public TokenBlackListException() {
        super(ErrorCode.JWT_IN_BLACKLIST);
    }
}
