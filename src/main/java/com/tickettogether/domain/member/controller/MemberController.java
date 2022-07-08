package com.tickettogether.domain.member.controller;

import com.tickettogether.domain.member.dto.MemberDto;
import com.tickettogether.domain.member.service.MemberServiceImpl;
import com.tickettogether.global.error.dto.BaseResponse;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
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

    @ApiOperation(value = "회원 정보 저장", notes = "카카오 로그인 후 입력 받은 정보를 가지고 회원 정보를 마저 저장한다.")
    @ApiResponse(code = 2012, message = "존재하지 않는 키워드입니다.")
    @PostMapping
    @ResponseBody
    public ResponseEntity<BaseResponse<MemberDto.SaveResponse>> saveMemberInfo(@RequestBody MemberDto.SaveRequest saveRequest){
        return ResponseEntity.ok(BaseResponse.create(SAVE_MEMBER_SUCCESS.getMessage(),
                memberService.saveMemberProfile(saveRequest, tempMemberId)));
    }

    @ApiOperation(value = "본인 정보 조회", notes = "로그인 유저의 정보를 조회한다.")
    @ApiResponse(code = 2011, message = "존재하지 않는 회원입니다.")
    @GetMapping
    @ResponseBody
    public ResponseEntity<BaseResponse<MemberDto.SearchResponse>> searchMemberInfo(){
        return ResponseEntity.ok(BaseResponse.create(GET_PROFILE_SUCCESS.getMessage(),
                memberService.getMemberProfile(tempMemberId)));
    }

    @ApiOperation(value = "다른 회원 정보 조회", notes = "로그인 유저가 아닌 다른 회원의 정보를 조회한다.")
    @ApiResponse(code = 2011, message = "존재하지 않는 회원입니다.")
    @GetMapping("/{memberId}")
    @ResponseBody
    public ResponseEntity<BaseResponse<MemberDto.SearchResponse>> searchMemberInfo(@PathVariable("memberId") Long memberId){
        return ResponseEntity.ok(BaseResponse.create(GET_PROFILE_SUCCESS.getMessage(),
                memberService.getOtherMemberProfile(memberId)));
    }

    @ApiOperation(value = "회원 정보 수정", notes = "로그인 유저의 정보를 수정한다.")
    @ApiResponse(code = 2011, message = "존재하지 않는 회원입니다.")
    @PatchMapping
    public ResponseEntity<BaseResponse<MemberDto.UpdateResponse>> updateMemberInfo(@RequestBody MemberDto.UpdateRequest updateRequest){
        return ResponseEntity.ok(BaseResponse.create(UPDATE_PROFILE_SUCCESS.getMessage(),
                memberService.updateMemberProfile(updateRequest, tempMemberId)));
    }

    private User getLoginUser(){
        return (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    }
}
