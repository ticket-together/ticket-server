package com.tickettogether.domain.member.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum MemberResponseMessage {
    SAVE_MEMBER_SUCCESS("유저 정보 저장을 완료했습니다."),
    GET_PROFILE_SUCCESS("유저 프로필 정보 가져오기를 완료했습니다."),
    UPDATE_PROFILE_SUCCESS("유저 프로필 수정을 완료했습니다.");

    private final String message;
}

