package com.tickettogether.domain.review.service;

import com.tickettogether.domain.culture.domain.Hall;
import com.tickettogether.domain.culture.repository.HallRepository;
import com.tickettogether.domain.member.domain.Member;
import com.tickettogether.domain.member.repository.MemberRepository;
import com.tickettogether.domain.review.domain.Review;
import com.tickettogether.domain.review.dto.ReviewDto;
import com.tickettogether.domain.review.dto.ReviewInfoDto;
import com.tickettogether.domain.review.dto.ReviewSearchCondition;
import com.tickettogether.domain.review.repository.ReviewRepository;
import com.tickettogether.domain.review.repository.ReviewRepositoryCustom;
import com.tickettogether.global.exception.BaseException;
import com.tickettogether.global.exception.BaseResponseStatus;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class ReviewServiceImpl implements ReviewService{

    private final ReviewRepository reviewRepository;
    private final MemberRepository memberRepository;
    private final HallRepository hallRepository;

    // private final ReviewRepositoryCustom reviewRepositoryCustom;


    private Long memberId = 1L;

    // 리뷰 작성,저장
    @Override
    public ReviewDto.addResponse addReview(Long memberId, Long hallId, ReviewDto.addRequest requestDto) throws BaseException {

        try {
            Member member = getMember(memberId);
            Hall hall = getHall(hallId);

            requestDto.setMember(member);
            requestDto.setHall(hall);

            Review review = requestDto.toEntity();
            reviewRepository.save(review);

            return new ReviewDto.addResponse(reviewRepository.save(review));

        } catch (BaseException e){
            throw new BaseException(e.getStatus());
        }


    }


    // 리뷰 조회 페이지 (해당 공연장)
    @Override
    public List<ReviewDto.searchResponse> searchAllReviews(Long hallId) throws BaseException {

        try {
            Hall hall = getHall(hallId);

            return reviewRepository.findByHall(hall)
                    .stream()
                    .map(ReviewDto.searchResponse :: new)
                    .collect(Collectors.toList());

        } catch (BaseException e){
            throw new BaseException(BaseResponseStatus.FAILED_TO_FIND_REVIEW);
        }
    }

    // 리뷰 조회 페이지 (해당 공연장, 좌석)
    @Override
    public List<ReviewInfoDto> searchReviewBySeat(Long hallId, ReviewSearchCondition condition) throws BaseException {

        condition.setHallId(hallId);
        return reviewRepository.findReviewBySeat(condition);
    }

    // 리뷰 수정
    @Override
    public ReviewDto.addResponse updateReview(Long hallId, Long reviewId, ReviewDto.updateRequest requestDto) throws BaseException{
        try {
            Review review = getReview(reviewId);

            review.updateReview(requestDto.getStarPoint(), requestDto.getContents(),
                    requestDto.getFloor(), requestDto.getPart(), requestDto.getRecord(), requestDto.getNumber());

            return new ReviewDto.addResponse(reviewRepository.save(review));

        } catch (BaseException e){
            throw new BaseException(e.getStatus());
        }
    }


    // 리뷰 삭제
    @Override
    public void deleteReview(Long reviewId) throws BaseException {

        Review review = getReview(reviewId);
        this.reviewRepository.delete(review);
    }

    // 예외처리 커스텀
    private Review getReview(Long reviewId) throws BaseException {
        return reviewRepository.findById(reviewId)
                .orElseThrow(() -> new BaseException(BaseResponseStatus.FAILED_TO_FIND_REVIEW));
    }

    private Member getMember(Long memberId) throws BaseException {
        return memberRepository.findById(memberId)
                .orElseThrow(() -> new BaseException(BaseResponseStatus.USERS_EMPTY_USER_ID));
    }

    private Hall getHall(Long hallId) throws BaseException {
        return hallRepository.findById(hallId)
                .orElseThrow(() -> new BaseException(BaseResponseStatus.FAILED_TO_FIND_HALL));
    }
}
