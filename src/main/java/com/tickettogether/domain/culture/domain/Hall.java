package com.tickettogether.domain.culture.domain;

import com.tickettogether.domain.member.domain.Role;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor(access= AccessLevel.PROTECTED)
public class Hall {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="hall_id")
    private Long id;

    private String location;  // 공연장 이름

    public Hall(String location){
        this.location = location;
    }
}