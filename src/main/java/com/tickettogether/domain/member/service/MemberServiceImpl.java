package com.tickettogether.domain.member.service;

import com.tickettogether.domain.member.domain.Keyword;
import com.tickettogether.domain.member.domain.Member;
import com.tickettogether.domain.member.domain.MemberKeyword;
import com.tickettogether.domain.member.dto.MemberDto;
import com.tickettogether.domain.member.exception.KeywordEmptyException;
import com.tickettogether.domain.member.exception.UserEmptyException;
import com.tickettogether.domain.member.repository.KeywordRepository;
import com.tickettogether.domain.member.repository.MemberKeywordRepository;
import com.tickettogether.domain.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MemberServiceImpl implements MemberService{

    private final MemberRepository memberRepository;
    private final MemberKeywordRepository memberKeywordRepository;
    private final KeywordRepository keywordRepository;

    @Transactional
    public MemberDto.SaveResponse saveMemberProfile(MemberDto.SaveRequest saveRequest, Long userId){
        Member member = findMemberByEmail(userId);
        member.saveMemberProfile(saveRequest.getPhoneNumber());

        for(Long id : saveRequest.getKeywordIds()){
            Keyword keyword = keywordRepository.findById(id).orElseThrow(KeywordEmptyException::new);
            memberKeywordRepository.save(
                    MemberKeyword.builder()
                    .keyword(keyword)
                    .member(member).build());
        }
        return new MemberDto.SaveResponse(member.getId());
    }

    @Transactional
    public MemberDto.UpdateResponse updateMemberProfile(MemberDto.UpdateRequest updateRequest, Long userId) {
        Member member = findMemberByEmail(userId);
        member.updateMemberProfile(updateRequest.getUsername(), updateRequest.getPhoneNumber());
        return new MemberDto.UpdateResponse(member.getId());
    }

    public MemberDto.SearchResponse getMemberProfile(Long userId) {
        Member member = findMemberByEmail(userId);
        return createSearchResponse(member);
    }

    public MemberDto.SearchResponse getOtherMemberProfile(Long userId) {
        Member member = memberRepository.findById(userId).orElseThrow(UserEmptyException::new);
        return createSearchResponse(member);
    }

    private Member findMemberByEmail(Long userId){
        return memberRepository.findById(userId).orElseThrow(UserEmptyException::new);
    }
    private MemberDto.SearchResponse createSearchResponse(Member member){
        return MemberDto.SearchResponse.builder()
                .userId(member.getId())
                .username(member.getName())
                .email(member.getEmail())
                .imgUrl(member.getImgUrl())
                .phoneNumber(member.getPhoneNumber())
                .build();
    }
}