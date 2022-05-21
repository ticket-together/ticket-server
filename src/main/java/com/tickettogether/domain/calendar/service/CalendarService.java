package com.tickettogether.domain.calendar.service;

import com.tickettogether.domain.calendar.domain.Calendar;
import com.tickettogether.domain.calendar.dto.CalendarDto;
import com.tickettogether.domain.calendar.repository.CalendarRepository;
import com.tickettogether.domain.member.domain.Member;
import com.tickettogether.domain.member.repository.MemberRepository;
import com.tickettogether.global.common.Constant;
import com.tickettogether.global.error.exception.BaseException;
import com.tickettogether.global.error.ErrorCode;
import com.tickettogether.infra.s3.S3Service;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;


@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CalendarService {
    private final MemberRepository memberRepository;
    private final CalendarRepository calendarRepository;
    private final S3Service s3Service;
    private final static int CALENDAR_LIMIT_COUNT = 1;

    @Transactional
    public CalendarDto.PostResponse createCalendar(CalendarDto.PostRequest newCalendar, Long memberId, MultipartFile multipartFile) throws BaseException {
        Member member = getMember(memberId);

        if(calendarRepository.countByMemberAndDate(member, LocalDate.parse(newCalendar.getDate())) >= CALENDAR_LIMIT_COUNT){
            throw new BaseException(ErrorCode.POST_CALENDAR_ERROR);
        }

        String uploadImageUrl = s3Service.uploadFileV1(Constant.CATEGORY_CALENDAR, multipartFile);

        Calendar createCalendar = Calendar.builder()
                .member(member)
                .date(LocalDate.parse(newCalendar.getDate()))
                .imgUrl(uploadImageUrl)
                .build();

        return new CalendarDto.PostResponse(calendarRepository.save(createCalendar));
    }

    public List<CalendarDto.PostResponse> getCalendars(Long memberId) throws BaseException {
        Member member = getMember(memberId);
        return calendarRepository.findByMember(member).stream()
                .map(CalendarDto.PostResponse::new)
                .collect(Collectors.toList());
    }

    @Transactional
    public void deleteCalendar(Long calendarId, Long memberId) throws BaseException {
        getMember(memberId);
        getCalendar(calendarId);
        calendarRepository.deleteById(calendarId);
    }

    @Transactional
    public void updateCalendar(Long calendarId, Long memberId, MultipartFile multipartFile, CalendarDto.PostRequest newCalendar) throws BaseException {
        getMember(memberId);
        Calendar calendar = getCalendar(calendarId);

        String uploadImageUrl = s3Service.uploadFileV1(Constant.CATEGORY_CALENDAR, multipartFile);
        calendar.updateCalendar(uploadImageUrl, LocalDate.parse(newCalendar.getDate()));
    }

    private Calendar getCalendar(Long calendarId) throws BaseException {
        return calendarRepository.findById(calendarId)
                .orElseThrow(() -> new BaseException(ErrorCode.EMPTY_CALENDAR_ID));
    }

    private Member getMember(Long memberId) throws BaseException {
        return memberRepository.findById(memberId)
                .orElseThrow(() -> new BaseException(ErrorCode.USERS_EMPTY_USER_ID));
    }
}
