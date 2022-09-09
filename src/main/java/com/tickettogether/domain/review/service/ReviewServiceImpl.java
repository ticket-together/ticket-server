package com.tickettogether.domain.review.service;

import com.tickettogether.domain.member.domain.Member;
import com.tickettogether.domain.member.exception.UserEmptyException;
import com.tickettogether.domain.member.repository.MemberRepository;
import com.tickettogether.domain.review.domain.Review;
import com.tickettogether.domain.review.dto.ReviewDto;
import com.tickettogether.domain.review.dto.ReviewInfoDto;
import com.tickettogether.domain.review.dto.ReviewSearchCondition;
import com.tickettogether.domain.review.exception.ReviewEmptyException;
import com.tickettogether.domain.review.repository.ReviewRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ReviewServiceImpl implements ReviewService {

    private final ReviewRepository reviewRepository;
    private final MemberRepository memberRepository;

    @Override
    @Transactional
    public ReviewDto.addResponse addReview(Long memberId, String hallName, ReviewDto.addRequest requestDto) {
        Member member = getMember(memberId);

        requestDto.setMember(member);
        requestDto.setHallName(hallName);

        Review review = requestDto.toEntity();

        return new ReviewDto.addResponse(reviewRepository.save(review));
    }

    @Override
    public List<ReviewInfoDto> searchReviewBySeat(String hallName, ReviewSearchCondition condition) {

        condition.setHallName(hallName);
        if (reviewRepository.findReviewBySeat(condition).isEmpty()) {
            throw new ReviewEmptyException();
        }

        return reviewRepository.findReviewBySeat(condition);
    }

    @Override
    @Transactional
    public ReviewDto.addResponse updateReview(String hallName, Long reviewId, ReviewDto.updateRequest requestDto) {
        Review review = getReview(reviewId);

        review.updateReview(requestDto.getStarPoint(), requestDto.getContents(),
                requestDto.getFloor(), requestDto.getPart(), requestDto.getRecord(), requestDto.getNumber());

        return new ReviewDto.addResponse(review);
    }

    @Override
    @Transactional
    public void deleteReview(Long reviewId) {
        Review review = getReview(reviewId);
        reviewRepository.delete(review);
    }

    @Override
    public List<ReviewInfoDto> getReviewsByMember(Long memberId) {
        Member member = getMember(memberId);
        return reviewRepository.findByMemberOrderByCreatedAtDesc(member)
                .stream()
                .map(ReviewInfoDto::new)
                .collect(Collectors.toList());
    }

    private Review getReview(Long reviewId) {
        return reviewRepository.findById(reviewId)
                .orElseThrow(ReviewEmptyException::new);

    }

    private Member getMember(Long memberId) {
        return memberRepository.findById(memberId)
                .orElseThrow(UserEmptyException::new);

    }
}

