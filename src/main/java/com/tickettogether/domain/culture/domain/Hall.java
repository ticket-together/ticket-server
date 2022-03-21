package com.tickettogether.domain.culture.domain;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
@Getter
@NoArgsConstructor(access= AccessLevel.PROTECTED)
public class Hall {
    @Id
    @GeneratedValue
    @Column(name="hall_id")
    private Long id;

    private String location;
}
