package com.tickettogether.domain.culture.service;

import com.tickettogether.domain.culture.dto.CultureDto;

public interface CultureService {
    CultureDto.CultureResponse getCulture(Long id);
}