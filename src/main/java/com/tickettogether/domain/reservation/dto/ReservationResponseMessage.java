package com.tickettogether.domain.reservation.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ReservationResponseMessage{

    GET_RESERVATIONS_SUCCESS("예약 목록 조회를 완료했습니다.");

    private final String message;
}
