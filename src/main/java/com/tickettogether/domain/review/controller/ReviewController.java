package com.tickettogether.domain.review.controller;

import com.tickettogether.domain.review.dto.ReviewDto;
import com.tickettogether.domain.review.service.ReviewService;
import com.tickettogether.global.config.BaseException;
import com.tickettogether.global.config.BaseResponse;
import com.tickettogether.global.config.BaseResponseStatus;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Lazy;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/reviews")
public class ReviewController {

    private final ReviewService reviewService;
    private Long memberId = 1L;

    @GetMapping("/search/{hallId}")
    public BaseResponse<List<ReviewDto.searchResponse>> searchAllReviews(@PathVariable("hallId") Long hallId) {
        // Optional<ReviewDto> reviewList = reviewService.getReviewList(hallId);
        try {
            return new BaseResponse<>(reviewService.getAllReviews(hallId));
        }
        catch (BaseException e){
            return new BaseResponse<>(e.getStatus());
        }
    }

    @GetMapping("/search/{hallId}/?floor=2&part=A&record=2&number=8")
    public BaseResponse<ReviewDto.searchResponse> searchReview(@PathVariable("hallId") Long hallId, @RequestBody ReviewDto.searchRequest request){

    }


    @PostMapping("/add/{hallId}") //ReviewDto.addResponse
    public BaseResponse<?> addReview(@PathVariable("hallId") Long hallId, @ModelAttribute ReviewDto.addRequest request){
        try {
            reviewService.addReview(memberId,request);
            return new BaseResponse(HttpStatus.OK);

        }
        catch (BaseException e){
            return new BaseResponse<>(e.getStatus());
        }
    }

}