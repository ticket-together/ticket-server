package com.tickettogether.domain.review.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ReviewResponseMessage {
    SAVE_REVIEW_SUCCESS("리뷰 저장을 완료했습니다."),
    GET_REVIEW_SUCCESS("리뷰 조회를 완료했습니다."),
    MODIFY_REVIEW_SUCCESS("리뷰 수정을 완료했습니다."),
    DELETE_REVIEW_SUCCESS("리뷰 삭제를 완료했습니다.");

    private final String message;
}
