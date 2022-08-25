package com.tickettogether.domain.parts.dto;

import com.tickettogether.domain.culture.domain.Culture;
import com.tickettogether.domain.member.domain.Member;
import com.tickettogether.domain.parts.domain.MemberParts;
import com.tickettogether.domain.parts.domain.Parts;
import lombok.*;

import java.time.LocalDate;

public class PartsDto {

    @Getter
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    @AllArgsConstructor
    public static class createRequest{
        private Member manager;
        private Culture culture;
        private String partName;
        private String partContent;
        private Integer partTotal;
        private LocalDate partDate;
        private Parts.Status status;
    }

    @Getter
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    @AllArgsConstructor
    public static class createResponse{
        private Long managerId;
        private String cultureName;
        private String cultureImgUrl;
        private String partName;
        private String partContent;
        private Integer partTotal;
        private LocalDate partDate;
        private Parts.Status status;

        public createResponse(MemberParts memberParts) {
            this.managerId = memberParts.getManager().getId();
            this.cultureName = memberParts.getParts().getCulture().getName();
            this.cultureImgUrl = memberParts.getParts().getCulture().getImgUrl();
            this.partName = memberParts.getParts().getPartName();
            this.partContent = memberParts.getParts().getPartContent();
            this.partTotal = memberParts.getParts().getPartTotal();
            this.partDate = memberParts.getParts().getPartDate();
            this.status = memberParts.getParts().getStatus();
        }

    }
}
