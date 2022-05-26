package com.tickettogether.domain.review.service;

import com.tickettogether.domain.culture.domain.Hall;
import com.tickettogether.domain.culture.repository.HallRepository;
import com.tickettogether.domain.member.domain.Member;
import com.tickettogether.domain.member.exception.UserEmptyException;
import com.tickettogether.domain.member.repository.MemberRepository;
import com.tickettogether.domain.review.domain.Review;
import com.tickettogether.domain.review.dto.ReviewDto;
import com.tickettogether.domain.review.dto.ReviewInfoDto;
import com.tickettogether.domain.review.dto.ReviewSearchCondition;
import com.tickettogether.domain.review.exception.HallEmptyException;
import com.tickettogether.domain.review.exception.ReviewEmptyException;
import com.tickettogether.domain.review.repository.ReviewRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class ReviewServiceImpl implements ReviewService{

    private final ReviewRepository reviewRepository;
    private final MemberRepository memberRepository;
    private final HallRepository hallRepository;

    private Long memberId = 1L;

    // 리뷰 작성,저장
    @Override
    public ReviewDto.addResponse addReview(Long memberId, Long hallId, ReviewDto.addRequest requestDto) {
        Member member = getMember(memberId);
        Hall hall = getHall(hallId);

        requestDto.setMember(member);
        requestDto.setHall(hall);

        Review review = requestDto.toEntity();

        return new ReviewDto.addResponse(reviewRepository.save(review));
    }


    // 리뷰 조회 페이지 (해당 공연장, 좌석)
    @Override
    public List<ReviewInfoDto> searchReviewBySeat(Long hallId, ReviewSearchCondition condition) {

        getHall(hallId);
        condition.setHallId(hallId);

        if (reviewRepository.findReviewBySeat(condition).isEmpty()) throw new ReviewEmptyException();
        return reviewRepository.findReviewBySeat(condition);
    }

    // 리뷰 수정
    @Override
    public ReviewDto.addResponse updateReview(Long hallId, Long reviewId, ReviewDto.updateRequest requestDto) {
        Review review = getReview(reviewId);

        review.updateReview(requestDto.getStarPoint(), requestDto.getContents(),
                requestDto.getFloor(), requestDto.getPart(), requestDto.getRecord(), requestDto.getNumber());

        return new ReviewDto.addResponse(review);
    }


    // 리뷰 삭제
    @Override
    public void deleteReview(Long reviewId) {

        Review review = getReview(reviewId);
        this.reviewRepository.delete(review);
    }

    // 예외처리 커스텀
    private Review getReview(Long reviewId) {
        return reviewRepository.findById(reviewId)
                .orElseThrow(ReviewEmptyException::new);

    }

    private Member getMember(Long memberId) {
        return memberRepository.findById(memberId)
                .orElseThrow(UserEmptyException::new);

    }

    private Hall getHall(Long hallId) {
        return hallRepository.findById(hallId)
                .orElseThrow(HallEmptyException::new);
    }

}

