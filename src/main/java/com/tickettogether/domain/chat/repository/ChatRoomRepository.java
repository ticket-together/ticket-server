package com.tickettogether.domain.chat.repository;

import com.tickettogether.domain.chat.domain.ChatRoom;
import com.tickettogether.domain.parts.domain.Parts;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ChatRoomRepository extends JpaRepository<ChatRoom, Long> {
    ChatRoom findByNameAndParts(String name, Parts parts);
}
