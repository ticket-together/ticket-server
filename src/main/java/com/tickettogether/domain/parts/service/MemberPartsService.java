package com.tickettogether.domain.parts.service;

import com.tickettogether.domain.parts.dto.PartsDto;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public interface MemberPartsService {
    PartsDto.CreateResponse createParts(Long userId, Long prodId, PartsDto.CreateRequest requestDto);
    List<PartsDto.SearchResponse> searchParts(Long prodId);
    void joinParts(Long userId, Long partId);
}