package com.tickettogether.domain.chat.service;

import com.tickettogether.domain.chat.dto.ChatDto;
import com.tickettogether.domain.chat.dto.ChatDto.ChatEnterResponse;

public interface ChatRoomService {

    ChatEnterResponse createChatRoom(ChatDto.ChatEnterRequest request);
    ChatDto.ChatMessageResponse createChatMessageOrSave(ChatDto.ChatStompRequest request);
}
