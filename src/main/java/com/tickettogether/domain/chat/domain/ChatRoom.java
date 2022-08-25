package com.tickettogether.domain.chat.domain;

import com.tickettogether.domain.parts.domain.Parts;
import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Builder
@Getter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ChatRoom {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "room_id")
    private Long id;

    @OneToMany(mappedBy = "chatRoom", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ChatMessage> chatMessageList = new ArrayList<>();

    private Long partsId;

    private String name;

    @Builder
    public ChatRoom(String name, Long partsId){
        this.name = name;
        this.partsId = partsId;
    }
}
