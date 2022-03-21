package com.tickettogether.domain.member.domain;

import javax.persistence.*;

@Entity
public class SiteInfo {

    @EmbeddedId
    private SiteInfoId siteInfoId;

    @MapsId("memberId")
    @ManyToOne
    @JoinColumn(name = "member_id")
    public Member member;

    private Long ticketId;

    private String ticketPassword;
}
