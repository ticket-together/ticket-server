package com.tickettogether.domain.reservation.domain;

import com.tickettogether.domain.member.domain.Member;
import lombok.Getter;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Getter
public class Reservation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="reservation_id")
    private Long id;

    private String number;

    private String name;

    private String imgUrl;

    private String hallName;

    private LocalDate date;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="member_id")
    private Member member;

    public void addMember(Member member){
        this.member = member;
    }
}
