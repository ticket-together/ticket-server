package com.tickettogether.domain.culture.controller;

import com.tickettogether.domain.culture.dto.CultureDto;
import com.tickettogether.domain.culture.dto.CultureResponseMessage;
import com.tickettogether.domain.culture.service.CultureService;
import com.tickettogether.global.error.dto.BaseResponse;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping(value = "/api/v1/culture")
public class CultureController {

    private final CultureService cultureService;
    private Long memberId = 1L; //테스트 용

    @ApiOperation(value = "공연 상세 조회")
    @GetMapping("/{id}")
    public ResponseEntity<BaseResponse<CultureDto.CultureResponse>> getCulture(@PathVariable("id") Long id) {
        return ResponseEntity.ok(BaseResponse.create(CultureResponseMessage.GET_CULTURE_SUCCESS.getMessage(), cultureService.getCulture(id)));
    }

    @ApiOperation(value = "공연 검색")
    @GetMapping("/search")
    public ResponseEntity<BaseResponse<CultureDto.CultureSearchResponse>> searchCulture(@RequestParam(value = "query", defaultValue = "") String query,
                                                                                     @PageableDefault(size = 10) Pageable pageable) {
        return ResponseEntity.ok(BaseResponse.create(CultureResponseMessage.GET_CULTURE_SUCCESS.getMessage(), cultureService.searchCulture(query, pageable)));
    }

    @ApiOperation(value = "메인 페이지 공연 목록")
    @GetMapping
    public ResponseEntity<BaseResponse<List<CultureDto.MainCultureResponse>>> getMainCulture() {
        return  ResponseEntity.ok(BaseResponse.create(CultureResponseMessage.GET_CULTURE_SUCCESS.getMessage(), cultureService.getMainCulture(memberId)));
    }
}