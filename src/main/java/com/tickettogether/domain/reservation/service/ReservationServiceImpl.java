package com.tickettogether.domain.reservation.service;

import com.tickettogether.domain.calendar.dto.CalendarDto;
import com.tickettogether.domain.member.domain.Member;
import com.tickettogether.domain.reservation.domain.Reservation;
import com.tickettogether.domain.reservation.dto.ReservationDto;
import com.tickettogether.domain.reservation.repository.ReservationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ReservationServiceImpl implements ReservationService {

    private final ReservationRepository reservationRepository;
    @Override
    public List<ReservationDto.GetResponse> getReservations(Member member) {
        return reservationRepository.findByMember(member).stream()
                .map(ReservationDto.GetResponse::new)
                .collect(Collectors.toList());
    }

}
