package com.tickettogether.global.error.exception;

import com.tickettogether.global.error.ErrorCode;

public class UserStatusException extends BaseException{
    public UserStatusException(ErrorCode errorCode){ super(errorCode);}
}
