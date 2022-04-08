package com.tickettogether.domain.review.service;

import com.tickettogether.domain.culture.domain.Hall;
import com.tickettogether.domain.culture.repository.HallRepository;
import com.tickettogether.domain.member.domain.Member;
import com.tickettogether.domain.member.repository.MemberRepository;
import com.tickettogether.domain.review.domain.Review;
import com.tickettogether.domain.review.dto.ReviewDto;
import com.tickettogether.domain.review.repository.ReviewRepository;
import com.tickettogether.global.config.BaseException;
import com.tickettogether.global.config.BaseResponseStatus;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ReviewService {

    private final ReviewRepository reviewRepository;
    private final MemberRepository memberRepository;
    private final HallRepository hallRepository;

    private Long memberId = 1L;

    @Transactional
    public List<ReviewDto.searchResponse> getAllReviews(Long hallId) throws BaseException {

        // 리뷰dto 리스트 만들기
        List<ReviewDto.searchResponse> reviewDtoList = new ArrayList<>();

        // id로 공연장 정보 가져오기
        Hall hall = hallRepository.findById(hallId)
                .orElseThrow(() -> new BaseException(BaseResponseStatus.FAILED_TO_FIND_HALL));


        // 공연장 정보로 리뷰 리스트 가져오기
        Optional<Review> reviewList = reviewRepository.findByHall(hall);

        // 리뷰 리스트의 요소들을 ReviewDto로 변환하여 List로 변환
        return reviewList.stream()
                .map(ReviewDto.searchResponse::new)
                .collect(Collectors.toList());
    }

    @Transactional
    public List<ReviewDto.searchResponse> getReviews(Long hallId, ReviewDto.searchRequest request) throws BaseException {

    }

    @Transactional
    public void addReview(Long memberId, ReviewDto.addRequest request) throws BaseException {
        Optional<Member> findMember = memberRepository.findById(memberId);
        request.setMember(findMember.get());
        Review review = request.toEntity();
        reviewRepository.save(review);
    }

    @Transactional
    public void updateReview(Long reviewId, ReviewDto.updateRequest updateDto) throws BaseException{
        Review review = reviewRepository.findById(reviewId)
                .orElseThrow(() -> new BaseException(BaseResponseStatus.FAILED_TO_FIND_REVIEW));
        // review.updateReview(updateDto);

    }

    @Transactional
    public void deleteReview(Long reviewId){
        this.reviewRepository.deleteById(reviewId);
    }

}
