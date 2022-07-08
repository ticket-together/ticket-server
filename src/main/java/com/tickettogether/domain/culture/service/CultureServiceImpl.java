package com.tickettogether.domain.culture.service;

import com.tickettogether.domain.culture.domain.Culture;
import com.tickettogether.domain.culture.domain.CultureKeyword;
import com.tickettogether.domain.culture.dto.CultureDto;
import com.tickettogether.domain.culture.exception.CultureEmptyException;
import com.tickettogether.domain.culture.repository.CultureRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CultureServiceImpl implements CultureService{

    private final CultureRepository cultureRepository;

    public CultureDto.CultureResponse getCulture(Long id){
        Culture culture = cultureRepository.findById(id).orElseThrow(CultureEmptyException::new);
        return new CultureDto.CultureResponse(culture);
    }

    public CultureDto.CultureSearchResponse searchCulture(String search, Pageable pageable) {
        return new CultureDto.CultureSearchResponse(cultureRepository.searchCultureByName(pageable, search));
    }

    public List<CultureDto.MainCultureResponse> getMainCulture(Long id) {
        List<Culture> mainCulture= new ArrayList();
        mainCulture.addAll(cultureRepository.findTop4CultureByKeyword(CultureKeyword.CLASSIC));
        mainCulture.addAll(cultureRepository.findTop4CultureByKeyword(CultureKeyword.CONCERT));
        return mainCulture.stream().map(CultureDto.MainCultureResponse::new).collect(Collectors.toList());
    }
}