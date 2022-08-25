package com.tickettogether.domain.parts.controller;

import com.tickettogether.domain.parts.dto.PartsDto;
import com.tickettogether.domain.parts.service.MemberPartsService;
import com.tickettogether.global.error.dto.BaseResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.tickettogether.domain.parts.dto.PartsResponseMessage.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/parts")
public class PartsController {

    private final MemberPartsService partsService;

    private Long tempMemberId = 1L;

    // 팟 생성, 저장
    @PostMapping("/{prodId}")
    public ResponseEntity<BaseResponse<PartsDto.createResponse>> createParts(@PathVariable("prodId") Long prodId, @RequestBody PartsDto.createRequest partsDto){
        return ResponseEntity.ok(BaseResponse.create(SAVE_PARTS_SUCCESS.getMessage(),partsService.createParts(tempMemberId, prodId, partsDto)));
    }

}