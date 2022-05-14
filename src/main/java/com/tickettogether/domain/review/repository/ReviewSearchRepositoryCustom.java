package com.tickettogether.domain.review.repository;

import com.tickettogether.domain.review.dto.ReviewInfoDto;
import com.tickettogether.domain.review.dto.ReviewSearchCondition;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReviewSearchRepositoryCustom {
    List<ReviewInfoDto> findBySeat (ReviewSearchCondition condition);
}
