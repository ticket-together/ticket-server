package com.tickettogether.domain.culture.repository;

import com.tickettogether.domain.culture.domain.Hall;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface HallRepository extends JpaRepository<Hall, Long>{

}