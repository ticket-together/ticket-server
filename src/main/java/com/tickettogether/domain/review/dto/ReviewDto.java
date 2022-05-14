package com.tickettogether.domain.review.dto;

import com.tickettogether.domain.culture.domain.Hall;
import com.tickettogether.domain.member.domain.Member;
import com.tickettogether.domain.review.domain.Review;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class ReviewDto {

    // 리뷰 저장 or 수정시 request
    @Setter
    @Getter
    public static class addRequest {
        private Member member;
        private Hall hall;
        private BigDecimal starPoint;
        private String contents;
        private String floor;
        private String part;
        private String record;
        private String number;

        // dto -> entity
        public Review toEntity() {
            return Review.builder()
                    .hall(hall)
                    .starPoint(starPoint)
                    .contents(contents)
                    .floor(floor)
                    .part(part)
                    .record(record)
                    .number(number)
                    .build();
        }

    }

    // 리뷰 저장 페이지에 전달할 데이터
    @Getter
    public static class addResponse {
        private Member member;
        private Hall hall;
        private BigDecimal starPoint;
        private String contents;
        private String floor;
        private String part;
        private String record;
        private String number;

        // entity -> dto
        public addResponse(Review review) {
            this.member = review.getMember();
            this.hall = review.getHall();
            this.starPoint = review.getStarPoint();
            this.contents = review.getContents();
            this.floor = review.getFloor();
            this.part = review.getPart();
            this.record = review.getRecord();
            this.number = review.getNumber();

        }

    }

    // 상세 조회시 request
    @Data
    public static class searchRequest {
        private Long hallId;
        private String floor;
        private String part;
        private String record;
        private String number;

        public searchRequest(Long hallId,String floor, String part, String record, String number){
            this.hallId = hallId;
            this.floor = floor;
            this.part = part;
            this.record = record;
            this.number = number;
        }
    }

    // 전체 or 상세 조회 페이지에 전달할 데이터
    @Getter
    public static class searchResponse {
        private Member member;
        private BigDecimal starPoint;
        private String contents;
        private String floor;
        private String part;
        private String record;
        private String number;


        public searchResponse(Review review) {
            this.member = review.getMember();
            this.starPoint = review.getStarPoint();
            this.contents = review.getContents();
            this.floor = review.getFloor();
            this.part = review.getPart();
            this.record = review.getRecord();
            this.number = review.getNumber();
        }

    }


    // 리뷰 수정시 request
    @Getter
    @Setter
    public static class updateRequest {
        private BigDecimal starPoint;
        private String contents;
        private String floor;
        private String part;
        private String record;
        private String number;

    }

}
