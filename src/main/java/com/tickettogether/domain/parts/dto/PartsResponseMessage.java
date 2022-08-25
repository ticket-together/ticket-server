package com.tickettogether.domain.parts.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum PartsResponseMessage {

    SAVE_PARTS_SUCCESS("팟 생성을 완료했습니다.");

    private final String message;

}
