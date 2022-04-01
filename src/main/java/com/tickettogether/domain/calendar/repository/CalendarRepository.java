package com.tickettogether.domain.calendar.repository;

import com.tickettogether.domain.calendar.domain.Calendar;
import com.tickettogether.domain.member.domain.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

public interface CalendarRepository extends JpaRepository<Calendar, Long> {
    int countByMemberAndDate(Member member, LocalDate date);
    List<Calendar> findByMember(Member member);
}
