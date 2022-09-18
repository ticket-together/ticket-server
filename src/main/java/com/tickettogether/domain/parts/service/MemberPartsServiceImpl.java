package com.tickettogether.domain.parts.service;

import com.tickettogether.domain.culture.domain.Culture;
import com.tickettogether.domain.culture.exception.CultureEmptyException;
import com.tickettogether.domain.culture.repository.CultureRepository;
import com.tickettogether.domain.member.domain.Member;
import com.tickettogether.domain.member.domain.Role;
import com.tickettogether.domain.member.exception.UserEmptyException;
import com.tickettogether.domain.member.repository.MemberRepository;
import com.tickettogether.domain.parts.domain.MemberParts;
import com.tickettogether.domain.parts.domain.Parts;
import com.tickettogether.domain.parts.dto.PartsDto;
import com.tickettogether.domain.parts.exception.*;
import com.tickettogether.domain.parts.repository.MemberPartsRepository;
import com.tickettogether.domain.parts.repository.PartsRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import static com.tickettogether.domain.parts.domain.Status.*;

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
    public PartsDto.CreateResponse createParts(Long userId, Long prodId, PartsDto.CreateRequest request) {

        Member user = findMemberById(userId);
        Culture culture = findCultureById(prodId);

        MemberParts memberParts = memberPartsRepository.save(
                MemberParts.builder()
                        .member(user)
                        .parts(Parts.builder()
                                .culture(culture)
                                .request(request)
                                .currentPartTotal(1)
                                .manager(user)
                                .status(ACTIVE)
                                .build())
                        .build()
        );
        return new PartsDto.CreateResponse(memberParts);
    }

    @Override
    public List<PartsDto.SearchResponse> searchParts(Long userId, Long prodId) {

        Member user = findMemberById(userId);
        Culture culture = findCultureById(prodId);

        List<Parts> partsList = partsRepository.findByCultureOrderByPartDate(culture);
        List<PartsDto.SearchResponse> partsInfoList = new ArrayList<>();

        for (Parts parts : partsList) {
            partsInfoList.add(createSearchResponse(user, parts));
        }

        return partsInfoList;
    }

    @Override
    @Transactional
    public void joinParts(Long userId, Long partId) {

        Parts parts = findPartsById(partId);
        Member user = findMemberById(userId);

        if (parts.getStatus() == CLOSED) {
            throw new PartsClosedException();
        }

        if (checkParticipation(user, parts.getMemberParts())) {
            throw new PartsJoinDeniedException();
        }

        if (parts.getCurrentPartTotal() >= parts.getPartTotal()) {
            throw new PartsFullException();
        }

        memberPartsRepository.save(
                MemberParts.builder()
                        .member(user)
                        .parts(parts.addMember())
                        .build()
        );

    }

    @Override
    @Transactional
    public PartsDto.closeResponse closeParts(Long userId, Long partId) {

        Member user = findMemberById(userId);
        Parts parts = findPartsById(partId);

        if (!parts.getManager().equals(user)) {
            throw new PartsCloseDeniedException();
        }

        parts.close();
        return new PartsDto.closeResponse(parts);
    }

    @Override
    @Transactional
    public void leaveParts(Long userId, Long partId) {

        Member user = findMemberById(userId);
        Parts parts = findPartsById(partId);

        if (parts.getManager().equals(user)) {
            throw new PartsLeaveDeniedException();
        }

        if (parts.getStatus() == CLOSED) {
            throw new PartsClosedException();
        }

        MemberParts memberParts = memberPartsRepository.findByPartsAndMember(parts, user)
                .orElseThrow(PartsLeaveDeniedException::new);

        memberPartsRepository.delete(memberParts);
        parts.removeMember();
    }

    @Override
    @Transactional
    public void deleteParts(Long userId, Long partId) {

        Member user = findMemberById(userId);
        Parts parts = findPartsById(partId);

        if (!parts.getManager().equals(user)) {
            throw new PartsDeleteDeniedException();
        }

        if (parts.getCurrentPartTotal() > 1) {
            throw new PartsMemberExistedException();
        }

        partsRepository.deleteById(partId);

    }

    @Override
    public List<PartsDto.memberInfo> searchPartMembers(Long userId, Long partId) {

        Parts parts = findPartsById(partId);

        List<Member> memberList = getPartsMember(parts);
        List<PartsDto.memberInfo> partMemberInfoList = new ArrayList<>();

        for (Member member : memberList) {
            partMemberInfoList.add(getMemberInfo(member, parts));
        }

        return partMemberInfoList;
    }

    @Override
    public List<PartsDto.SearchResponse> findPartsByMember(Long memberId) {
        Member member = memberRepository.findMember(memberId)
                .orElseThrow(UserEmptyException::new);

        return member.getMemberPartsList()
                .stream()
                .map(MemberParts::getParts)
                .sorted(Comparator.comparing(Parts::getCreatedAt).reversed())
                .map(PartsDto.SearchResponse::new)
                .peek(p -> p.updateRole(checkManager(member.getId(), p.getManagerId())))
                .collect(Collectors.toList());
    }

    private List<Member> getPartsMember(Parts parts) {
        List<MemberParts> memberPartsList = memberPartsRepository.findByParts(parts);
        return memberPartsList.stream()
                .map(MemberParts::getMember)
                .filter(Objects::nonNull)
                .collect(Collectors.toList());
    }

    private PartsDto.memberInfo getMemberInfo(Member user, Parts parts) {
        return PartsDto.memberInfo.builder()
                .memberId(user.getId())
                .memberName(user.getName())
                .memberImgUrl(user.getImgUrl())
                .isManager(parts.getManager().equals(user))
                .build();
    }


    private boolean checkParticipation(Member user, List<MemberParts> memberPartsList) {
        for (MemberParts memberParts : memberPartsList) {
            if (memberParts.getMember().equals(user)) {
                return true;
            }
        }
        return false;
    }

    private Role checkManager(Long memberId, Long managerId) {
        if (memberId.equals(managerId)) {
            return Role.PART_MANAGER;
        }
        return Role.PART_MEMBER;
    }

    private Role getMemberRole(Member user, Parts parts) {
        if (parts.getManager().equals(user)) {
            return Role.PART_MANAGER;
        } else if (checkParticipation(user, parts.getMemberParts())) {
            return Role.PART_MEMBER;
        } else {
            return Role.PART_USER;
        }
    }

    private PartsDto.SearchResponse createSearchResponse(Member user, Parts parts) {
        return PartsDto.SearchResponse.builder()
                .managerId(parts.getManager().getId())
                .cultureName(parts.getCulture().getName())
                .partId(parts.getId())
                .partName(parts.getPartName())
                .partContent(parts.getPartContent())
                .partDate(parts.getPartDate())
                .partTotal(parts.getPartTotal())
                .currentPartTotal(parts.getCurrentPartTotal())
                .status(parts.getStatus())
                .role(getMemberRole(user, parts))
                .createdAt(parts.getCreatedAt())
                .build();
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


