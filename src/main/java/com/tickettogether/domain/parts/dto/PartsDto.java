package com.tickettogether.domain.parts.dto;

import com.tickettogether.domain.culture.domain.Culture;
import com.tickettogether.domain.member.domain.Member;
import com.tickettogether.domain.parts.domain.MemberParts;
import com.tickettogether.domain.parts.domain.Parts;
import lombok.Getter;
import lombok.Setter;
import java.time.LocalDate;

import static com.tickettogether.domain.parts.domain.Parts.Status.ACTIVE;

public class PartsDto {

    // 팟 생성시 request
    // 작성자, 공연, 팟이름, 팟 상세정보, 모임날짜, 인원수
    @Setter
    @Getter
    public static class createRequest {

        private Member member;
        private Culture culture;
        private String partName;
        private String partContent;
        private Integer partTotal;
        private LocalDate partDate;
        private Integer memberCount;
        private Parts.Status status;

        public MemberParts toEntity() {
            return MemberParts.builder()
                    .member(member)
                    .parts(Parts.builder()
                            .culture(culture)
                            .partName(partName)
                            .partContent(partContent)
                            .partTotal(partTotal)
                            .partDate(partDate)
                            .status(ACTIVE)
                            .build()
                    )
                    .memberCount(1)
                    .build();
        }


    }

    // 팟 생성시 response
    // 공연 이름에 해당하는 공연 포스터 + 나머지 입력받은 값 + 인원수 카운트값 (1)
    @Getter
    public static class createResponse {

        private Long memberId;
        private String cultureName;
        private String cultureImgUrl;
        private String partName;
        private String partContent;
        private Integer partTotal;
        private LocalDate partDate;
        private Parts.Status status;
        private Integer memberCount;

        public createResponse(MemberParts memberParts) {
            this.memberId = memberParts.getMember().getId();
            this.cultureName = memberParts.getParts().getCulture().getName();
            this.cultureImgUrl = memberParts.getParts().getCulture().getImgUrl();
            this.partName = memberParts.getParts().getPartName();
            this.partContent = memberParts.getParts().getPartContent();
            this.partTotal = memberParts.getParts().getPartTotal();
            this.partDate = memberParts.getParts().getPartDate();
            this.status = memberParts.getParts().getStatus();
            this.memberCount = memberParts.getMemberCount();
        }

    }
}
