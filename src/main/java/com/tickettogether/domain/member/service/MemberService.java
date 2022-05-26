package com.tickettogether.domain.member.service;

import com.tickettogether.domain.member.domain.Member;
import com.tickettogether.domain.member.dto.MemberDto;

public interface MemberService {
    MemberDto.SaveResponse saveMemberProfile(MemberDto.SaveRequest saveRequest, Long userId);
    MemberDto.UpdateResponse updateMemberProfile(MemberDto.UpdateRequest updateRequest, Long userId);
    MemberDto.SearchResponse getMemberProfile(Long userId);
    MemberDto.SearchResponse getOtherMemberProfile(Long userId);
    Member findMemberById(Long userId);
}
