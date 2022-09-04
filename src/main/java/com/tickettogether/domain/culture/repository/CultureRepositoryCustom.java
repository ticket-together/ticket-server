package com.tickettogether.domain.culture.repository;

import com.tickettogether.domain.culture.domain.Culture;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;

public interface CultureRepositoryCustom {
    Slice<Culture> searchCultureByQuery(Pageable pageable, String query);
}
