package com.tickettogether.domain.culture.repository;

import com.tickettogether.domain.culture.domain.Hall;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface HallRepository {
    Optional <Hall> findById (Long id);
}
