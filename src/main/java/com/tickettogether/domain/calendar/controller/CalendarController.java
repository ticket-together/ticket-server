package com.tickettogether.domain.calendar.controller;

import com.tickettogether.domain.calendar.dto.CalendarDto;
import com.tickettogether.domain.calendar.service.CalendarService;
import com.tickettogether.global.exception.BaseException;
import com.tickettogether.global.exception.BaseResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping(value = "/api/v1/calendar")
public class CalendarController {

    private final CalendarService calendarService;
    private Long memberId = 1L; //테스트 용

    @GetMapping
    public BaseResponse<List<CalendarDto.PostResponse>> getCalendar() throws BaseException {
        try {
            return new BaseResponse<>(calendarService.getCalendar(memberId));
        }
        catch (BaseException e){
            return new BaseResponse<>(e.getStatus());
        }
    }

    @PostMapping
    public BaseResponse<CalendarDto.PostResponse> createCalendar(@RequestBody CalendarDto.PostRequest newCalendar) throws BaseException {
        try {
            return new BaseResponse<>(calendarService.createCalendar(newCalendar, memberId));
        }
        catch (BaseException e){
            return new BaseResponse<>(e.getStatus());
        }
    }
}
