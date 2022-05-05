package com.tickettogether.domain.review.repository;

import com.tickettogether.domain.review.dto.ReviewInfoDto;
import com.tickettogether.domain.review.dto.ReviewSearchCondition;

import java.util.List;

public interface ReviewSearchRepository {
    List<ReviewInfoDto> searchBySeat (ReviewSearchCondition condition);

}
