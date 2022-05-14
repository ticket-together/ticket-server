package com.tickettogether.domain.review.repository;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.tickettogether.domain.review.dto.QReviewInfoDto;
import com.tickettogether.domain.review.dto.ReviewInfoDto;
import com.tickettogether.domain.review.dto.ReviewSearchCondition;

import javax.persistence.EntityManager;

import static com.tickettogether.domain.culture.domain.QHall.hall;
import static com.tickettogether.domain.member.domain.QMember.member;
import static org.springframework.util.StringUtils.isEmpty;

import java.util.List;

import static com.tickettogether.domain.review.domain.QReview.review;

public class ReviewSearchRepositoryImpl implements ReviewSearchRepositoryCustom {

    private final JPAQueryFactory queryFactory;

    /*
    public ReviewSearchRepositoryImpl(JPAQueryFactory queryFactory){
        this.queryFactory = queryFactory;
    }

*/

    public ReviewSearchRepositoryImpl(EntityManager em){
        this.queryFactory = new JPAQueryFactory(em);
    }

    @Override
    public List<ReviewInfoDto> findBySeat(ReviewSearchCondition condition) {
        return queryFactory
                .select(new QReviewInfoDto(
                        member.id.as("member_Id"),
                        member.name,
                        member.imgUrl,
                        hall.id.as("hall_id"),
                        review.id.as("review_id"),
                        review.starPoint,
                        review.contents,
                        review.floor,
                        review.part,
                        review.record,
                        review.number))
                .from(review)
                .leftJoin(review.member, member)
                .leftJoin(review.hall,hall)
                .where(HallEq(condition.getHallId()),
                        FloorEq(condition.getFloor()),
                        PartEq(condition.getPart()),
                        RecordEq(condition.getRecord()),
                        NumberEq(condition.getNumber()))
                .orderBy(review.floor.asc(), review.part.asc(), review.record.asc(), review.number.asc())
                .fetch();
    }
    private BooleanExpression HallEq(Long hallId) {
        return isEmpty(hallId) ? null : review.hall.id.eq(hallId);
    }
    private BooleanExpression FloorEq(String floor) {
        return isEmpty(floor) ? null : review.floor.eq(floor);
    }
    private BooleanExpression PartEq(String part) {
        return isEmpty(part) ? null : review.part.eq(part);
    }
    private BooleanExpression RecordEq(String record) {
        return isEmpty(record) ? null : review.record.eq(record);
    }
    private BooleanExpression NumberEq(String number) {
        return isEmpty(number) ? null : review.number.eq(number);
    }


}
