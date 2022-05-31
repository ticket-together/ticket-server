package com.tickettogether.domain.review.service;

import com.tickettogether.domain.review.dto.ReviewDto;
import com.tickettogether.domain.review.dto.ReviewInfoDto;
import com.tickettogether.domain.review.dto.ReviewSearchCondition;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface ReviewService {

    ReviewDto.addResponse addReview(Long memberId, Long hallId, ReviewDto.addRequest requestDto);

    List<ReviewInfoDto> searchReviewBySeat(Long hallId, ReviewSearchCondition condition);

    ReviewDto.addResponse updateReview(Long hallId, Long reviewId, ReviewDto.updateRequest requestDto);

    void deleteReview(Long reviewId);
}

