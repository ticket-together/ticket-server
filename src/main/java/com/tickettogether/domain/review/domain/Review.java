package com.tickettogether.domain.review.domain;

import com.tickettogether.domain.culture.domain.Hall;
import com.tickettogether.domain.member.domain.Member;
import com.tickettogether.global.entity.BaseEntity;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.math.BigDecimal;

@Entity
@Getter
@NoArgsConstructor(access= AccessLevel.PROTECTED)
public class Review extends BaseEntity {
    @Id @GeneratedValue
    @Column(name="review_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="member_id")
    private Member member;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="hall_id")
    private Hall hall;

    @Column(precision = 2, scale = 1)
    private BigDecimal startPoint;

    private String contents;

    private int floor;

    private String part;

    private int record;    //row

    private int number;

    @Builder
    public Review(BigDecimal startPoint, String contents, int floor, String part, int record, int number){
        this.startPoint = startPoint;
        this.contents = contents;
        this.floor = floor;
        this.part = part;
        this.record = record;
        this.number = number;
    }

    public void addMember(Member member){
        this.member = member;
    }
}
