package com.tickettogether.domain.member.domain;

import com.tickettogether.domain.parts.domain.MemberParts;
import com.tickettogether.domain.parts.domain.Parts;
import com.tickettogether.domain.reservation.domain.TicketSiteInfo;
import com.tickettogether.domain.review.domain.Review;
import com.tickettogether.global.entity.BaseEntity;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor(access= AccessLevel.PROTECTED)
public class Member extends BaseEntity {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="member_id")
    private Long id;

    @OneToMany(mappedBy = "member", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<MemberParts> memberPartsList = new ArrayList<>();

    @OneToMany(mappedBy = "member", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<TicketSiteInfo> siteInfos = new ArrayList<>();

    @OneToMany(mappedBy = "member", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Review> reviews = new ArrayList<>();

    @OneToMany(mappedBy = "member", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<MemberKeyword> memberKeywords = new ArrayList<>();

    private String phoneNumber;

    @Column(nullable = false)
    private String email;

    private String password;

    private String name;

    public void saveMemberProfile(String name){
        this.name = name;
    }

    @Lob
    private String imgUrl;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Role role;

    @Enumerated(EnumType.STRING)
    private Status status = Status.ACTIVE;

    @Builder
    public Member(String email, String password, String name, String imgUrl, String phoneNumber, Role role){
        this.email = email;
        this.password = password;
        this.name = name;
        this.imgUrl = imgUrl;
        this.phoneNumber = phoneNumber;
        this.role = role;
    }

    public void updateOAuthProfile(String imgUrl, String phoneNumber){  //DTO 대체
        this.imgUrl = imgUrl;
        this.phoneNumber = phoneNumber;
    }

    public void updateMemberProfile(String name, String phoneNumber){  //DTO 대체
        this.name = name;
        this.phoneNumber = phoneNumber;
    }

    public enum Status {
        ACTIVE, INACTIVE
    }

    public void setMemberKeywords(MemberKeyword keyword){
        this.memberKeywords.add(keyword);
        if(keyword.getMember() != this){
            keyword.setMember(this);
        }
    }

    public void changeStatus(Status status){
        if(status.equals(Status.ACTIVE)){
            this.status = Status.INACTIVE;
        }else this.status = Status.ACTIVE;
    }
}