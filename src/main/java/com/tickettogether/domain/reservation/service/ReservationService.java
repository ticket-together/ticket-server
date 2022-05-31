package com.tickettogether.domain.reservation.service;

import com.tickettogether.domain.member.domain.Member;
import com.tickettogether.domain.member.dto.MemberDto;
import com.tickettogether.domain.reservation.domain.Reservation;
import com.tickettogether.domain.reservation.dto.ReservationDto;

import java.util.List;

public interface ReservationService {
    List<ReservationDto.GetResponse> getReservations(Member member);
    void postSiteInfo(ReservationDto.SiteInfoPostRequest siteInfo, Long memberId);
}
