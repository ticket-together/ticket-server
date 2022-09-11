package com.tickettogether.domain.review.controller;

import com.tickettogether.domain.review.dto.ReviewDto;
import com.tickettogether.domain.review.dto.ReviewSearchCondition;
import com.tickettogether.domain.review.dto.ReviewInfoDto;
import com.tickettogether.domain.review.service.ReviewService;
import com.tickettogether.global.error.dto.BaseResponse;
import io.swagger.annotations.ApiOperation;
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

    @ApiOperation(value = "리뷰 작성")
    @PostMapping("/add")
    public ResponseEntity<BaseResponse<ReviewDto.addResponse>> addReview(@RequestParam("hall") String hallName, @RequestBody ReviewDto.addRequest reviewDto) {
        return ResponseEntity.ok(BaseResponse.create(SAVE_REVIEW_SUCCESS.getMessage(), reviewService.addReview(memberId, hallName, reviewDto)));
    }

    @ApiOperation(value = "리뷰 조회", notes = "공연장 이름, 좌석으로 조회")
    @GetMapping("/search")
    public ResponseEntity<BaseResponse<List<ReviewInfoDto>>> searchReviews(@RequestParam("hall") String hallName, ReviewSearchCondition condition) {
        return ResponseEntity.ok(BaseResponse.create(GET_REVIEW_SUCCESS.getMessage(), reviewService.searchReviewBySeat(hallName, condition)));
    }

    @ApiOperation(value = "리뷰 수정")
    @PutMapping("/update")
    public ResponseEntity<BaseResponse<String>> updateReview(@RequestParam("hall") String hallName,
                                                             @RequestParam("reviewId") Long reviewId,
                                                             @RequestBody ReviewDto.updateRequest reviewDto) {
        reviewService.updateReview(hallName, reviewId, reviewDto);
        return ResponseEntity.ok(BaseResponse.create(MODIFY_REVIEW_SUCCESS.getMessage()));
    }

    @ApiOperation(value = "리뷰 삭제")
    @DeleteMapping("/delete")
    public ResponseEntity<BaseResponse<String>> deleteReview(@RequestParam("reviewId") Long reviewId) {
        reviewService.deleteReview(reviewId);
        return ResponseEntity.ok(BaseResponse.create(DELETE_REVIEW_SUCCESS.getMessage()));
    }

    @ApiOperation(value = "다른 멤버가 작성한 리뷰 조회")
    @GetMapping("/{memberId}")
    public ResponseEntity<BaseResponse<List<ReviewInfoDto>>> getReviewsByMember(@PathVariable("memberId") Long memberId) {
        return ResponseEntity.ok(BaseResponse.create(GET_REVIEW_SUCCESS.getMessage(), reviewService.getReviewsByMember(memberId)));
    }

    @ApiOperation(value = "현재 로그인한 멤버가 작성한 리뷰 조회")
    @GetMapping
    public ResponseEntity<BaseResponse<List<ReviewInfoDto>>> getReviewsByCurrentMember() {
        return ResponseEntity.ok(BaseResponse.create(GET_REVIEW_SUCCESS.getMessage(), reviewService.getReviewsByMember(memberId)));
    }
}
