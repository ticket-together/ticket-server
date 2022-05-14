package com.tickettogether.domain.review.dto;

import com.querydsl.core.annotations.QueryProjection;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class ReviewInfoDto {

    private Long memberId;
    private String name;
    private String imgUrl;
    private Long hallId;
    private Long reviewId;
    private BigDecimal starPoint;
    private String contents;
    private String floor;
    private String part;
    private String record;
    private String number;

    @QueryProjection
    public ReviewInfoDto(Long memberId, String name, String imgUrl, Long hallId, Long reviewId, BigDecimal starPoint, String contents, String floor, String part, String record, String number){
        this.memberId = memberId;
        this.name = name;
        this.imgUrl = imgUrl;
        this.hallId = hallId;
        this.reviewId = reviewId;
        this.starPoint = starPoint;
        this.contents = contents;
        this.floor = floor;
        this.part = part;
        this.record = record;
        this.number = number;

    }
}
