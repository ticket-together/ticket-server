package com.tickettogether.domain.parts.domain;

import com.tickettogether.domain.culture.domain.Culture;
import com.tickettogether.domain.member.domain.Member;
import com.tickettogether.domain.parts.dto.PartsDto;
import com.tickettogether.global.entity.BaseEntity;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import javax.persistence.*;
import java.time.LocalDate;
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
    private final List<MemberParts> memberParts = new ArrayList<>();

    @Enumerated(EnumType.STRING)
    private Status status;

    private String partName;

    private String partContent;

    private Integer partTotal;

    private Integer currentPartTotal;

    private LocalDate partDate;

    @OneToOne
    @JoinColumn(name = "member_id")
    private Member manager;

    @Builder
    public Parts(Culture culture, Integer currentPartTotal, Status status, Member manager, PartsDto.CreateRequest request){
        this.culture = culture;
        this.partName = request.getPartName();
        this.partContent = request.getPartContent();
        this.partTotal = request.getPartTotal();
        this.currentPartTotal = currentPartTotal;
        this.partDate = request.getPartDate();
        this.status = status;
        this.manager = manager;
    }

    public Parts changePartStatus(){    //팟 마감
        this.status = Status.CLOSED;
        return this;
    }

    public Parts addMember() {
        this.currentPartTotal++;
        return this;
    }

    public Parts removeMember() {
        this.currentPartTotal--;
        return this;
    }
}