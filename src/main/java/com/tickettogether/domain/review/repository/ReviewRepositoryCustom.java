package com.tickettogether.domain.review.repository;

import com.tickettogether.domain.review.dto.ReviewInfoDto;
import com.tickettogether.domain.review.dto.ReviewSearchCondition;

import java.util.List;

public interface ReviewRepositoryCustom {
    List<ReviewInfoDto> findReviewBySeat(ReviewSearchCondition condition);
}
