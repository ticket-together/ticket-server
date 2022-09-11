package com.tickettogether.domain.member.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum MemberResponseMessage {
    SAVE_MEMBER_SUCCESS("유저 정보 저장을 완료했습니다."),
    GET_PROFILE_SUCCESS("유저 프로필 정보 가져오기를 완료했습니다."),
    UPDATE_PROFILE_SUCCESS("유저 프로필 수정을 완료했습니다."),
    REFRESH_ISSUE_SUCCESS("리프레시 토큰 발급을 완료했습니다."),
    LOGOUT_SUCCESS("로그아웃을 완료했습니다.");

    private final String message;
}

