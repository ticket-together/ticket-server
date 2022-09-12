package com.tickettogether.domain.parts.dto;

import com.tickettogether.domain.member.domain.Role;
import com.tickettogether.domain.parts.domain.MemberParts;
import com.tickettogether.domain.parts.domain.Parts;
import lombok.*;
import java.time.LocalDate;

public class PartsDto {

    @Getter
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    @AllArgsConstructor
    public static class CreateRequest{
        private String partName;
        private String partContent;
        private LocalDate partDate;
        private Integer partTotal;
    }

    @Getter
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    @AllArgsConstructor
    public static class CreateResponse{
        private Long managerId;
        private String cultureName;
        private String cultureImgUrl;
        private String partName;
        private String partContent;
        private Integer partTotal;
        private LocalDate partDate;
        private Parts.Status status;

        public CreateResponse(MemberParts memberParts) {
            this.managerId = memberParts.getParts().getManager().getId();
            this.cultureName = memberParts.getParts().getCulture().getName();
            this.cultureImgUrl = memberParts.getParts().getCulture().getImgUrl();
            this.partName = memberParts.getParts().getPartName();
            this.partContent = memberParts.getParts().getPartContent();
            this.partTotal = memberParts.getParts().getPartTotal();
            this.partDate = memberParts.getParts().getPartDate();
            this.status = memberParts.getParts().getStatus();
        }
    }

    @Getter
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    @AllArgsConstructor
    @Builder
    public static class SearchResponse{
        private Long managerId;
        private String cultureName;
        private Long partId;
        private String partName;
        private String partContent;
        private LocalDate partDate;
        private int partTotal;
        private int currentPartTotal;
        private Parts.Status status;
        private Role role;

    }

    @Getter
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    @AllArgsConstructor
    public static class closeResponse{
        private Parts.Status status;

        public closeResponse(Parts parts) {
            this.status = parts.getStatus();
        }

    }

    @Getter
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    @AllArgsConstructor
    @Builder
    public static class memberInfo{
        private Long memberId;
        private String memberName;
        private String memberImgUrl;
        private boolean isManager;

    }


}

