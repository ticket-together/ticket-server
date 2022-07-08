package com.tickettogether.domain.culture.repository;

import com.tickettogether.domain.culture.domain.Culture;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface CultureRepository extends JpaRepository<Culture, Long> {
    @Query("SELECT distinct c FROM Culture c  where (c.name like concat('%', :query, '%')  or c.hallName like concat('%', :query, '%'))")
    Slice<Culture> searchCultureByName(Pageable pageable, @Param("query") String query);
}