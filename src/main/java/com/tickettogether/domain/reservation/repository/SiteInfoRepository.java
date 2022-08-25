package com.tickettogether.domain.reservation.repository;

import com.tickettogether.domain.member.domain.Member;
import com.tickettogether.domain.reservation.domain.TicketSite;
import com.tickettogether.domain.reservation.domain.TicketSiteInfo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface SiteInfoRepository extends JpaRepository<TicketSiteInfo, Long> {
    int countTicketSiteInfoByMemberAndTicketSite(Member member, TicketSite ticketSite);
    Optional<TicketSiteInfo> findByMemberAndTicketSite(Member member, TicketSite ticketSite);
    Optional<TicketSiteInfo> findByMemberAndId(Member member, Long id);
}
