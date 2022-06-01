package com.tickettogether.domain.reservation.dto;

import com.tickettogether.domain.member.domain.Member;
import com.tickettogether.domain.reservation.domain.TicketSite;
import com.tickettogether.domain.reservation.domain.TicketSiteInfo;
import com.tickettogether.domain.reservation.exception.SiteEmptyException;
import com.tickettogether.domain.reservation.domain.Reservation;
import lombok.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

public class ReservationDto {

    @Getter
    @NoArgsConstructor
    public static class GetResponse {
        private String number;
        private String name;
        private String imgUrl;
        private String hallName;
        private LocalDate date;

        public GetResponse(Reservation reservation) {
            this.number = reservation.getNumber();
            this.name = reservation.getName();
            this.imgUrl = reservation.getImgUrl();
            this.hallName = reservation.getHallName();
            this.date = reservation.getDate();
        }
    }

    @Getter
    @AllArgsConstructor
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    @Builder
    public static class SiteInfoPostRequest {
        private String id;
        private String password;
        private String ticketSite;

        public TicketSiteInfo toEntity(Member member){
            return TicketSiteInfo.builder()
                    .ticketSite(TicketSite.of(ticketSite).orElseThrow(SiteEmptyException::new))
                    .ticketId(this.id)
                    .ticketPassword(this.password)
                    .member(member).build();
        }

        public void setPassword(String password) {
            this.password = password;
        }
    }

    @Getter
    @AllArgsConstructor
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    @Builder
    public static class SiteInfoPostResponse {
        private String id;
        private String password;
        private TicketSite ticketSite;

        public SiteInfoPostResponse (TicketSiteInfo ticketSiteInfo){
            this.id = ticketSiteInfo.getTicketId();
            this.password = ticketSiteInfo.getTicketPassword();
            this.ticketSite = ticketSiteInfo.getTicketSite();
        }

        public void setPassword(String password) {
            this.password = password;
        }
    }

    @Getter
    @AllArgsConstructor
    public static class SiteInfoGetResponse {
        private Long id;
        private String siteId;
        private String ticketSite;

        public SiteInfoGetResponse (TicketSiteInfo ticketSiteInfo){
            this.id = ticketSiteInfo.getId();
            this.siteId = ticketSiteInfo.getTicketId();
            this.ticketSite = ticketSiteInfo.getTicketSite().getTicketSiteName();
        }
    }

}
