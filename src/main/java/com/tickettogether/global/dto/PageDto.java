package com.tickettogether.global.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
import lombok.experimental.Accessors;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.Sort;

import java.util.List;

@Getter
@Builder(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class PageDto<T> {

    private int pageNumber;

    @JsonProperty("isFirst")
    private boolean first;

    @JsonProperty("isLast")
    private boolean last;

    @JsonProperty("hasNext")
    @Accessors(fluent = true)
    private boolean hasNext;

    @JsonProperty("isEmpty")
    private boolean empty;

    private Sort sort;

    private List<T> contents;

    public static <T> PageDto<T> create(Slice<T> page, List<T> contents) {
        return PageDto.<T>builder()
                .pageNumber(page.getNumber() + 1)
                .first(page.isFirst())
                .last(page.isLast())
                .hasNext(page.hasNext())
                .empty(page.isEmpty())
                .sort(page.getSort())
                .contents(contents)
                .build();
    }
}
