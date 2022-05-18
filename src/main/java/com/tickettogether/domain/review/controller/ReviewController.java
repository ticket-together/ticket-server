package com.tickettogether.domain.review.controller;

import com.tickettogether.domain.review.dto.ReviewDto;
import com.tickettogether.domain.review.dto.ReviewSearchCondition;
import com.tickettogether.domain.review.dto.ReviewInfoDto;
import com.tickettogether.domain.review.repository.ReviewRepositoryCustom;
import com.tickettogether.domain.review.service.ReviewService;
import com.tickettogether.global.exception.BaseException;
import com.tickettogether.global.exception.BaseResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/reviews")
public class ReviewController {

    private final ReviewService reviewService;

    private Long memberId = 1L;
    private ReviewRepositoryCustom reviewRepository;


    // 리뷰 작성,저장
    @PostMapping("/{hallId}/add")
    public BaseResponse<ReviewDto.addResponse> addReview(@PathVariable("hallId") Long hallId, @RequestBody ReviewDto.addRequest reviewDto){
        try {
            reviewService.addReview(memberId, hallId, reviewDto);
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
            reviewService.searchReviewBySeat(hallId, condition);
            return new BaseResponse<>(reviewService.searchReviewBySeat(hallId, condition));
        }

        catch (BaseException e) {
            return new BaseResponse<>(e.getStatus());
        }

    }

    // 리뷰 수정
    @PostMapping("/{hallId}/{reviewId}/update")
    public BaseResponse<ReviewDto.addResponse> updateReview(@PathVariable("hallId") Long hallId,
                                                            @PathVariable("reviewId") Long reviewId,
                                                            @RequestBody ReviewDto.updateRequest reviewDto){
        try {
            reviewService.updateReview(hallId, reviewId, reviewDto);
            return new BaseResponse<>(reviewService.updateReview(hallId, reviewId, reviewDto));
        }
        catch (BaseException e){
            return new BaseResponse<>(e.getStatus());
        }
    }

    // 리뷰 삭제
    @PostMapping("/{hallId}/{reviewId}/delete")
    public void deleteReview(@PathVariable("hallId") Long hallId, @PathVariable("reviewId") Long reviewId) throws BaseException {
        reviewService.deleteReview(reviewId);
    }

}



