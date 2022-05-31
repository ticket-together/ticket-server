package com.tickettogether.domain.reservation.service;

import com.tickettogether.domain.member.domain.Member;
import com.tickettogether.domain.reservation.domain.TicketSite;
import com.tickettogether.domain.reservation.dto.ReservationDto;

import java.util.List;

public interface ReservationService {
    List<ReservationDto.GetResponse> getReservations(Member member);
    ReservationDto.SiteInfoGetResponse postSiteInfo(ReservationDto.SiteInfoPostRequest siteInfo, Long memberId);
    ReservationDto.SiteInfoGetResponse getSiteInfo(Long memberId, TicketSite ticketSite);
}
