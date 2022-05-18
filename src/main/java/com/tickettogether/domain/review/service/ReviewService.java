package com.tickettogether.domain.review.service;

import com.tickettogether.domain.review.dto.ReviewDto;
import com.tickettogether.domain.review.dto.ReviewInfoDto;
import com.tickettogether.domain.review.dto.ReviewSearchCondition;
import com.tickettogether.global.exception.BaseException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface ReviewService {

    ReviewDto.addResponse addReview(Long memberId, Long hallId, ReviewDto.addRequest requestDto) throws BaseException;

    List<ReviewDto.searchResponse> searchAllReviews(Long hallId) throws BaseException;

    List<ReviewInfoDto> searchReviewBySeat(Long hallId, ReviewSearchCondition condition) throws BaseException;

    ReviewDto.addResponse updateReview(Long hallId, Long reviewId, ReviewDto.updateRequest requestDto) throws BaseException;

    void deleteReview(Long reviewId) throws BaseException;
}

