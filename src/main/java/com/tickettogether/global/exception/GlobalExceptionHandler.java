package com.tickettogether.global.exception;

import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.multipart.MaxUploadSizeExceededException;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(MaxUploadSizeExceededException.class)
    protected BaseResponse handleMaxUploadSizeExceededException (MaxUploadSizeExceededException e) {
        return new BaseResponse(BaseResponseStatus.FILE_SIZE_EXCEED);
    }
}
