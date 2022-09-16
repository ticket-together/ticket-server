package com.tickettogether.domain.parts.dto;

import com.tickettogether.domain.member.domain.Role;
import com.tickettogether.domain.parts.domain.MemberParts;
import com.tickettogether.domain.parts.domain.Parts;
import com.tickettogether.domain.parts.domain.Status;
import lombok.*;
import java.time.LocalDate;
import java.time.LocalDateTime;

public class PartsDto {

    @Getter
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    @AllArgsConstructor
    public static class CreateRequest {
        private String partName;
        private String partContent;
        private LocalDate partDate;
        private Integer partTotal;
    }

    @Getter
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    @AllArgsConstructor
    public static class CreateResponse {
        private Long managerId;
        private String cultureName;
        private String cultureImgUrl;
        private String partName;
        private String partContent;
        private Integer partTotal;
        private LocalDate partDate;
        private LocalDateTime createdAt;
        private Status status;

        public CreateResponse(MemberParts memberParts) {
            this.managerId = memberParts.getParts().getManager().getId();
            this.cultureName = memberParts.getParts().getCulture().getName();
            this.cultureImgUrl = memberParts.getParts().getCulture().getImgUrl();
            this.partName = memberParts.getParts().getPartName();
            this.partContent = memberParts.getParts().getPartContent();
            this.partTotal = memberParts.getParts().getPartTotal();
            this.partDate = memberParts.getParts().getPartDate();
            this.status = memberParts.getParts().getStatus();
            this.createdAt = memberParts.getParts().getCreatedAt();
        }
    }

    @Getter
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    @AllArgsConstructor
    @Builder
    public static class SearchResponse {
        private Long managerId;
        private String cultureName;
        private Long partId;
        private String partName;
        private String partContent;
        private LocalDate partDate;
        private LocalDateTime createdAt;
        private int partTotal;
        private int currentPartTotal;
        private Status status;
        private Role role;

        public SearchResponse(Parts parts) {
            this.managerId = parts.getManager().getId();
            this.cultureName = parts.getCulture().getName();
            this.partId = parts.getId();
            this.partName = parts.getPartName();
            this.partContent = parts.getPartContent();
            this.partDate = parts.getPartDate();
            this.partTotal = parts.getPartTotal();
            this.currentPartTotal = parts.getCurrentPartTotal();
            this.status = parts.getStatus();
            this.createdAt = parts.getCreatedAt();
        }

        public void updateRole(Role role) {
            this.role = role;
        }
    }

    @Getter
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    @AllArgsConstructor
    public static class closeResponse {
        private Status status;

        public closeResponse(Parts parts) {
            this.status = parts.getStatus();
        }

    }

    @Getter
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    @AllArgsConstructor
    @Builder
    public static class memberInfo {
        private Long memberId;
        private String memberName;
        private String memberImgUrl;
        private boolean isManager;
    }
}

