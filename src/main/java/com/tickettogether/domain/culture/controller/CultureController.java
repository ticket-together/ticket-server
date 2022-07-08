package com.tickettogether.domain.culture.controller;

import com.tickettogether.domain.culture.dto.CultureDto;
import com.tickettogether.domain.culture.dto.CultureResponseMessage;
import com.tickettogether.domain.culture.service.CultureService;
import com.tickettogether.global.error.dto.BaseResponse;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping(value = "/api/v1/culture")
public class CultureController {

    private final CultureService cultureService;

    @ApiOperation(value = "공연 상세 조회")
    @GetMapping("/{id}")
    public ResponseEntity<BaseResponse<CultureDto.CultureResponse>> getCulture(@PathVariable("id") Long id){
        return ResponseEntity.ok(BaseResponse.create(CultureResponseMessage.GET_CALENDARS_SUCCESS.getMessage(), cultureService.getCulture(id)));
    }
}