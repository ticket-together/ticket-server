package com.tickettogether.domain.calendar.controller;

import com.tickettogether.domain.calendar.dto.CalendarDto;
import com.tickettogether.domain.calendar.service.CalendarService;
import com.tickettogether.global.error.dto.BaseResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

import static com.tickettogether.domain.calendar.dto.CalendarResponseMessage.*;

@RequiredArgsConstructor
@RestController
@RequestMapping(value = "/api/v1/calendar")
public class CalendarController {

    private final CalendarService calendarService;
    private Long memberId = 1L; //테스트 용

    @GetMapping
    public ResponseEntity<BaseResponse<List<CalendarDto.PostResponse>>> getCalendars(){
        return ResponseEntity.ok(BaseResponse.create(GET_CALENDARS_SUCCESS.getMessage(),calendarService.getCalendars(memberId)));
    }

    @PostMapping
    public ResponseEntity<BaseResponse<CalendarDto.PostResponse>> createCalendar(
            @RequestPart("files") MultipartFile multipartFile,
            @RequestPart("calendar") CalendarDto.PostRequest newCalendar){
        return ResponseEntity.ok(BaseResponse.create(POST_CALENDAR_SUCCESS.getMessage(),calendarService.createCalendar(newCalendar, memberId, multipartFile)));
    }

    @DeleteMapping("/{calendarId}")
    public ResponseEntity<BaseResponse<String>> deleteCalendar(@PathVariable("calendarId") Long calendarId) {
        calendarService.deleteCalendar(calendarId, memberId);
        return ResponseEntity.ok(BaseResponse.create(DELETE_CALENDAR_SUCCESS.getMessage()));
    }

    @PutMapping("/{calendarId}")
    public ResponseEntity<BaseResponse<String>> updateCalendar(@PathVariable("calendarId") Long calendarId,
                                                               @RequestPart("files") MultipartFile multipartFile,
                                                               @RequestPart("calendar") CalendarDto.PostRequest newCalendar) {
        calendarService.updateCalendar(calendarId, memberId, multipartFile, newCalendar);
        return ResponseEntity.ok(BaseResponse.create(UPDATE_CALENDAR_SUCCESS.getMessage()));
    }

}
