package com.tickettogether.domain.reservation.controller;

import com.tickettogether.domain.member.domain.Member;
import com.tickettogether.domain.member.service.MemberService;
import com.tickettogether.domain.reservation.dto.ReservationDto;
import com.tickettogether.domain.reservation.service.ReservationService;
import com.tickettogether.global.error.dto.BaseResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.tickettogether.domain.reservation.dto.ReservationResponseMessage.*;

@RequiredArgsConstructor
@RestController
@RequestMapping(value = "/api/v1/tickets")
public class ReservationController {

    private final ReservationService reservationService;
    private final MemberService memberService;
    private Long tempMemberId = 1L;

    @GetMapping("/reservation")
    public ResponseEntity<BaseResponse<List<ReservationDto.GetResponse>>> getCalendars(){
        Member member = memberService.findMemberById(tempMemberId);
        return ResponseEntity.ok(BaseResponse.create(GET_RESERVATIONS_SUCCESS.getMessage(),reservationService.getReservations(member)));
    }

    @PostMapping("/site")
    public ResponseEntity<BaseResponse<String>> postSiteInfo(@RequestBody ReservationDto.SiteInfoPostRequest siteInfoPostRequest){
        reservationService.postSiteInfo(siteInfoPostRequest, tempMemberId);
        return ResponseEntity.ok(BaseResponse.create(POST_TICKET_SITE_SUCCESS.getMessage()));
    }

}
