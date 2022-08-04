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
    @PostMapping("/add")
    public ResponseEntity<BaseResponse<ReviewDto.addResponse>> addReview(@RequestParam("hall") String hallName, @RequestBody ReviewDto.addRequest reviewDto){
        return ResponseEntity.ok(BaseResponse.create(SAVE_REVIEW_SUCCESS.getMessage(),reviewService.addReview(memberId, hallName, reviewDto)));
    }


    // 리뷰 조회 페이지 (해당 공연장, 좌석)
    @GetMapping("/search")
    public ResponseEntity<BaseResponse<List<ReviewInfoDto>>> searchReviews(@RequestParam("hall") String hallName, ReviewSearchCondition condition) {
        return ResponseEntity.ok(BaseResponse.create(GET_REVIEW_SUCCESS.getMessage(),reviewService.searchReviewBySeat(hallName, condition)));
    }

    // 리뷰 수정
    @PutMapping("/update")
    public ResponseEntity<BaseResponse<String>> updateReview(@RequestParam("hall") String hallName,
                                                             @RequestParam("reviewId") Long reviewId,
                                                             @RequestBody ReviewDto.updateRequest reviewDto){
        reviewService.updateReview(hallName, reviewId, reviewDto);
        return ResponseEntity.ok(BaseResponse.create(MODIFY_REVIEW_SUCCESS.getMessage()));
    }

    // 리뷰 삭제
    @DeleteMapping("/delete")
    public ResponseEntity<BaseResponse<String>> deleteReview(@RequestParam("hall") String hallName, @RequestParam("reviewId") Long reviewId){
        reviewService.deleteReview(reviewId);
        return ResponseEntity.ok(BaseResponse.create(DELETE_REVIEW_SUCCESS.getMessage()));
    }

}




