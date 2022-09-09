package com.tickettogether.domain.member.service;

import com.tickettogether.domain.culture.domain.CultureKeyword;
import com.tickettogether.domain.member.domain.Member;
import com.tickettogether.domain.member.domain.MemberKeyword;
import com.tickettogether.domain.member.dto.MemberDto;
import com.tickettogether.domain.member.exception.KeywordEmptyException;
import com.tickettogether.domain.member.exception.UserEmptyException;
import com.tickettogether.domain.member.repository.MemberKeywordRepository;
import com.tickettogether.domain.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MemberServiceImpl implements MemberService{

    private final MemberRepository memberRepository;

    private final MemberKeywordRepository memberKeywordRepository;

    @Transactional
    public MemberDto.SaveResponse saveMemberProfile(MemberDto.SaveRequest saveRequest, Long userId){
        Member member = findMemberById(userId);
        if(saveRequest.getNickname() != null) {
            member.saveMemberProfile(saveRequest.getNickname());
        }

        for(String k : saveRequest.getKeywords()){
            try{
                CultureKeyword keyword = CultureKeyword.valueOf(k);
                memberKeywordRepository.save(
                        MemberKeyword.builder()
                                .member(member)
                                .keyword(keyword).build());
            }catch (Exception ex){
                throw new KeywordEmptyException();
            }
        }
        return new MemberDto.SaveResponse(member.getId());
    }

    @Transactional
    public MemberDto.UpdateResponse updateMemberProfile(MemberDto.UpdateRequest updateRequest, Long userId) {
        Member member = findMemberById(userId);
        member.updateMemberProfile(updateRequest.getUsername(), updateRequest.getPhoneNumber());
        return new MemberDto.UpdateResponse(member.getId());
    }

    public MemberDto.SearchResponse getMemberProfile(Long userId) {
        Member member = findMemberById(userId);
        return createSearchResponse(member);
    }

    public MemberDto.SearchResponse getOtherMemberProfile(Long userId) {
        Member member = findMemberById(userId);
        return createSearchResponse(member);
    }

    public Member findMemberById(Long userId){
        return memberRepository.findById(userId).orElseThrow(UserEmptyException::new);
    }

    private MemberDto.SearchResponse createSearchResponse(Member member){
        return MemberDto.SearchResponse.builder()
                .userId(member.getId())
                .username(member.getName())
                .email(member.getEmail())
                .imgUrl(member.getImgUrl())
                .phoneNumber(member.getPhoneNumber())
                .keywords(getKeywordList(member))
                .build();
    }

    private List<String> getKeywordList(Member member){
        List<MemberKeyword> allByMember = memberKeywordRepository.findAllByMember(member);
        return allByMember.stream()
                .map(h -> h.getKeyword().toString())
                .collect(Collectors.toList());
    }
}