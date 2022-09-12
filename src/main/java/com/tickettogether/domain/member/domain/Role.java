package com.tickettogether.domain.member.domain;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.Arrays;

@Getter
@RequiredArgsConstructor
public enum Role {

    GUEST("ROLE_GUEST", "손님"),     //최초 가입
    ADMIN("ROLE_ADMIN", "관리자"),
    USER("ROLE_USER", "일반 사용자"),

    PART_MANAGER("PART_MANAGER", "방장"),  // 팟 관련 role
    PART_MEMBER("PART_MEMBER", "팟 멤버"),
    PART_USER("PART_UNRELATED", "팟에 참여되지 않은 사용자");

    private final String key;
    private final String title;

    public static Role of(String key) {
        return Arrays.stream(Role.values())
                .filter(r -> r.getKey().equals(key))
                .findAny()
                .orElse(GUEST);
    }
}
