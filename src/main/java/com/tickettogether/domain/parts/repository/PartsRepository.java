package com.tickettogether.domain.parts.repository;

import com.tickettogether.domain.culture.domain.Culture;
import com.tickettogether.domain.parts.domain.Parts;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PartsRepository extends JpaRepository<Parts, Long>{
    List<Parts> findByCulture(Culture culture);
}