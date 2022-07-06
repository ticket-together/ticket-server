package com.tickettogether.domain.culture.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum CultureResponseMessage {
    GET_CALENDARS_SUCCESS("공연장 조회를 완료했습니다.");
    private final String message;
}