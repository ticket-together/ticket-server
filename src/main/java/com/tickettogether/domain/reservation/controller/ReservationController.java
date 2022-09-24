package com.tickettogether.domain.reservation.controller;

import com.tickettogether.domain.member.domain.Member;
import com.tickettogether.domain.reservation.exception.SiteEmptyException;
import com.tickettogether.domain.member.service.MemberService;
import com.tickettogether.domain.reservation.domain.TicketSite;
import com.tickettogether.domain.reservation.dto.ReservationDto;
import com.tickettogether.domain.reservation.service.ReservationService;
import com.tickettogether.global.config.security.annotation.LoginUser;
import com.tickettogether.global.error.dto.BaseResponse;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static com.tickettogether.domain.reservation.dto.ReservationResponseMessage.*;

@RequiredArgsConstructor
@RestController
@RequestMapping(value = "/api/v1/tickets")
public class ReservationController {

    private final ReservationService reservationService;
    private final MemberService memberService;

    @ApiOperation(value = "예약 목록 조회", notes = "메인 화면에서 예약한 내역을 확인할 수 있다.")
    @GetMapping("/reservation")
    public ResponseEntity<BaseResponse<List<ReservationDto.GetResponse>>> getCalendars(@LoginUser Long memberId){
        Member member = memberService.findMemberById(memberId);
        return ResponseEntity.ok(BaseResponse.create(GET_RESERVATIONS_SUCCESS.getMessage(),reservationService.getReservations(member)));
    }

    @ApiOperation(value = "사이트 정보 추가", notes = "티켓 사이트 연동에서 사이트 정보를 추가한다.")
    @ApiResponse(code = 2020, message = "최대 사이트 아이디 개수를 초과하였습니다.")
    @PostMapping("/site")
    public ResponseEntity<BaseResponse<ReservationDto.SiteInfoGetResponse>> postSiteInfo(@RequestBody ReservationDto.SiteInfoPostRequest siteInfoPostRequest,
                                                                                         @LoginUser Long memberId){
        return ResponseEntity.ok(BaseResponse.create(POST_TICKET_SITE_SUCCESS.getMessage(),reservationService.postSiteInfo(siteInfoPostRequest, memberId)));
    }

    @ApiOperation(value = "사이트 정보 조회", notes = "티켓 사이트 연동에서 추가했던 정보를 조회한다.")
    @ApiResponse(code = 2022, message = "존재하지 않는 사이트 입니다.")
    @GetMapping("/site/{name}")
    public ResponseEntity<BaseResponse<ReservationDto.SiteInfoGetResponse>> getSiteInfo(@PathVariable("name") String siteName,
                                                                                        @LoginUser Long memberId){
        TicketSite ticketSite = TicketSite.of(siteName).orElseThrow(SiteEmptyException::new);
        return ResponseEntity.ok(BaseResponse.create(GET_TICKET_SITE_SUCCESS.getMessage(), reservationService.getSiteInfo(memberId, ticketSite)));
    }

    @ApiOperation(value = "사이트 정보 수정", notes = "티켓 사이트 연동에서 추가했던 정보를 수정한다.")
    @ApiResponse(code = 2024,message = "사이트 정보 수정에 실패하였습니다.")
    @PatchMapping("/site/{id}")
    public ResponseEntity<BaseResponse<ReservationDto.SiteInfoGetResponse>> updateSiteInfo(
            @RequestBody ReservationDto.SiteInfoPostRequest siteInfoPostRequest,
            @PathVariable("id") Long id){
        return ResponseEntity.ok(BaseResponse.create(GET_TICKET_SITE_SUCCESS.getMessage(), reservationService.updateSiteInfo(siteInfoPostRequest, id)));
    }

    @ApiOperation(value = "사이트 정보 삭제", notes = "티켓 사이트 연동에서 추가했던 정보를 수정한다.")
    @ApiResponse(code = 2022, message = "존재하지 않는 사이트 정보입니다.")
    @DeleteMapping("/site/{id}")
    public ResponseEntity<BaseResponse<String>> deleteSiteInfo(
            @PathVariable("id") Long id, @LoginUser Long memberId){
        reservationService.deleteSiteInfo(id, memberId);
        return ResponseEntity.ok(BaseResponse.create(DELETE_TICKET_SITE_SUCCESS.getMessage()));
    }
}