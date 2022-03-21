package com.tickettogether.domain.member.domain;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;

@Embeddable
public class SiteInfoId implements Serializable {

    private Long memberId;      //@MapsId()

    @Column(name="siteInfo_id")
    private Long id;
}
