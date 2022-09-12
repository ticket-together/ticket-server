package com.tickettogether.domain.member.repository;

import com.tickettogether.domain.member.domain.Member;
import java.util.Optional;

public interface MemberRepositoryCustom {
    Optional<Member> findMember(Long id);
}
