package com.tickettogether.domain.parts.service;
import com.tickettogether.domain.parts.dto.PartsDto;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public interface MemberPartsService {

    PartsDto.createResponse createParts(Long userId, Long prodId, PartsDto.createRequest requestDto);
    List<PartsDto.searchResponse> searchParts(Long userId, Long prodId);
}
