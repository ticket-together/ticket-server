package com.tickettogether.domain.calendar.controller;

import com.tickettogether.domain.calendar.dto.CalendarDto;
import com.tickettogether.domain.calendar.service.CalendarService;
import com.tickettogether.global.config.security.annotation.LoginUser;
import com.tickettogether.global.error.dto.BaseResponse;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import java.util.List;
import static com.tickettogether.domain.calendar.dto.CalendarResponseMessage.*;

@RequiredArgsConstructor
@RestController
@RequestMapping(value = "/api/v1/calendar")
public class CalendarControllerV1 {

    private final CalendarService calendarService;

    @ApiOperation(value = "캘린더 전체 목록 조회")
    @GetMapping
    public ResponseEntity<BaseResponse<List<CalendarDto.PostResponse>>> getCalendars(@LoginUser Long memberId) {
        return ResponseEntity.ok(BaseResponse.create(GET_CALENDARS_SUCCESS.getMessage(), calendarService.getCalendars(memberId)));
    }

    @ApiOperation(value = "다른 유저의 캘린더 전체 목록 조회")
    @GetMapping("/{memberId}")
    public ResponseEntity<BaseResponse<List<CalendarDto.PostResponse>>> getCalendarsByMember(@PathVariable("memberId") Long memberId) {
        return ResponseEntity.ok(BaseResponse.create(GET_CALENDARS_SUCCESS.getMessage(), calendarService.getCalendars(memberId)));
    }

    @ApiOperation(value = "캘린더 항목 추가", notes = "포스터 사진을 추가할 날짜에 맞게 캘린더에 추가한다.")
    @ApiResponse(code = 2020, message = "최대 캘린더 개수를 초과하였습니다.")
    @PostMapping
    public ResponseEntity<BaseResponse<CalendarDto.PostResponse>> createCalendar(
            @RequestBody CalendarDto.PostRequest newCalendar, @LoginUser Long memberId) {
        return ResponseEntity.ok(BaseResponse.create(POST_CALENDAR_SUCCESS.getMessage(), calendarService.createCalendar(newCalendar, memberId)));
    }

    @ApiOperation(value = "캘린더 삭제")
    @ApiResponse(code = 2021, message = "존재하지 않는 캘린더입니다.")
    @DeleteMapping("/{calendarId}")
    public ResponseEntity<BaseResponse<String>> deleteCalendar(@PathVariable("calendarId") Long calendarId, @LoginUser Long memberId) {
        calendarService.deleteCalendar(calendarId, memberId);
        return ResponseEntity.ok(BaseResponse.create(DELETE_CALENDAR_SUCCESS.getMessage()));
    }

    @ApiOperation(value = "캘린더 수정", notes = "get에서 받은 캘린더 id로 사진을 수정한다.")
    @ApiResponse(code = 2021, message = "존재하지 않는 캘린더입니다.")
    @PutMapping("/{calendarId}")
    public ResponseEntity<BaseResponse<String>> updateCalendar(@PathVariable("calendarId") Long calendarId,
                                                               @RequestPart("files") MultipartFile multipartFile,
                                                               @RequestPart("calendar") CalendarDto.PostRequest newCalendar, @LoginUser Long memberId) {
        calendarService.updateCalendar(calendarId, memberId, multipartFile, newCalendar);
        return ResponseEntity.ok(BaseResponse.create(UPDATE_CALENDAR_SUCCESS.getMessage()));
    }
}
