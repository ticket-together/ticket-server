package com.tickettogether.domain.culture.service;

import com.tickettogether.domain.culture.domain.Culture;
import com.tickettogether.domain.culture.domain.CultureKeyword;
import com.tickettogether.domain.culture.dto.CultureDto;
import com.tickettogether.domain.culture.exception.CultureEmptyException;
import com.tickettogether.domain.culture.repository.CultureRepository;
import com.tickettogether.domain.member.domain.Member;
import com.tickettogether.domain.member.domain.MemberKeyword;
import com.tickettogether.domain.member.exception.UserEmptyException;
import com.tickettogether.domain.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CultureServiceImpl implements CultureService{

    private final CultureRepository cultureRepository;
    private final MemberRepository memberRepository;
    private static final int MAX_KEYWORD_COUNT = 2;

    public CultureDto.CultureResponse getCulture(Long id){
        Culture culture = cultureRepository.findById(id).orElseThrow(CultureEmptyException::new);
        return new CultureDto.CultureResponse(culture);
    }

    public CultureDto.CultureSearchResponse searchCulture(String search, Pageable pageable) {
        return new CultureDto.CultureSearchResponse(cultureRepository.searchCultureByName(pageable, search));
    }

    public List<CultureDto.MainCultureResponse> getMainCulture(Long id) {
        Member member = memberRepository.findById(id)
                .orElseThrow(UserEmptyException::new);

        List<Culture> cultures= new ArrayList();
        Set<CultureKeyword> memberKeywords = getMemberKeywords(member);

        for (CultureKeyword keyword : memberKeywords) {
            cultures.addAll(cultureRepository.findTop4CultureByKeyword(keyword));
        }

        return cultures
                .stream()
                .map(CultureDto.MainCultureResponse::new)
                .collect(Collectors.toList());
    }

    private Set<CultureKeyword> getMemberKeywords(Member member) {
        List<CultureKeyword> memberKeywords = member
                .getMemberKeywords()
                .stream()
                .map(MemberKeyword::getKeyword)
                .collect(Collectors.toList());

        Set<CultureKeyword> cultureKeywords = new HashSet<>(memberKeywords);
        int i = 0;
        while (cultureKeywords.size() < MAX_KEYWORD_COUNT) {
            cultureKeywords.add(CultureKeyword.values()[i]);
            i += 1;
        }
        return cultureKeywords;
    }
}