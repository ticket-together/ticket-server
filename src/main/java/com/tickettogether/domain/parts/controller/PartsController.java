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

    private final Long tempMemberId = 10L;

    @ApiOperation(value = "팟 생성", notes = "요청한 멤버가 방장으로, 팟을 생성한다.")
    @PostMapping("/{cultureId}")
    public ResponseEntity<BaseResponse<PartsDto.CreateResponse>> createParts(@PathVariable("cultureId") Long prodId, @RequestBody PartsDto.CreateRequest partsDto){
        return ResponseEntity.ok(BaseResponse.create(SAVE_PARTS_SUCCESS.getMessage(),partsService.createParts(tempMemberId, prodId, partsDto)));
    }

    @ApiOperation(value = "팟 조회", notes = "공연 정보에 맞는 팟을 조회한다.")
    @GetMapping("/{cultureId}")
    public ResponseEntity<BaseResponse<List<PartsDto.SearchResponse>>> getParts(@PathVariable("cultureId") Long prodId) {
        return ResponseEntity.ok(BaseResponse.create(GET_PARTS_SUCCESS.getMessage(),partsService.searchParts(prodId)));
    }

    @ApiOperation(value = "팟 참여", notes = "사용자가 원하는 팟에 참여한다.")
    @PostMapping("/{cultureId}/{partId}/join")
    public ResponseEntity<BaseResponse<String>> joinParts(@PathVariable("cultureId") Long prodId, @PathVariable("partId") Long partId) {
        partsService.joinParts(tempMemberId, partId);
        return ResponseEntity.ok(BaseResponse.create(JOIN_PARTS_SUCCESS.getMessage()));
    }

    @ApiOperation(value = "팟 마감", notes = "요청한 멤버가 방장일시, 팟을 마감한다.")
    @PatchMapping("/{cultureId}/{partId}/close")
    public ResponseEntity<BaseResponse<PartsDto.closeResponse>> closeParts(@PathVariable("cultureId") Long prodId, @PathVariable("partId") Long partId) {
        return ResponseEntity.ok(BaseResponse.create(CLOSE_PARTS_SUCCESS.getMessage(),partsService.closeParts(tempMemberId, partId)));
    }

    @ApiOperation(value = "팟 삭제", notes = "요청한 멤버가 방장일시, 팟을 삭제한다.")
    @DeleteMapping("/{cultureId}/{partId}")
    public ResponseEntity<BaseResponse<String>> deleteParts(@PathVariable("cultureId") Long prodId, @PathVariable("partId") Long partId) {
        partsService.deleteParts(tempMemberId, partId);
        return ResponseEntity.ok(BaseResponse.create(DELETE_PARTS_SUCCESS.getMessage()));
    }

    @ApiOperation(value = "팟 멤버 조회", notes = "팟에 참여중인 멤버들의 기본 정보를 조회한다.")
    @GetMapping("/{cultureId}/{partId}/member")
    public ResponseEntity<BaseResponse<List<PartsDto.memberInfo>>> searchPartMembers(@PathVariable("partId") Long partId) {
        return ResponseEntity.ok(BaseResponse.create(GET_PARTS_MEMBER_SUCCESS.getMessage(),partsService.searchPartMembers(tempMemberId, partId)));
    }
}