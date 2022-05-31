package com.tickettogether.domain.member.repository;

import com.tickettogether.domain.member.domain.TicketSiteInfo;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SiteInfoRepository extends JpaRepository<TicketSiteInfo, Long> {
}
