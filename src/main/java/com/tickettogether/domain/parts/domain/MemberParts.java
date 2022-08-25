package com.tickettogether.domain.parts.domain;

import com.tickettogether.domain.member.domain.Member;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class MemberParts {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="member_parts_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="member_id")
    private Member member;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="manager_id")
    private Member manager;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name="parts_id")
    private Parts parts;

    @Builder
    public MemberParts(Member manager, Member member, Parts parts){
        this.manager = manager;
        this.member = member;
        this.parts = parts;
    }
}
