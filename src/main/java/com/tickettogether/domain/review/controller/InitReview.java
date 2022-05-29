package com.tickettogether.domain.review.controller;

import com.tickettogether.domain.culture.domain.Hall;
import com.tickettogether.domain.member.domain.Member;
import com.tickettogether.domain.review.domain.Review;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

import java.math.BigDecimal;

import static com.tickettogether.domain.member.domain.Role.GUEST;

@Profile("dev")
@Component
@RequiredArgsConstructor
public class InitReview {

    private final InitReviewService initReviewService;
    @PostConstruct
    public void init() {
        initReviewService.init();
    }
    @Component
    static class InitReviewService {

        @PersistenceContext
        EntityManager em;

        @Transactional
        public void init() {

            Member member1 = new Member("yy@naver.com", "abc1", "영현", "profile1", GUEST);
            Member member2 = new Member("yyhhh@naver.com", "abch1", "영현h", "profile1h", GUEST);

//            em.persist(member1);
//            em.persist(member2);

            Hall hall1 = new Hall("잠실 경기장");
            Hall hall2 = new Hall("올림픽 공연장");

//            em.persist(hall1);
//            em.persist(hall2);

            Review review1 = new Review(member1, hall1, BigDecimal.valueOf(5), "content", "2", "A", "1", "1");
            Review review2 = new Review(member1, hall2, BigDecimal.valueOf(3), "content1", "3", "B", "3", "12");
            Review review3 = new Review(member1, hall2, BigDecimal.valueOf(4), "content2", "2", "C", "2", "13");
            Review review4 = new Review(member2, hall2, BigDecimal.valueOf(1), "content3", "2", "A", "12", "21");

//            em.persist(review1);
//            em.persist(review2);
//            em.persist(review3);
//            em.persist(review4);

        }
    }
}