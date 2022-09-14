package com.tickettogether.global.error;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;

/**
 * 에러 코드 관리
 */
@JsonFormat(shape = JsonFormat.Shape.OBJECT)
@Getter
public enum ErrorCode {

    // Common
    REQUEST_ERROR(2000, "입력값을 확인해주세요."),
    EMPTY_JWT(2001, "JWT를 입력해주세요."),
    INVALID_JWT(2002, "유효하지 않은 JWT 입니다."),
    INVALID_REFRESH_JWT(2003, "유효하지 않은 리프레시 토큰입니다."),
    INVALID_USER_JWT(2003,"권한이 없는 유저의 접근입니다."),
    FAIL_UPLOAD_FILE( 2004, "파일 업로드에 실패하셨습니다."),
    EMPTY_FILE(2005, "파일을 확인해 주세요."),
    FILE_SIZE_EXCEED(2006, "파일 사이즈를 확인해 주세요."),
    INTERNAL_SERVER_ERROR(5000, "서버 오류입니다. "),
    METHOD_NOT_ALLOWED(4000, "요청 방식이 잘못되었습니다."),
    INVALID_SECRET_KEY(2007, "유효하지 않은 시크릿 키입니다."),
    JWT_IN_BLACKLIST(2008, "블랙리스트에 의해 차단된 액세스 토큰입니다."),
    EXPIRED_TOKEN(2009, "기한이 만료된 토큰입니다."),

    /**
     * calender
     */
    POST_CALENDAR_ERROR(2020, "최대 캘린더 개수를 초과하였습니다."),
    EMPTY_CALENDAR_ID(2021, "존재하지 않는 캘린더입니다."),

    /**
     * culture
     */

    EMPTY_SITE(2022, "존재하지 않는 사이트 정보입니다."),
    POST_SIZE_ERROR(2020, "최대 사이트 아이디 개수를 초과하였습니다."),
    EMPTY_SITE_INFO(2023, "존재하지 않는 정보입니다."),
    UPDATE_SITE_FAIL(2024, "사이트 정보 수정에 실패하였습니다."),

    /**
     * member
     */
    USERS_EMPTY_USER_ID(2010, "유저 아이디 값을 확인해주세요."),
    POST_USERS_EMPTY_EMAIL( 2015, "이메일을 입력해주세요."),
    POST_USERS_INVALID_EMAIL(2016, "이메일 형식을 확인해주세요."),
    POST_USERS_EXISTS_EMAIL(2017,"중복된 이메일입니다."),
    DUPLICATED_EMAIL( 3013, "중복된 이메일입니다."),
    FAILED_TO_LOGIN(3014,"없는 아이디거나 비밀번호가 틀렸습니다."),
    MODIFY_FAIL_USERNAME(4014,"유저 이름 수정에 실패하였습니다."),

    PASSWORD_ENCRYPTION_ERROR(4011, "비밀번호 암호화에 실패하였습니다."),
    PASSWORD_DECRYPTION_ERROR( 4012, "비밀번호 복호화에 실패하였습니다."),

    EMPTY_USER_ID(2011, "존재하지 않는 회원입니다."),
    EMPTY_KEYWORD_ID(2012, "존재하지 않는 키워드입니다."),


    /**
     * parts
     */
    EMPTY_PARTS_ID(3040, "팟이 존재하지 않습니다."),
    PARTS_JOIN_DENIED(3041, "해당 팟에 이미 참여되어있습니다."),
    PARTS_CLOSE_DENIED(3042, "해당 팟 마감에 대한 권한이 없습니다."),
    PARTS_DELETE_DENIED(3042, "해당 팟 삭제에 대한 권한이 없습니다."),
    PARTS_FULL(3044, "해당 팟의 인원이 모두 찼습니다."),
    PARTS_LEAVE_DENIED(3045, "해당 팟에서 나갈 권한이 없습니다."),


    /**
     * reviews
     */
    EMPTY_HALL(3030, "해당하는 공연장이 없습니다."),
    EMPTY_REVIEW(3031, "리뷰가 존재하지 않습니다."),


    /**
     * chats
     */
    EMPTY_ROOM_ID(3040, "존재하지 않는 채팅방입니다.");

    private final String message;
    private final int status;

    ErrorCode(final int status, final String message) {
        this.status = status;
        this.message = message;
    }
}
