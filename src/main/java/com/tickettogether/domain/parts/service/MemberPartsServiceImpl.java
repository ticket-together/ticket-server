package com.tickettogether.domain.parts.service;

import com.tickettogether.domain.culture.domain.Culture;
import com.tickettogether.domain.culture.exception.CultureEmptyException;
import com.tickettogether.domain.culture.repository.CultureRepository;
import com.tickettogether.domain.member.domain.Member;
import com.tickettogether.domain.member.exception.UserEmptyException;
import com.tickettogether.domain.member.repository.MemberRepository;
import com.tickettogether.domain.parts.domain.MemberParts;
import com.tickettogether.domain.parts.dto.PartsDto;
import com.tickettogether.domain.parts.repository.PartsRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class MemberPartsServiceImpl implements MemberPartsService {

    private final MemberRepository memberRepository;
    private final PartsRepository partsRepository;
    private final CultureRepository cultureRepository;

    // 팟 생성
    @Override
    public PartsDto.createResponse createParts(Long memberId, String cultureName, PartsDto.createRequest requestDto) {
        Member member = getMember(memberId);
        Culture culture = getCulture(cultureName);

        requestDto.setMember(member);
        requestDto.setCulture(culture);

        MemberParts memberParts = requestDto.toEntity();

        return new PartsDto.createResponse(partsRepository.save(memberParts));
    }

    private Member getMember(Long memberId) {
        return memberRepository.findById(memberId)
                .orElseThrow(UserEmptyException::new);
    }

    private Culture getCulture(String cultureName) {
        return cultureRepository.findByName(cultureName)
                .orElseThrow(CultureEmptyException::new);
    }
}
