package com.tickettogether.domain.member.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.tickettogether.domain.culture.domain.QCulture;
import com.tickettogether.domain.member.domain.Member;
import com.tickettogether.domain.member.domain.QMember;
import com.tickettogether.domain.parts.domain.MemberParts;
import com.tickettogether.domain.parts.domain.QMemberParts;
import com.tickettogether.domain.parts.domain.QParts;
import lombok.RequiredArgsConstructor;

import java.util.Optional;

import static com.tickettogether.domain.culture.domain.QCulture.culture;
import static com.tickettogether.domain.member.domain.QMember.member;
import static com.tickettogether.domain.parts.domain.QMemberParts.memberParts;
import static com.tickettogether.domain.parts.domain.QParts.parts;

@RequiredArgsConstructor
public class MemberRepositoryImpl implements MemberRepositoryCustom {

    private final JPAQueryFactory queryFactory;

    @Override
    public Optional<Member> findMember(Long id) {
        return Optional.ofNullable(queryFactory
                .selectFrom(member)
                .where(member.id.eq(id))
                .innerJoin(member.memberPartsList, memberParts)
                .fetchJoin()
                .leftJoin(memberParts.parts, parts)
                .fetchJoin()
                .leftJoin(parts.culture, culture)
                .fetchJoin()
                .fetchOne());
    }
}
