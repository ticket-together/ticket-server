package com.tickettogether.domain.chat.repository;

import com.tickettogether.domain.chat.domain.ChatMessage;
import com.tickettogether.domain.chat.domain.ChatRoom;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ChatMessageRepository extends JpaRepository<ChatMessage, Long> {
    Page<ChatMessage> findAllByChatRoomOrderByCreatedAtDesc(Pageable pageable, ChatRoom chatRoom);
}
