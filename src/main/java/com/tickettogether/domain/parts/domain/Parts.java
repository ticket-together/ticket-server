package com.tickettogether.domain.parts.domain;

import com.tickettogether.domain.culture.domain.Culture;
import com.tickettogether.global.entity.BaseEntity;
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
public class Parts extends BaseEntity {

    public enum Status {
        ACTIVE, CLOSED
    }

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="parts_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="culture_id")
    private Culture culture;

    @OneToMany(mappedBy = "parts", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<MemberParts> memberParts = new ArrayList<>();

    @Enumerated(EnumType.STRING)
    private Status status;

    private String partName;

    private String partContent;

    private Integer partTotal;

    private LocalDateTime partDate;

    private LocalDateTime partDeadLine;

    @Builder
    public Parts(String partName, String partContent, Integer partTotal, LocalDateTime partDate, LocalDateTime partDeadLine){
        this.partName = partName;
        this.partContent = partContent;
        this.partTotal = partTotal;
        this.partDate = partDate;
        this.partDeadLine = partDeadLine;
    }

    public Parts changePartStatus(){    //팟 마감
        this.status = Status.CLOSED;
        return this;
    }
}
