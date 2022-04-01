package com.tickettogether.domain.calendar.service;

import com.tickettogether.domain.calendar.domain.Calendar;
import com.tickettogether.domain.calendar.dto.CalendarDto;
import com.tickettogether.domain.calendar.repository.CalendarRepository;
import com.tickettogether.domain.member.domain.Member;
import com.tickettogether.domain.member.repository.MemberRepository;
import com.tickettogether.global.config.BaseException;
import com.tickettogether.global.config.BaseResponseStatus;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;


@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CalendarService {
    private final MemberRepository memberRepository;
    private final CalendarRepository calendarRepository;
    private final static int CALENDAR_LIMIT_COUNT = 2;

    @Transactional
    public CalendarDto.PostResponse createCalendar(CalendarDto.PostRequest newCalendar, Long memberId) throws BaseException {
        Member member = getMember(memberId);

        if(calendarRepository.countByMemberAndDate(member, LocalDate.parse(newCalendar.getDate())) >= CALENDAR_LIMIT_COUNT){
            throw new BaseException(BaseResponseStatus.POST_CALENDAR_ERROR);
        }

        Calendar createCalendar = Calendar.builder()
                .member(member)
                .date(LocalDate.parse(newCalendar.getDate()))
                .imgUrl(newCalendar.getImgUrl())
                .build();

        return new CalendarDto.PostResponse(calendarRepository.save(createCalendar));
    }

    public List<CalendarDto.PostResponse> getCalendar(Long memberId) throws BaseException {
        Member member = getMember(memberId);
        return calendarRepository.findByMember(member).stream()
                .map(CalendarDto.PostResponse::new)
                .collect(Collectors.toList());
    }

    private Member getMember(Long memberId) throws BaseException {
        return memberRepository.findById(memberId)
                .orElseThrow(() -> new BaseException(BaseResponseStatus.USERS_EMPTY_USER_ID));
    }
}
