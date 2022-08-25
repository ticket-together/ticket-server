package com.tickettogether.domain.reservation.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ReservationResponseMessage{

    GET_RESERVATIONS_SUCCESS("예약 목록 조회를 완료했습니다."),
    POST_TICKET_SITE_SUCCESS("티켓 사이트 정보 등록을 완료했습니다."),
    UPDATE_TICKET_SITE_SUCCESS("티켓 사이트 정보 수정을 완료했습니다."),
    GET_TICKET_SITE_SUCCESS("티켓 사이트 정보 조회를 완료했습니다."),
    DELETE_TICKET_SITE_SUCCESS("티켓 사이트 정보 삭제를 완료했습니다.");

    private final String message;
}
