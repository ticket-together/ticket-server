package com.tickettogether.domain.review.dto;

import com.tickettogether.domain.culture.domain.Hall;
import com.tickettogether.domain.member.domain.Member;
import com.tickettogether.domain.review.domain.Review;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class ReviewDto {

    @Getter
    public static class searchRequest{
        private int floor;
        private String part;
        private int record;
        private int number;

    }

    @Getter
    public static class searchResponse{
        private Member member;
        private BigDecimal starPoint;
        private String contents;
        private int floor;
        private String part;
        private int record;
        private int number;


        public searchResponse (Review review){
            this.member = review.getMember();
            this.starPoint = review.getStarPoint();
            this.contents = review.getContents();
            this.floor = review.getFloor();
            this.part = review.getPart();
            this.record = review.getRecord();
            this.number = review.getNumber();
        }

    }

    @Setter
    @Getter
    public static class addRequest{
        private Member member;
        private Hall hall;
        private BigDecimal starPoint;
        private String contents;
        private int floor;
        private String part;
        private int record;
        private int number;


        public addRequest(Review review){
            this.hall = review.getHall();
            this.starPoint = review.getStarPoint();
            this.contents = review.getContents();
            this.floor = review.getFloor();
            this.part = review.getPart();
            this.record = review.getRecord();
            this.number = review.getNumber();
        }

        public Review toEntity(){
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

    @Setter
    public static class addResponse {
        private Member member;
        private Hall hall;
        private BigDecimal starPoint;
        private String contents;
        private int floor;
        private String part;
        private int record;
        private int number;
    }

    @Getter
    public static class updateRequest{
        private Hall hall;
        private BigDecimal starPoint;
        private String contents;
        private int floor;
        private String part;
        private int record;
        private int number;


        public void updateRequest(Review review){
            this.hall = review.getHall();
            this.starPoint = review.getStarPoint();
            this.contents = review.getContents();
            this.floor = review.getFloor();
            this.part = review.getPart();
            this.record = review.getRecord();
            this.number = review.getNumber();
        }

    }

}
