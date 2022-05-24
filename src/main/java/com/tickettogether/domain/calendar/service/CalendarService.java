package com.tickettogether.domain.calendar.service;

import com.tickettogether.domain.calendar.dto.CalendarDto;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface CalendarService {
    CalendarDto.PostResponse createCalendar(CalendarDto.PostRequest newCalendar, Long memberId, MultipartFile multipartFile);
    List<CalendarDto.PostResponse> getCalendars(Long memberId);
    void deleteCalendar(Long calendarId, Long memberId);
    void updateCalendar(Long calendarId, Long memberId, MultipartFile multipartFile, CalendarDto.PostRequest newCalendar);
}
