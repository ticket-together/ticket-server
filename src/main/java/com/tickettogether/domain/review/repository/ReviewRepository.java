package com.tickettogether.domain.review.repository;

import com.tickettogether.domain.review.domain.Review;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReviewRepository extends JpaRepository<Review, Long> , ReviewRepositoryCustom {

}