package com.tickettogether.domain.chat.service;

import com.tickettogether.domain.chat.domain.ChatRoom;
import com.tickettogether.domain.chat.dto.ChatDto;
import com.tickettogether.domain.chat.exception.ChatRoomEmptyException;
import com.tickettogether.domain.chat.repository.ChatMessageRepository;
import com.tickettogether.domain.chat.repository.ChatRoomRepository;
import com.tickettogether.domain.parts.domain.Parts;
import com.tickettogether.domain.parts.exception.PartsEmptyException;
import com.tickettogether.domain.parts.repository.PartsRepository;
import com.tickettogether.global.dto.PageDto;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class ChatRoomServiceImpl implements ChatRoomService {
    private final ChatRoomRepository chatRoomRepository;

    private final ChatMessageRepository chatMessageRepository;

    private final PartsRepository partsRepository;

    public ChatDto.ChatEnterResponse createChatRoom(ChatDto.ChatEnterRequest request){
        Parts parts = partsRepository.findById(request.getPartsId()).orElseThrow(PartsEmptyException::new);
        ChatRoom chatRoom = chatRoomRepository.save(
                ChatRoom.builder()
                        .name(request.getRoomName())
                        .parts(parts).build()
        );
        return ChatDto.ChatEnterResponse.builder().roomId(chatRoom.getId()).build();
    }

    public ChatDto.ChatSearchResponse getChatListByRoomId(Long roomId, Pageable pageable) {
        ChatRoom chatRoom = chatRoomRepository.findById(roomId).orElseThrow(ChatRoomEmptyException::new);
        Page<ChatDto.ChatMessageResponse> messageDtoList = chatMessageRepository.findAllByChatRoomOrderByCreatedAtDesc(pageable, chatRoom)
                .map(ChatDto.ChatMessageResponse::create);

        return ChatDto.ChatSearchResponse.builder()
                .roomId(roomId)
                .roomName(chatRoom.getName())
                .messageList(PageDto.create(messageDtoList, messageDtoList.getContent())).build();
    }

    public void saveChatMessage(ChatDto.ChatMessageResponse chatMessage){
        ChatRoom chatRoom = chatRoomRepository.findById(chatMessage.getRoomId()).orElseThrow(ChatRoomEmptyException::new);
        chatMessageRepository.save(chatMessage.toEntity(chatRoom));
    }
}
