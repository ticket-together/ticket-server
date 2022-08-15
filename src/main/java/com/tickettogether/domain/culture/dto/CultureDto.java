package com.tickettogether.domain.culture.dto;

import com.tickettogether.domain.culture.domain.Culture;
import com.tickettogether.domain.culture.domain.CultureKeyword;
import lombok.*;
import org.springframework.data.domain.Slice;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class CultureDto {
    @Getter
    @NoArgsConstructor
    public static class CultureResponse{
        private Long cultureId;
        private String startDate;
        private String endDate;
        private CultureKeyword keyword;
        private String imgUrl;
        private String name;
        private String hallName;

        public CultureResponse(Culture culture){
            this.cultureId = culture.getProdId();
            this.startDate = culture.getStartDate().toString();
            this.endDate = culture.getEndDate().toString();
            this.keyword = culture.getKeyword();
            this.imgUrl = culture.getImgUrl();
            this.name = culture.getName();
            this.hallName = culture.getHallName();
        }
    }

    @Getter
    @NoArgsConstructor
    public static class CultureSearchResponse {
        private Boolean hasNext;
        private int page;
        private List<CultureResponse> cultures = new ArrayList<>();

        public CultureSearchResponse(Slice<Culture> cultures){
            this.cultures = cultures.getContent().stream()
                    .map(CultureDto.CultureResponse::new)
                    .collect(Collectors.toList());
            this.hasNext = cultures.hasNext();
            this.page = cultures.getNumber();
        }
    }

    @Getter
    @NoArgsConstructor
    public static class MainCultureResponse{
        private Long cultureId;
        private CultureKeyword keyword;
        private String imgUrl;
        private String name;

        public MainCultureResponse(Culture culture){
            this.cultureId = culture.getProdId();
            this.keyword = culture.getKeyword();
            this.imgUrl = culture.getImgUrl();
            this.name = culture.getName();
        }
    }
}
