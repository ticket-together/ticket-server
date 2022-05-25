package com.tickettogether.domain.member.controller;

import com.tickettogether.domain.member.dto.MemberDto;
import com.tickettogether.domain.member.service.MemberServiceImpl;
import com.tickettogether.global.error.dto.BaseResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import static com.tickettogether.domain.member.dto.MemberResponseMessage.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/member")
public class MemberController {
    private final MemberServiceImpl memberService;
    private Long tempMemberId = 1L;

    /**
     * 회원 정보 저장
     */
    @PostMapping
    @ResponseBody
    public ResponseEntity<BaseResponse<MemberDto.SaveResponse>> saveMemberInfo(@RequestBody MemberDto.SaveRequest saveRequest){
        return ResponseEntity.ok(BaseResponse.create(SAVE_MEMBER_SUCCESS.getMessage(),
                memberService.saveMemberProfile(saveRequest, tempMemberId)));
    }
    /**
     회원 정보 조회
     */
    @GetMapping
    @ResponseBody
    public ResponseEntity<BaseResponse<MemberDto.SearchResponse>> searchMemberInfo(){
        return ResponseEntity.ok(BaseResponse.create(GET_PROFILE_SUCCESS.getMessage(),
                memberService.getMemberProfile(tempMemberId)));
    }

    /**
     다른 회원 정보 조회
     */
    @GetMapping("/{memberId}")
    @ResponseBody
    public ResponseEntity<BaseResponse<MemberDto.SearchResponse>> searchMemberInfo(@PathVariable("memberId") Long memberId){
        return ResponseEntity.ok(BaseResponse.create(GET_PROFILE_SUCCESS.getMessage(),
                memberService.getOtherMemberProfile(memberId)));
    }

    /**
     회원 정보 수정
     */
    @PatchMapping
    public ResponseEntity<BaseResponse<MemberDto.UpdateResponse>> updateMemberInfo(@RequestBody MemberDto.UpdateRequest updateRequest){
        return ResponseEntity.ok(BaseResponse.create(UPDATE_PROFILE_SUCCESS.getMessage(),
                memberService.updateMemberProfile(updateRequest, tempMemberId)));
    }

    private User getLoginUser(){
        return (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    }
}
