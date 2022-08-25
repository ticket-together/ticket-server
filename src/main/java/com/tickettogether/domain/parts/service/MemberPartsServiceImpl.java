package com.tickettogether.domain.parts.service;

import com.tickettogether.domain.culture.domain.Culture;
import com.tickettogether.domain.culture.exception.CultureEmptyException;
import com.tickettogether.domain.culture.repository.CultureRepository;
import com.tickettogether.domain.member.domain.Member;
import com.tickettogether.domain.member.exception.UserEmptyException;
import com.tickettogether.domain.member.repository.MemberRepository;
import com.tickettogether.domain.parts.domain.MemberParts;
import com.tickettogether.domain.parts.domain.Parts;
import com.tickettogether.domain.parts.dto.PartsDto;
import com.tickettogether.domain.parts.exception.*;
import com.tickettogether.domain.parts.repository.MemberPartsRepository;
import com.tickettogether.domain.parts.repository.PartsRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static com.tickettogether.domain.parts.domain.Parts.Status.ACTIVE;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MemberPartsServiceImpl implements MemberPartsService {

    private final MemberRepository memberRepository;
    private final MemberPartsRepository memberPartsRepository;
    private final CultureRepository cultureRepository;

    @Override
    @Transactional
    public PartsDto.createResponse createParts(Long userId, Long prodId, PartsDto.createRequest requestDto) {

        Member user = findMemberById(userId);
        Culture culture = findCultureById(prodId);

        MemberParts memberParts = memberPartsRepository.save(
                MemberParts.builder()
                        .manager(user)
                        .member(user)
                        .parts(Parts.builder()
                                .culture(culture)
                                .partName(requestDto.getPartName())
                                .partContent(requestDto.getPartContent())
                                .partTotal(requestDto.getPartTotal())
                                .partDate(requestDto.getPartDate())
                                .status(ACTIVE)
                                .build()
                        )
                        .build()
        );

        return new PartsDto.createResponse(memberPartsRepository.save(memberParts));
    }


    private Member findMemberById(Long userId) {
        return memberRepository.findById(userId)
                .orElseThrow(UserEmptyException::new);
    }

    private Culture findCultureById(Long prodId) {
        return cultureRepository.findById(prodId)    // 이후에 findById -> findByProdId로 변경
                .orElseThrow(CultureEmptyException::new);
    }

}


