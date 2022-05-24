package com.tickettogether.domain.member.repository;

import com.tickettogether.domain.member.domain.MemberKeyword;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberKeywordRepository extends JpaRepository<MemberKeyword, Long> {
}
