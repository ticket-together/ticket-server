package com.tickettogether.domain.review.dto;

import com.tickettogether.domain.member.domain.Member;
import com.tickettogether.domain.review.domain.Review;
import lombok.Getter;
import lombok.Setter;
import java.math.BigDecimal;
import java.util.Locale;

@Getter
@Setter
public class ReviewDto {

    @Setter
    @Getter
    public static class addRequest {

        private Member member;
        private String hallName;
        private BigDecimal starPoint;
        private String contents;
        private String floor;
        private String part;
        private String record;
        private String number;

        public Review toEntity() {
            return Review.builder()
                    .hallName(hallName)
                    .starPoint(starPoint)
                    .contents(contents)
                    .floor(floor.toUpperCase(Locale.ROOT))
                    .part(part.toUpperCase(Locale.ROOT))
                    .record(record.toUpperCase(Locale.ROOT))
                    .number(number.toUpperCase(Locale.ROOT))
                    .build();
        }

    }

    @Getter
    public static class addResponse {
        private Member member;
        private String hallName;
        private BigDecimal starPoint;
        private String contents;
        private String floor;
        private String part;
        private String record;
        private String number;
        private String writeDate;

        public addResponse(Review review) {
            this.member = review.getMember();
            this.hallName = review.getHallName();
            this.starPoint = review.getStarPoint();
            this.contents = review.getContents();
            this.floor = review.getFloor();
            this.part = review.getPart();
            this.record = review.getRecord();
            this.number = review.getNumber();
            this.writeDate = review.getCreatedAt().toString();

        }

    }

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
