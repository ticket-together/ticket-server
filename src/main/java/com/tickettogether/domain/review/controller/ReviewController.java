package com.tickettogether.domain.review.controller;

import com.tickettogether.domain.review.dto.ReviewDto;
import com.tickettogether.domain.review.dto.ReviewSearchCondition;
import com.tickettogether.domain.review.dto.ReviewInfoDto;
import com.tickettogether.domain.review.repository.ReviewRepository;
import com.tickettogether.domain.review.repository.ReviewSearchRepository;
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
    private final ReviewRepository reviewRepository;
    private final ReviewSearchRepository reviewSearchRepository;

    private Long memberId = 1L;


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


    // 리뷰 조회 페이지 (해당 공연장)
    @GetMapping("/{hallId}/search")
    public BaseResponse<List<ReviewDto.searchResponse>> searchAllReviews(@PathVariable("hallId") Long hallId) {

        try {
            reviewService.searchAllReviews(hallId);
            return new BaseResponse<>(reviewService.searchAllReviews(hallId));
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

   /*
    // 리뷰 조회 페이지 (해당 공연장, 좌석)  "/{hallId}/search?floor=2&part=A&record=2&number=8"
    @GetMapping("/{hallId}/search")
    public BaseResponse<ReviewDto.searchResponse> searchReview(@PathVariable("hallId") Long hallId,
                                                               @RequestParam(value = "floor", required = false) String floor,
                                                               @RequestParam(value ="part", required = false) String part,
                                                               @RequestParam(value ="record", required = false) String record,
                                                               @RequestParam(value ="number", required = false) String number){
        try {
            /*
            reviewService.searchReviews(hallId, floor, part, record, number);
            return new BaseResponse(reviewService.searchReviews(hallId, floor, part, record, number));






        } catch (BaseException e) {
            return new BaseResponse<>(e.getStatus());
        }
    }
    */
    // 리뷰 수정
    @PatchMapping("/{hallId}/{reviewId}/update")
    public BaseResponse<ReviewDto.addResponse> updateReview(@PathVariable("hallId") Long hallId,
                                                            @PathVariable("reviewId") Long reviewId,
                                                            @RequestBody ReviewDto.updateRequest reviewDto){
        try {
            reviewService.updateReview(reviewId, reviewDto);
            return new BaseResponse<>(reviewService.updateReview(reviewId, reviewDto));
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
