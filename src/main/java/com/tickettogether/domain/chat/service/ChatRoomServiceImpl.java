package com.tickettogether.domain.chat.service;

import com.tickettogether.domain.chat.domain.ChatMessage;
import com.tickettogether.domain.chat.domain.ChatMessage.MessageType;
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

import java.time.format.DateTimeFormatter;


@Service
@RequiredArgsConstructor
public class ChatRoomServiceImpl implements ChatRoomService {
    private final ChatRoomRepository chatRoomRepository;

    private final ChatMessageRepository chatMessageRepository;

    private final PartsRepository partsRepository;
    private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd hh:mm:ss");

    public ChatDto.ChatEnterResponse createChatRoom(ChatDto.ChatEnterRequest request){
        Parts parts = partsRepository.findById(request.getPartsId()).orElseThrow(PartsEmptyException::new);
        ChatRoom chatRoom = chatRoomRepository.save(
                ChatRoom.builder()
                        .name(request.getRoomName())
                        .parts(parts).build()
        );
        return ChatDto.ChatEnterResponse.builder().roomId(chatRoom.getId()).build();
    }

    public ChatDto.ChatMessageResponse createChatMessageOrSave(ChatDto.ChatStompRequest request, Long roomId){
        if(MessageType.JOIN.name().equals(request.getType())){
            return chatMessageDtoBuilder(request.getSender() + "님이 채팅방에 입장하셨습니다.", request.getSender(), request.getType(),null);
        }

        ChatMessage chatMessage = chatMessageRepository.save(request.toEntity(
                        chatRoomRepository.findById(roomId).orElseThrow(ChatRoomEmptyException::new)));
        return chatMessageDtoBuilder(chatMessage.getData(), chatMessage.getSender(), chatMessage.getType().name(), chatMessage.getCreatedAt().format(formatter));
    }

    public ChatDto.ChatSearchResponse getChatListByRoomId(Long roomId, Pageable pageable) {
        ChatRoom chatRoom = chatRoomRepository.findById(roomId).orElseThrow(ChatRoomEmptyException::new);
        Page<ChatDto.ChatMessageResponse> messageDtoList = chatMessageRepository.findAllByChatRoomOrderByCreatedAtDesc(pageable, chatRoom)
                .map(x -> chatMessageDtoBuilder(x.getData(), x.getSender(), x.getType().name(), x.getCreatedAt().format(formatter)));

        return ChatDto.ChatSearchResponse.builder()
                .roomId(roomId)
                .roomName(chatRoom.getName())
                .messageList(PageDto.create(messageDtoList, messageDtoList.getContent())).build();
    }

    private ChatDto.ChatMessageResponse chatMessageDtoBuilder(String data, String sender, String createdAt, String messageType){
        return ChatDto.ChatMessageResponse.builder()
                .sender(sender)
                .data(data)
                .type(messageType)
                .createdAt(createdAt).build();
    }
}
