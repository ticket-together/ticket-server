package com.tickettogether.global.config.security.exception;

import com.tickettogether.global.error.ErrorCode;
import com.tickettogether.global.error.exception.InvalidValueException;

public class SecretKeyException extends InvalidValueException {
    public SecretKeyException() {
        super(ErrorCode.INVALID_SECRET_KEY);
    }
}
