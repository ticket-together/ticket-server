package com.tickettogether.domain.member.repository;

import com.tickettogether.domain.member.domain.Keyword;
import org.springframework.data.jpa.repository.JpaRepository;

public interface KeywordRepository extends JpaRepository<Keyword, Long> {

}
