package com.tickettogether.domain.culture.service;

import com.tickettogether.domain.culture.domain.Culture;
import com.tickettogether.domain.culture.dto.CultureDto;
import com.tickettogether.domain.culture.exception.CultureEmptyException;
import com.tickettogether.domain.culture.repository.CultureRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CultureServiceImpl implements CultureService{

    private final CultureRepository cultureRepository;

    public CultureDto.CultureResponse getCulture(Long id){
        Culture culture = cultureRepository.findById(id).orElseThrow(CultureEmptyException::new);
        return new CultureDto.CultureResponse(culture);
    }
}