package com.tickettogether.global.config.security.exception;

import com.tickettogether.global.error.ErrorCode;
import com.tickettogether.global.error.exception.AuthorityException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

public class TokenRefreshException extends AuthorityException {
    public TokenRefreshException(){
        super(ErrorCode.INVALID_REFRESH_JWT);
    }

    public TokenRefreshException(String message){
        super(message, ErrorCode.INVALID_REFRESH_JWT);
    }
}
