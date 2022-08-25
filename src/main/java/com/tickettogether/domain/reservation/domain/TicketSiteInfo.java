package com.tickettogether.domain.reservation.domain;

import com.tickettogether.domain.member.domain.Member;
import com.tickettogether.domain.reservation.dto.ReservationDto;
import lombok.*;

import javax.persistence.*;

@Entity
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Getter
public class TicketSiteInfo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "site_info_id")
    private Long id;

    private String ticketId;

    private String ticketPassword;

    @Enumerated(EnumType.STRING)
    private TicketSite ticketSite;

    @ManyToOne
    @JoinColumn(name = "member_id")
    public Member member;

    public TicketSiteInfo updateTicketSiteInfo(ReservationDto.SiteInfoPostRequest siteInfo) {
        this.ticketId = siteInfo.getId();
        this.ticketPassword = siteInfo.getPassword();
        return this;
    }
}
