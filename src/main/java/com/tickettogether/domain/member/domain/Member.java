package com.tickettogether.domain.member.domain;

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
    private List<SiteInfo> siteInfos = new ArrayList<>();

    @OneToMany(mappedBy = "member", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Review> reviews = new ArrayList<>();

    @Column(nullable = false)
    private String email;

    @Column(nullable = false)
    private String password;

    private String name;

    @Lob
    private String imgUrl;

    @Builder
    public Member(String email, String password, String name, String imgUrl){
        this.email = email;
        this.password = password;
        this.name = name;
        this.imgUrl = imgUrl;
    }

    public void updateMemberProfile(String name, String imgUrl){  //DTO 대체
        this.name = name;
        this.imgUrl = imgUrl;
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
}
