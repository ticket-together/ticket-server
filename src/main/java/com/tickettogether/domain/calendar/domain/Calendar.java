package com.tickettogether.domain.calendar.domain;

import com.tickettogether.domain.member.domain.Member;
import com.tickettogether.global.entity.BaseEntity;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.joda.time.DateTime;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Getter
@NoArgsConstructor(access= AccessLevel.PROTECTED)
public class Calendar extends BaseEntity {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="calendar_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="member_id")
    private Member member;

    private LocalDate date;

    @Lob
    private String imgUrl;

    @Builder
    public Calendar(Member member, LocalDate date, String imgUrl){
        this.member = member;
        this.date = date;
        this.imgUrl = imgUrl;
    }

    public void updateCalendar(String imgUrl, LocalDate date){
        this.imgUrl = imgUrl;
        this.date = date;
    }
}
