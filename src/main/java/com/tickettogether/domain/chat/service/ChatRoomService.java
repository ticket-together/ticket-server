package com.tickettogether.domain.chat.service;

import com.tickettogether.domain.chat.dto.ChatDto;
import com.tickettogether.domain.chat.dto.ChatDto.ChatEnterResponse;
import org.springframework.data.domain.Pageable;

public interface ChatRoomService {

    ChatEnterResponse createChatRoom(ChatDto.ChatEnterRequest request);
    ChatDto.ChatMessageResponse createChatMessageOrSave(ChatDto.ChatStompRequest request);
    ChatDto.ChatSearchResponse getChatListByRoomId(Long roomId, Pageable pageable);
}
