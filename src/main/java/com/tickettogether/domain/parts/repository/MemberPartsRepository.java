package com.tickettogether.domain.parts.repository;

import com.tickettogether.domain.parts.domain.MemberParts;
import org.springframework.data.jpa.repository.JpaRepository;


public interface MemberPartsRepository extends JpaRepository<MemberParts, Long> {

}
