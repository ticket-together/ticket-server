package com.tickettogether.domain.chat.domain;

import com.tickettogether.domain.parts.domain.Parts;
import lombok.*;
import org.hibernate.mapping.Join;

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

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "parts_id")
    private Parts parts;

    private String name;

    @Builder
    public ChatRoom(String name, Parts parts){
        this.name = name;
        this.parts = parts;
    }
}
