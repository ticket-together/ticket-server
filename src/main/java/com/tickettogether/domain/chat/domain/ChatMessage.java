package com.tickettogether.domain.chat.domain;

import lombok.*;
import org.springframework.data.annotation.CreatedDate;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class ChatMessage {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "message_id")
    private Long id;

    @JoinColumn(name = "room_id")
    @ManyToOne(fetch = FetchType.LAZY)
    private ChatRoom chatRoom;

    @Enumerated(value = EnumType.STRING)
    private MessageType type;

    private String sender;

    private Object data;

    @CreatedDate
    @Column(columnDefinition = "false")
    private LocalDateTime createdAt;

    public enum MessageType{
        CHAT,
        JOIN,
        LEAVE
    }

    @Builder
    public ChatMessage(ChatRoom chatRoom, MessageType type, String sender, Object data){
        this.chatRoom = chatRoom;
        this.type = type;
        this.sender = sender;
        this.data = data;
    }
}
