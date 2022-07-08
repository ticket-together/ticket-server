package com.tickettogether.domain.culture.dto;

import com.tickettogether.domain.culture.domain.Culture;
import com.tickettogether.domain.culture.domain.CultureKeyword;
import lombok.*;

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
            this.cultureId = culture.getId();
            this.startDate = culture.getStartDate().toString();
            this.endDate = culture.getEndDate().toString();
            this.keyword = culture.getKeyword();
            this.imgUrl = culture.getImgUrl();
            this.name = culture.getName();
            this.hallName = culture.getHallName();
        }
    }
}
