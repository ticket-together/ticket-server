package com.tickettogether.domain.review.repository;

import com.tickettogether.domain.culture.domain.Hall;
import com.tickettogether.domain.member.domain.Member;
import com.tickettogether.domain.review.domain.Review;
import org.springframework.data.jpa.repository.JpaRepository;


import java.util.Optional;

public interface ReviewRepository extends JpaRepository<Review, Long> , ReviewRepositoryCustom {

    Optional<Review> findByHall(Hall hall);
    Optional<Review> findByMember(Member member);
}


