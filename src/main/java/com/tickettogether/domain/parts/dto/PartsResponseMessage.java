package com.tickettogether.domain.parts.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum PartsResponseMessage {

    SAVE_PARTS_SUCCESS("팟 생성을 완료했습니다."),
    GET_PARTS_SUCCESS("팟 조회를 완료했습니다."),
    CLOSE_PARTS_SUCCESS("팟 마감을 완료했습니다."),
    DELETE_PARTS_SUCCESS("팟 삭제를 완료했습니다.");

    private final String message;

}
