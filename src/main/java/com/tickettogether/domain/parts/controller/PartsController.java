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

    @PostMapping("/{prodId}")
    public ResponseEntity<BaseResponse<PartsDto.createResponse>> createParts(@PathVariable("prodId") Long prodId, @RequestBody PartsDto.createRequest partsDto) {
        return ResponseEntity.ok(BaseResponse.create(SAVE_PARTS_SUCCESS.getMessage(), partsService.createParts(tempMemberId, prodId, partsDto)));
    }

    @GetMapping("/{prodId}")
    public ResponseEntity<BaseResponse<List<PartsDto.searchResponse>>> searchParts(@PathVariable("prodId") Long prodId) {
        return ResponseEntity.ok(BaseResponse.create(GET_PARTS_SUCCESS.getMessage(),partsService.searchParts(tempMemberId, prodId)));
    }

    @GetMapping("/{prodId}/{partId}/member")
    public ResponseEntity<BaseResponse<List<PartsDto.memberInfo>>> searchPartMembers(@PathVariable("partId") Long partId) {
        return ResponseEntity.ok(BaseResponse.create(GET_PARTS_MEMBER_SUCCESS.getMessage(),partsService.searchPartMembers(tempMemberId, partId)));
    }

    @PostMapping("/{prodId}/{partId}/join")
    public ResponseEntity<BaseResponse<String>> joinParts(@PathVariable("prodId") Long prodId, @PathVariable("partId") Long partId) {
        partsService.joinParts(tempMemberId, partId);
        return ResponseEntity.ok(BaseResponse.create(JOIN_PARTS_SUCCESS.getMessage()));
    }

    @PatchMapping("/{prodId}/{partId}/close")
    public ResponseEntity<BaseResponse<PartsDto.closeResponse>> closeParts(@PathVariable("prodId") Long prodId, @PathVariable("partId") Long partId) {
        return ResponseEntity.ok(BaseResponse.create(CLOSE_PARTS_SUCCESS.getMessage(),partsService.closeParts(tempMemberId, partId)));
    }

    @DeleteMapping("/{prodId}/{partId}")
    public ResponseEntity<BaseResponse<String>> deleteParts(@PathVariable("prodId") Long prodId, @PathVariable("partId") Long partId) {
        partsService.deleteParts(tempMemberId, partId);
        return ResponseEntity.ok(BaseResponse.create(DELETE_PARTS_SUCCESS.getMessage()));
    }
}
