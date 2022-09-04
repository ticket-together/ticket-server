package com.tickettogether.domain.culture.repository;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.tickettogether.domain.culture.domain.Culture;
import com.tickettogether.domain.culture.domain.CultureKeyword;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.SliceImpl;
import java.util.List;
import static com.tickettogether.domain.culture.domain.QCulture.culture;
import static org.springframework.util.StringUtils.hasText;

@RequiredArgsConstructor
public class CultureRepositoryImpl implements CultureRepositoryCustom {
    private final JPAQueryFactory queryFactory;

    @Override
    public Slice<Culture> searchCultureByQuery(Pageable pageable, String query) {
        List<Culture> cultures = queryFactory.selectFrom(culture)
                .where(cultureLike(query))
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize() + 1)
                .fetch();

        boolean hasNext = false;
        if (cultures.size() > pageable.getPageSize()) {
            cultures.remove(pageable.getPageSize());
            hasNext = true;
        }
        return new SliceImpl<>(cultures, pageable, hasNext);
    }

    private BooleanExpression cultureLike(String query) {
        return hasText(query) ? culture.name.contains(query).or(culture.hallName.contains(query)) : null;
    }
}
