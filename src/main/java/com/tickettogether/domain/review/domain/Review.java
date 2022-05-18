package com.tickettogether.domain.review.domain;

import com.sun.istack.NotNull;
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
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="review_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="member_id")
    private Member member;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="hall_id")
    private Hall hall;

    @Column(precision = 2, scale = 1)
    private BigDecimal starPoint;

    private String contents;

    private String floor;

    private String part;

    private String record;    //row

    private String number;

    @Builder
    public Review(Member member, Hall hall, BigDecimal starPoint, String contents, String floor, String part, String record, String number){
        this.member=member;
        this.hall = hall;
        this.starPoint = starPoint;
        this.contents = contents;
        this.floor = floor;
        this.part = part;
        this.record = record;
        this.number = number;
    }

    public void updateReview(BigDecimal starPoint, String contents, String floor, String part, String  record, String number){  //DTO 대체
        this.starPoint = starPoint;
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
