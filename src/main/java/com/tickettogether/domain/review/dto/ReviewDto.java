package com.tickettogether.domain.review.dto;

import com.tickettogether.domain.culture.domain.Hall;
import com.tickettogether.domain.member.domain.Member;
import com.tickettogether.domain.review.domain.Review;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class ReviewDto {

    // 리뷰 저장시 request
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

    // 리뷰 저장시 response
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
        private String writeDate;

        public addResponse(Review review) {
            this.member = review.getMember();
            this.hall = review.getHall();
            this.starPoint = review.getStarPoint();
            this.contents = review.getContents();
            this.floor = review.getFloor();
            this.part = review.getPart();
            this.record = review.getRecord();
            this.number = review.getNumber();
            this.writeDate = review.getCreatedAt().toString();

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
