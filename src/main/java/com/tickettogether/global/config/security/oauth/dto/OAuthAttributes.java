package com.tickettogether.global.config.security.oauth.dto;

import com.tickettogether.domain.member.domain.Member;
import com.tickettogether.domain.member.domain.Role;
import lombok.*;

import java.util.Map;

@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class OAuthAttributes {

    private String registerId;

    private Map<String, Object> attributes;

    private String nameKey;   //pk

    private String nickName;

    private String email;

    private String imgUrl;

    private String phoneNumber;

    public Member toEntity(){
        return Member.builder()
                .name(nickName)
                .email(email)
                .imgUrl(imgUrl)
                .phoneNumber(phoneNumber)
                .role(Role.USER)
                .build();
    }
}
