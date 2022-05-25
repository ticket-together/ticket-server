package com.tickettogether.domain.calendar.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum CalendarResponseMessage {

    GET_CALENDARS_SUCCESS("캘린더 조회를 완료했습니다."),
    POST_CALENDAR_SUCCESS("캘린더 추가를 완료했습니다."),
    UPDATE_CALENDAR_SUCCESS("캘린더 수정을 완료했습니다."),
    DELETE_CALENDAR_SUCCESS("캘린더 삭제를 성공하였습니다.");

    private final String message;
}
