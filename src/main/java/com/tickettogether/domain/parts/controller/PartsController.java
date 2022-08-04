package com.tickettogether.domain.parts.controller;

import com.tickettogether.domain.parts.dto.PartsDto;
import com.tickettogether.domain.parts.service.MemberPartsService;
import com.tickettogether.global.error.dto.BaseResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static com.tickettogether.domain.parts.dto.PartsResponseMessage.SAVE_PARTS_SUCCESS;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/parts")
public class PartsController {

    private final MemberPartsService partsService;

    private Long memberId = 1L;

    // 팟 생성, 저장
    @PostMapping("/create")
    public ResponseEntity<BaseResponse<PartsDto.createResponse>> createParts(@RequestParam("name") String cultureName, @RequestBody PartsDto.createRequest partsDto){
        return ResponseEntity.ok(BaseResponse.create(SAVE_PARTS_SUCCESS.getMessage(),partsService.createParts(memberId, cultureName, partsDto)));
    }

//    // 팟 조회
//    @GetMapping("/{cultureId}")
//    public ResponseEntity<BaseResponse<List<PartsInfoDto>>> searchParts(@PathVariable("cultureId") Long cultureId) {
//        return ResponseEntity.ok(BaseResponse.create(GET_REVIEW_SUCCESS.getMessage(),partsService.searchParts(cultureId)));
//    }
}
