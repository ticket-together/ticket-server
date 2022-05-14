package com.tickettogether.domain.review.repository;

import com.tickettogether.domain.culture.domain.Hall;
import com.tickettogether.domain.member.domain.Member;
import com.tickettogether.domain.review.domain.Review;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ReviewRepository extends JpaRepository<Review, Long> , ReviewSearchRepositoryCustom {

    Optional<Review> findByHall(Hall hall);
    Optional<Review> findByMember(Member member);
}


