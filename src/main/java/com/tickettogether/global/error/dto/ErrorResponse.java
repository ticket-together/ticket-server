package com.tickettogether.global.error.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.tickettogether.global.error.ErrorCode;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import java.util.Map;

@Getter
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ErrorResponse {
    private final int status;
    private String errorMessage;
    private Map<String, String> errors;
    private ErrorResponse(final ErrorCode code) {
        this.status = code.getStatus();
        this.errorMessage = code.getMessage();
    }

    private ErrorResponse(final ErrorCode code, Map<String, String> errors) {
        this.status = code.getStatus();
        this.errorMessage = code.getMessage();
        this.errors = errors;
    }

    public static ErrorResponse create(final ErrorCode code){
        return new ErrorResponse(code);
    }

    public static ErrorResponse create(final ErrorCode code, Map<String, String> errors){
        return new ErrorResponse(code, errors);
    }
}

