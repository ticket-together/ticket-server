package com.tickettogether.domain.parts.repository;

import com.tickettogether.domain.member.domain.Member;
import com.tickettogether.domain.parts.domain.MemberParts;
import com.tickettogether.domain.parts.domain.Parts;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MemberPartsRepository extends JpaRepository<MemberParts, Long> {

}
