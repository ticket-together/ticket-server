package com.tickettogether.domain.review.domain;

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

    private String hallName;

    @Column(precision = 2, scale = 1)
    private BigDecimal starPoint;

    private String contents;

    private String floor;

    private String part;

    private String record;    //row

    private String number;

    @Builder
    public Review(Member member, String hallName, BigDecimal starPoint, String contents, String floor, String part, String record, String number){
        this.member = member;
        this.hallName = hallName;
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
