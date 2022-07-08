package com.tickettogether.domain.member.domain;

import lombok.*;

import javax.persistence.*;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
public class MemberKeyword {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "member_keyword_id")
    private Long id;

    @ManyToOne
    @JoinColumn(name="member_id")
    private Member member;

    @Enumerated(EnumType.STRING)
    private Keyword keyword;
}
