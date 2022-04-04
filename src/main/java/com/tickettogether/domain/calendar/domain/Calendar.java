package com.tickettogether.domain.calendar.domain;

import com.tickettogether.domain.member.domain.Member;
import com.tickettogether.global.entity.BaseEntity;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

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

    private LocalDateTime date;

    @Lob
    private String img_url;

    @Builder
    public Calendar(Member member, LocalDateTime date, String img_url){
        this.member = member;
        this.date = date;
        this.img_url = img_url;
    }
}
