package com.tickettogether.domain.review.repository;

import com.tickettogether.domain.member.domain.Member;
import com.tickettogether.domain.review.domain.Review;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface ReviewRepository extends JpaRepository<Review, Long>, ReviewRepositoryCustom {
    List<Review> findByMemberOrderByCreatedAtDesc(Member member);
}