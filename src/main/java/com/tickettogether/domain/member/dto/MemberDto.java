package com.tickettogether.domain.member.dto;

import lombok.*;

import java.util.List;

@Getter
@AllArgsConstructor
public class MemberDto {

    @Getter
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    @AllArgsConstructor
    public static class SaveRequest{
        private String nickname;
        private List<String> keywords;
    }

    @Getter
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    @AllArgsConstructor
    public static class SaveResponse{
        private Long memberId;
    }

    @Getter
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    @AllArgsConstructor
    public static class UpdateRequest{
        private String username;
        private String phoneNumber;
    }

    @Getter
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    @AllArgsConstructor
    public static class UpdateResponse{
        private Long userId;
    }

    @Getter
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    @AllArgsConstructor
    @Builder
    public static class SearchResponse{
        private Long userId;
        private String username;
        private String email;
        private String imgUrl;
        private String phoneNumber;
        private List<String> keywords;
    }
}
