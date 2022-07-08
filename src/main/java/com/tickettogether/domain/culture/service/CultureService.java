package com.tickettogether.domain.culture.service;

import com.tickettogether.domain.culture.dto.CultureDto;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface CultureService {
    CultureDto.CultureResponse getCulture(Long id);
    CultureDto.CultureSearchResponse searchCulture(String search, Pageable pageable);
    List<CultureDto.MainCultureResponse> getMainCulture(Long id);
}