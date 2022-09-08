package com.tickettogether.domain.review.dto;

import com.querydsl.core.annotations.QueryProjection;
import com.tickettogether.domain.review.domain.Review;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class ReviewInfoDto {
    private Long memberId;
    private String name;
    private String imgUrl;
    private String hallName;
    private Long reviewId;
    private BigDecimal starPoint;
    private String contents;
    private String floor;
    private String part;
    private String record;
    private String number;

    @QueryProjection
    public ReviewInfoDto(Long memberId, String name, String imgUrl, Long reviewId, String hallName, BigDecimal starPoint, String contents, String floor, String part, String record, String number) {
        this.memberId = memberId;
        this.name = name;
        this.imgUrl = imgUrl;
        this.hallName = hallName;
        this.reviewId = reviewId;
        this.starPoint = starPoint;
        this.contents = contents;
        this.floor = floor;
        this.part = part;
        this.record = record;
        this.number = number;
    }

    public ReviewInfoDto(Review review) {
        this.memberId = review.getMember().getId();
        this.name = review.getMember().getName();
        this.imgUrl = review.getMember().getImgUrl();
        this.hallName = review.getHallName();
        this.reviewId = review.getId();
        this.starPoint = review.getStarPoint();
        this.contents = review.getContents();
        this.floor = review.getFloor();
        this.part = review.getPart();
        this.record = review.getRecord();
        this.number = review.getNumber();
    }
}
