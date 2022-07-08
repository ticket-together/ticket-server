package com.tickettogether.domain.culture.repository;

import com.tickettogether.domain.culture.domain.Culture;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CultureRepository extends JpaRepository<Culture, Long> {
}