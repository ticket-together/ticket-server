package com.tickettogether.domain.calendar.controller;

import com.tickettogether.domain.calendar.dto.CalendarDto;
import com.tickettogether.domain.calendar.service.CalendarService;
import com.tickettogether.global.exception.BaseException;
import com.tickettogether.global.exception.BaseResponse;
import com.tickettogether.global.exception.BaseResponseStatus;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping(value = "/api/v1/calendar")
public class CalendarController {

    private final CalendarService calendarService;
    private Long memberId = 1L; //테스트 용

    @GetMapping
    public BaseResponse<List<CalendarDto.PostResponse>> getCalendars(){
        try {
            return new BaseResponse<>(calendarService.getCalendars(memberId));
        }
        catch (BaseException e){
            return new BaseResponse<>(e.getStatus());
        }
    }

    @PostMapping
    public BaseResponse<CalendarDto.PostResponse> createCalendar(
            @RequestPart("files") MultipartFile multipartFile,
            @RequestPart("calendar") CalendarDto.PostRequest newCalendar){
        try {
            return new BaseResponse<>(calendarService.createCalendar(newCalendar, memberId, multipartFile));
        }
        catch (BaseException e){
            return new BaseResponse<>(e.getStatus());
        }
    }

    @DeleteMapping("/{calendarId}")
    public BaseResponse<String> deleteCalendar(@PathVariable("calendarId") Long calendarId) {
        try {
            calendarService.deleteCalendar(calendarId, memberId);
            return new BaseResponse<>(BaseResponseStatus.SUCCESS);
        } catch (BaseException e){
            return new BaseResponse<>(e.getStatus());
        }
    }

    @PutMapping("/{calendarId}")
    public BaseResponse<String> updateCalendar(@PathVariable("calendarId") Long calendarId,
                                               @RequestPart("files") MultipartFile multipartFile,
                                               @RequestPart("calendar") CalendarDto.PostRequest newCalendar) {
        try {
            calendarService.updateCalendar(calendarId, memberId, multipartFile, newCalendar);
            return new BaseResponse<>(BaseResponseStatus.SUCCESS);
        } catch (BaseException e){
            return new BaseResponse<>(e.getStatus());
        }
    }

}
