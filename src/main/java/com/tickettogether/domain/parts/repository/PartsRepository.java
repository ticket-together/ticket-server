package com.tickettogether.domain.parts.repository;

import com.tickettogether.domain.parts.domain.Parts;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PartsRepository extends JpaRepository<Parts, Long>{

}
