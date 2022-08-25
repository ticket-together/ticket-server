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
import com.tickettogether.domain.parts.exception.PartsEmptyException;
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
    private final PartsRepository partsRepository;
    private final MemberPartsRepository memberPartsRepository;
    private final CultureRepository cultureRepository;

    @Override
    @Transactional
    // 팟 생성
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

    @Override
    public List<PartsDto.searchResponse> searchParts(Long userId, Long prodId) {
        Member user = findMemberById(userId);  // 현재 사용자
        Culture culture = findCultureById(prodId);   // 후에 findBy

        if (partsRepository.findPartsByCulture(culture).isEmpty()) {
            throw new PartsEmptyException();
        }

        List<Parts> partsList = partsRepository.findPartsByCulture(culture);
        List<PartsDto.searchResponse> partsDtoList = new ArrayList<>();

        for (Parts parts : partsList) {
            partsDtoList.add(partsInfo(parts));
        }
        return partsDtoList;
    }


    private Member findMemberById(Long userId) {
        return memberRepository.findById(userId)
                .orElseThrow(UserEmptyException::new);
    }

    private Culture findCultureById(Long prodId) {
        return cultureRepository.findByProdId(prodId)
                .orElseThrow(CultureEmptyException::new);
    }

    private Member getPartsManager(Parts parts) {
        List<MemberParts> memberPartsList = memberPartsRepository.findMemberPartsByParts(parts);
        return memberPartsList.stream()
                .findAny()
                .map(m -> m.getManager())
                .orElseThrow();
    }

    private List<Long> getPartsMemberId(Parts parts){
        List<MemberParts> memberPartsList = memberPartsRepository.findMemberPartsByParts(parts);
        return memberPartsList.stream()
                .filter(m -> m.getMember() != null)
                .map(m -> m.getMember().getId())
                .collect(Collectors.toList());
    }

    public int getPartsCount(Parts parts) {
        List<MemberParts> memberPartsList = memberPartsRepository.findMemberPartsByParts(parts);
        return memberPartsList.size();
    }

    private PartsDto.searchResponse partsInfo(Parts parts){
        return PartsDto.searchResponse.builder()
                .managerId(getPartsManager(parts).getId())
                .memberId(getPartsMemberId(parts))
                .cultureName(parts.getCulture().getName())
                .partId(parts.getId())
                .partName(parts.getPartName())
                .partContent(parts.getPartContent())
                .partDate(parts.getPartDate())
                .partTotal(parts.getPartTotal())
                .memberCount(getPartsCount(parts))
                .status(parts.getStatus())
                .build();
    }
}


