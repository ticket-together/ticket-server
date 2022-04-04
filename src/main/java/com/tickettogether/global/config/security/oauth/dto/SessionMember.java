package com.tickettogether.global.config.security.oauth.dto;

import com.tickettogether.domain.member.domain.Member;
import lombok.Getter;

@Getter
public class SessionMember {
    private String name;
    private String email;
    private String picture;

    public SessionMember(Member member) {
        this.name = member.getName();
        this.email = member.getEmail();
        this.picture = member.getImgUrl();
    }
}
