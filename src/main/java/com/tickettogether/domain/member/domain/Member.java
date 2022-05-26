package com.tickettogether.domain.member.domain;

import com.fasterxml.jackson.databind.introspect.MemberKey;
import com.tickettogether.domain.parts.domain.MemberParts;
import com.tickettogether.domain.parts.domain.Parts;
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
    private List<MemberKeyword> keywords = new ArrayList<>();

    @OneToMany(mappedBy = "member", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<SiteInfo> siteInfos = new ArrayList<>();

    @OneToMany(mappedBy = "member", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Review> reviews = new ArrayList<>();
    private String phoneNumber;

    @Column(nullable = false)
    private String email;

    private String password;

    private String name;

    public void saveMemberProfile(String phoneNumber){
        this.phoneNumber = phoneNumber;
    }

    @Lob
    private String imgUrl;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Role role;

    @Enumerated(EnumType.STRING)
    private Status status = Status.ACTIVE;

    @Builder
    public Member(String email, String password, String name, String imgUrl, Role role){
        this.email = email;
        this.password = password;
        this.name = name;
        this.imgUrl = imgUrl;
        this.role = role;
    }

    public void updateOAuthProfile(String name, String imgUrl){  //DTO 대체
        this.name = name;
        this.imgUrl = imgUrl;
    }

    public void updateMemberProfile(String name, String phoneNumber){  //DTO 대체
        this.name = name;
        this.phoneNumber = phoneNumber;
    }

        public enum Status {
            ACTIVE, INACTIVE
        }

    public void setReviews(Review review){
        this.reviews.add(review);
        if (review.getMember() != this){
            review.addMember(this);
        }
    }

    public void setMemberParts(Parts... parts){
        for(Parts part : parts){
            MemberParts data = MemberParts.builder().member(this).parts(part).build();
            this.memberPartsList.add(data);
        }
    }

    public void changeStatus(Status status){
        if(status.equals(Status.ACTIVE)){
            this.status = Status.INACTIVE;
        }else this.status = Status.ACTIVE;
    }
}
