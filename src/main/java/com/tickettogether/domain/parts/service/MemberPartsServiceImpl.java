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
import com.tickettogether.domain.parts.exception.PartsCloseDeniedException;
import com.tickettogether.domain.parts.exception.PartsEmptyException;
import com.tickettogether.domain.parts.exception.PartsJoinDeniedException;
import com.tickettogether.domain.parts.repository.MemberPartsRepository;
import com.tickettogether.domain.parts.repository.PartsRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.stream.Collectors;
import static com.tickettogether.domain.parts.domain.Parts.Status.ACTIVE;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MemberPartsServiceImpl implements MemberPartsService {

    private final MemberRepository memberRepository;
    private final MemberPartsRepository memberPartsRepository;
    private final CultureRepository cultureRepository;
    private final PartsRepository partsRepository;

    @Override
    @Transactional
    public PartsDto.CreateResponse createParts(Long memberId, Long prodId, PartsDto.CreateRequest request) {

        Member member = findMemberById(memberId);
        Culture culture = findCultureById(prodId);

        MemberParts memberParts = memberPartsRepository.save(
                MemberParts.builder()
                        .member(member)
                        .parts(Parts.builder()
                                .culture(culture)
                                .request(request)
                                .currentPartTotal(1)
                                .manager(member)
                                .status(ACTIVE)
                                .build())
                        .build()
        );
        return new PartsDto.CreateResponse(memberParts);
    }

    @Override
    public List<PartsDto.SearchResponse> searchParts(Long prodId) {
        Culture culture = findCultureById(prodId);

        List<Parts> partsList = partsRepository.findByCultureOrderByPartDate(culture);

        return partsList.stream()
                .map(PartsDto.SearchResponse::new)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public void joinParts(Long userId, Long partId) {

        Parts parts = findPartsById(partId);
        Member user = findMemberById(userId);

        if (checkParticipation(user, parts.getMemberParts())){
            throw new PartsJoinDeniedException();
        }

        MemberParts memberParts = memberPartsRepository.save(
                MemberParts.builder()
                        .member(user)
                        .parts(parts.addMember())
                        .build()
        );

    }

    @Override
    @Transactional
    public PartsDto.closeResponse closeParts(Long userId, Long partId){

        Member user = findMemberById(userId);
        Parts parts = findPartsById(partId);

        if (!parts.getManager().equals(user)) {
            throw new PartsCloseDeniedException();
        }

        parts.changePartStatus();
        return new PartsDto.closeResponse(parts);
    }

    private boolean checkParticipation(Member user, List<MemberParts> memberPartsList){
        for (MemberParts memberParts : memberPartsList) {
            if (memberParts.getMember().equals(user)) {
                return true;
            }
        }
        return false;
    }

    private Member findMemberById(Long userId) {
        return memberRepository.findById(userId)
                .orElseThrow(UserEmptyException::new);
    }

    private Culture findCultureById(Long prodId) {
        return cultureRepository.findByProdId(prodId)
                .orElseThrow(CultureEmptyException::new);
    }

    private Parts findPartsById(Long partId) {
        return partsRepository.findById(partId)
                .orElseThrow(PartsEmptyException::new);
    }
}


