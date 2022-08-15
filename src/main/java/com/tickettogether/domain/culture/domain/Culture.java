package com.tickettogether.domain.culture.domain;

import com.tickettogether.domain.parts.domain.Parts;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor(access= AccessLevel.PROTECTED)
public class Culture {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="culture_id")
    private Long id;

    private Long prodId;

    private LocalDateTime startDate;

    private LocalDateTime endDate;

    private String hallName;

    private String name;

    private String imgUrl;

    @Enumerated(EnumType.STRING)
    private CultureKeyword keyword;

    @OneToMany(mappedBy = "culture", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Parts> partsList = new ArrayList<>();
}
