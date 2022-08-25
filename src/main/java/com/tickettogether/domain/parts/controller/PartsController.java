package com.tickettogether.domain.parts.controller;

import com.tickettogether.domain.parts.dto.PartsDto;
import com.tickettogether.domain.parts.service.MemberPartsService;
import com.tickettogether.global.error.dto.BaseResponse;
import io.swagger.annotations.ApiOperation;
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

    @ApiOperation(value = "팟 생성", notes = "요청한 멤버가 방장으로, 팟을 생성한다.")
    @PostMapping("/{prodId}")
    public ResponseEntity<BaseResponse<PartsDto.CreateResponse>> createParts(@PathVariable("prodId") Long prodId, @RequestBody PartsDto.CreateRequest partsDto){
        return ResponseEntity.ok(BaseResponse.create(SAVE_PARTS_SUCCESS.getMessage(),partsService.createParts(tempMemberId, prodId, partsDto)));
    }

    @ApiOperation(value = "팟 조회", notes = "공연 정보에 맞는 팟을 조회한다.")
    @GetMapping("/{prodId}")
    public ResponseEntity<BaseResponse<List<PartsDto.SearchResponse>>> getParts(@PathVariable("prodId") Long prodId) {
        return ResponseEntity.ok(BaseResponse.create(GET_PARTS_SUCCESS.getMessage(),partsService.searchParts(prodId)));
    }
}