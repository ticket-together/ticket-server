package com.tickettogether.global.config.security.exception;

import com.tickettogether.global.error.ErrorCode;
import com.tickettogether.global.error.exception.AuthorityException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

public class TokenValidFailedException extends AuthorityException {
    public TokenValidFailedException() {
        super(ErrorCode.INVALID_JWT);
    }

    public TokenValidFailedException(String message) {
        super(message, ErrorCode.INVALID_JWT);
    }
}
