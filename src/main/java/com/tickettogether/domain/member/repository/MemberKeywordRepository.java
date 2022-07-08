package com.tickettogether.domain.member.repository;

import com.tickettogether.domain.member.domain.Member;
import com.tickettogether.domain.member.domain.MemberKeyword;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MemberKeywordRepository extends JpaRepository<MemberKeyword, Long> {

    List<MemberKeyword> findAllByMember(Member member);
}
