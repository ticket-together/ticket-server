package com.tickettogether.domain.culture.domain;

import com.tickettogether.domain.parts.domain.Parts;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
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

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="category_id")
    private Category category;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="hall_id")
    private Hall hall;

    @OneToMany(mappedBy = "culture", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Parts> partsList = new ArrayList<>();

    private LocalDateTime cultureDate;
}
