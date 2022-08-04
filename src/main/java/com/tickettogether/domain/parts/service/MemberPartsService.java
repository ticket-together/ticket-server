package com.tickettogether.domain.parts.service;

import com.tickettogether.domain.parts.dto.PartsDto;
import org.springframework.stereotype.Service;

@Service
public interface MemberPartsService {

    PartsDto.createResponse createParts(Long memberId, String cultureName, PartsDto.createRequest requestDto);

}
