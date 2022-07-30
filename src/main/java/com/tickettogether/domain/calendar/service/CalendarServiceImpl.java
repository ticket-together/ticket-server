package com.tickettogether.domain.calendar.service;

import com.tickettogether.domain.calendar.domain.Calendar;
import com.tickettogether.domain.calendar.dto.CalendarDto;
import com.tickettogether.domain.calendar.exception.CalendarBusinessException;
import com.tickettogether.domain.calendar.exception.CalendarEmptyException;
import com.tickettogether.domain.calendar.repository.CalendarRepository;
import com.tickettogether.domain.member.domain.Member;
import com.tickettogether.domain.member.service.MemberService;
import com.tickettogether.global.common.Constant;
import com.tickettogether.infra.s3.S3Service;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;


@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CalendarServiceImpl implements CalendarService{
    private final static int CALENDAR_LIMIT_COUNT = 1;
    private final CalendarRepository calendarRepository;
    private final MemberService memberService;
    private final S3Service s3Service;

    @Transactional
    public CalendarDto.PostResponse createCalendar(CalendarDto.PostRequest newCalendar, Long memberId){
        Member member = memberService.findMemberById(memberId);

        if(calendarRepository.countByMemberAndDate(member, LocalDate.parse(newCalendar.getDate())) >= CALENDAR_LIMIT_COUNT){
            throw new CalendarBusinessException();
        }

        Calendar createCalendar = Calendar.builder()
                .member(member)
                .date(LocalDate.parse(newCalendar.getDate()))
                .imgUrl(newCalendar.getImgUrl())
                .build();

        return new CalendarDto.PostResponse(calendarRepository.save(createCalendar));
    }

    public List<CalendarDto.PostResponse> getCalendars(Long memberId){
        Member member = memberService.findMemberById(memberId);
        return calendarRepository.findByMember(member).stream()
                .map(CalendarDto.PostResponse::new)
                .collect(Collectors.toList());
    }

    @Transactional
    public void deleteCalendar(Long calendarId, Long memberId){
        memberService.findMemberById(memberId);
        getCalendar(calendarId);
        calendarRepository.deleteById(calendarId);
    }

    @Transactional
    public void updateCalendar(Long calendarId, Long memberId, MultipartFile multipartFile, CalendarDto.PostRequest newCalendar){
        memberService.findMemberById(memberId);
        Calendar calendar = getCalendar(calendarId);
        String uploadImageUrl = s3Service.uploadFileV1(Constant.CATEGORY_CALENDAR, multipartFile);
        calendar.updateCalendar(uploadImageUrl, LocalDate.parse(newCalendar.getDate()));
    }

    private Calendar getCalendar(Long calendarId){
        return calendarRepository.findById(calendarId)
                .orElseThrow(CalendarEmptyException::new);
    }
}
