package com.tickettogether.domain.review.controller;

import com.tickettogether.domain.review.dto.ReviewDto;
import com.tickettogether.domain.review.dto.ReviewSearchCondition;
import com.tickettogether.domain.review.dto.ReviewInfoDto;
import com.tickettogether.domain.review.service.ReviewService;
import com.tickettogether.global.error.dto.BaseResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.tickettogether.domain.review.dto.ReviewResponseMessage.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/reviews")
public class ReviewController {

    private final ReviewService reviewService;

    private Long memberId = 1L;  // 테스트 용

    // 리뷰 작성,저장
    @PostMapping("/{hallId}/add")
    public ResponseEntity<BaseResponse<ReviewDto.addResponse>> addReview(@PathVariable("hallId") Long hallId, @RequestBody ReviewDto.addRequest reviewDto){
        return ResponseEntity.ok(BaseResponse.create(SAVE_REVIEW_SUCCESS.getMessage(),reviewService.addReview(memberId, hallId, reviewDto)));
    }


    // 리뷰 조회 페이지 (해당 공연장, 좌석)
    @GetMapping("/{hallId}/search")
    public ResponseEntity<BaseResponse<List<ReviewInfoDto>>> searchReviews(@PathVariable("hallId") Long hallId, ReviewSearchCondition condition) {
        return ResponseEntity.ok(BaseResponse.create(GET_REVIEW_SUCCESS.getMessage(),reviewService.searchReviewBySeat(hallId, condition)));
    }

    // 리뷰 수정
    @PutMapping("/{hallId}/{reviewId}/update")
    public ResponseEntity<BaseResponse<String>> updateReview(@PathVariable("hallId") Long hallId,
                                                             @PathVariable("reviewId") Long reviewId,
                                                             @RequestBody ReviewDto.updateRequest reviewDto){
        reviewService.updateReview(hallId, reviewId, reviewDto);
        return ResponseEntity.ok(BaseResponse.create(MODIFY_REVIEW_SUCCESS.getMessage()));
    }

    // 리뷰 삭제
    @DeleteMapping("/{hallId}/{reviewId}/delete")
    public ResponseEntity<BaseResponse<String>> deleteReview(@PathVariable("hallId") Long hallId, @PathVariable("reviewId") Long reviewId){
        reviewService.deleteReview(reviewId);
        return ResponseEntity.ok(BaseResponse.create(DELETE_REVIEW_SUCCESS.getMessage()));
    }

}




