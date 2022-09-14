package com.tickettogether.global.config.security.exception;

import com.tickettogether.global.error.ErrorCode;
import com.tickettogether.global.error.exception.AuthorityException;

public class TokenExpiredException extends AuthorityException {
    public TokenExpiredException() {
        super(ErrorCode.EXPIRED_TOKEN);
    }
}
