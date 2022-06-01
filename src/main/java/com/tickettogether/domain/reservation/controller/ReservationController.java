package com.tickettogether.domain.reservation.controller;

import com.tickettogether.domain.calendar.dto.CalendarDto;
import com.tickettogether.domain.member.domain.Member;
import com.tickettogether.domain.member.service.MemberService;
import com.tickettogether.domain.reservation.dto.ReservationDto;
import com.tickettogether.domain.reservation.service.ReservationService;
import com.tickettogether.global.error.dto.BaseResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static com.tickettogether.domain.reservation.dto.ReservationResponseMessage.*;

@RequiredArgsConstructor
@RestController
@RequestMapping(value = "/api/v1/tickets/reservation")
public class ReservationController {

    private final ReservationService reservationService;
    private final MemberService memberService;

    @GetMapping
    public ResponseEntity<BaseResponse<List<ReservationDto.GetResponse>>> getCalendars(){
        Member member = memberService.findMemberById(1L);
        return ResponseEntity.ok(BaseResponse.create(GET_RESERVATIONS_SUCCESS.getMessage(),reservationService.getReservations(member)));
    }
}
