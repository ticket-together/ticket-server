package com.tickettogether.domain.review.controller;

import com.tickettogether.domain.review.dto.ReviewDto;
import com.tickettogether.domain.review.dto.ReviewSearchCondition;
import com.tickettogether.domain.review.dto.ReviewInfoDto;
import com.tickettogether.domain.review.service.ReviewService;
import com.tickettogether.global.exception.BaseException;
import com.tickettogether.global.exception.BaseResponse;
import com.tickettogether.global.exception.BaseResponseStatus;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/reviews")
public class ReviewController {

    private final ReviewService reviewService;

    private Long memberId = 1L;  // 테스트 용

    // 리뷰 작성,저장
    @PostMapping("/{hallId}/add")
    public BaseResponse<ReviewDto.addResponse> addReview(@PathVariable("hallId") Long hallId, @RequestBody ReviewDto.addRequest reviewDto){
        try {
            return new BaseResponse<>(reviewService.addReview(memberId, hallId, reviewDto));
        }
        catch (BaseException e){
            return new BaseResponse<>(e.getStatus());
        }
    }


    // 리뷰 조회 페이지 (해당 공연장, 좌석)
    @GetMapping("/{hallId}/search")
    public BaseResponse<List<ReviewInfoDto>> searchReviews(@PathVariable("hallId") Long hallId, ReviewSearchCondition condition) {
        try {
            return new BaseResponse<>(reviewService.searchReviewBySeat(hallId, condition));
        }
        catch (BaseException e) {
            return new BaseResponse<>(e.getStatus());
        }

    }

    // 리뷰 수정
    @PutMapping("/{hallId}/{reviewId}/update")
    public BaseResponse<String> updateReview(@PathVariable("hallId") Long hallId,
                                             @PathVariable("reviewId") Long reviewId,
                                             @RequestBody ReviewDto.updateRequest reviewDto){
        try {
            reviewService.updateReview(hallId, reviewId, reviewDto);
            return new BaseResponse<>(BaseResponseStatus.SUCCESS);
        }
        catch (BaseException e){
            return new BaseResponse<>(e.getStatus());
        }
    }

    // 리뷰 삭제
    @DeleteMapping("/{hallId}/{reviewId}/delete")
    public BaseResponse<String> deleteReview(@PathVariable("hallId") Long hallId, @PathVariable("reviewId") Long reviewId) throws BaseException {
        try {
            reviewService.deleteReview(reviewId);
            return new BaseResponse<>(BaseResponseStatus.SUCCESS);
        }
        catch (BaseException e){
            return new BaseResponse<>(e.getStatus());
        }
    }

}



