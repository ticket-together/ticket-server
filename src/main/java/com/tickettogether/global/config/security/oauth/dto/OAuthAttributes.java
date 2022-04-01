package com.tickettogether.global.config.security.oauth.dto;

import com.tickettogether.domain.member.domain.Member;
import com.tickettogether.domain.member.domain.Role;
import lombok.*;

import java.util.Map;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class OAuthAttributes {
    private Map<String, Object> attributes;
    private String nameKey;   //pk
    private String nickName;
    private String email;
    private String imgUrl;

    @Builder
    public OAuthAttributes(Map<String, Object> attributes,
                           String nameKey, String nickName, String email, String imgUrl){
        this.attributes = attributes;
        this.nameKey = nameKey;
        this.nickName = nickName;
        this.email = email;
        this.imgUrl = imgUrl;
    }

    public Member toEntity(){
        return Member.builder()
                .name(nickName)
                .email(email)
                .imgUrl(imgUrl)
                .role(Role.USER)
                .build();
    }
}
