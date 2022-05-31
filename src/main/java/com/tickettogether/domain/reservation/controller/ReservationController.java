package com.tickettogether.domain.reservation.controller;

import com.tickettogether.domain.member.domain.Member;
import com.tickettogether.domain.member.exception.SiteEmptyException;
import com.tickettogether.domain.member.service.MemberService;
import com.tickettogether.domain.reservation.domain.TicketSite;
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
    public ResponseEntity<BaseResponse<ReservationDto.SiteInfoGetResponse>> postSiteInfo(@RequestBody ReservationDto.SiteInfoPostRequest siteInfoPostRequest){
        return ResponseEntity.ok(BaseResponse.create(POST_TICKET_SITE_SUCCESS.getMessage(),reservationService.postSiteInfo(siteInfoPostRequest, tempMemberId)));
    }

    @GetMapping("/site/{name}")
    public ResponseEntity<BaseResponse<ReservationDto.SiteInfoGetResponse>> getSiteInfo(@PathVariable("name") String siteName){
        TicketSite ticketSite = TicketSite.of(siteName).orElseThrow(SiteEmptyException::new);
        return ResponseEntity.ok(BaseResponse.create(GET_TICKET_SITE_SUCCESS.getMessage(), reservationService.getSiteInfo(tempMemberId, ticketSite)));
    }


}
