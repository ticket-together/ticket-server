package com.tickettogether.domain.reservation.repository;

import com.tickettogether.domain.member.domain.Member;
import com.tickettogether.domain.reservation.domain.Reservation;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;


public interface ReservationRepository extends JpaRepository<Reservation, Long> {
    List<Reservation> findByMember(Member member);
}
