package com.tickettogether.domain.reservation.dto;

import com.tickettogether.domain.reservation.domain.Reservation;
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
}
