package com.tickettogether.domain.chat.service;

import com.tickettogether.domain.chat.domain.ChatRoom;
import com.tickettogether.domain.chat.dto.ChatDto;
import com.tickettogether.domain.chat.exception.ChatRoomEmptyException;
import com.tickettogether.domain.chat.repository.ChatMessageRepository;
import com.tickettogether.domain.chat.repository.ChatRoomRepository;
import com.tickettogether.domain.parts.domain.Parts;
import com.tickettogether.domain.parts.exception.PartsEmptyException;
import com.tickettogether.domain.parts.repository.PartsRepository;
import com.tickettogether.global.config.redis.util.RedisUtil;
import com.tickettogether.global.dto.PageDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;


@Service
@RequiredArgsConstructor
@Slf4j
public class ChatRoomServiceImpl implements ChatRoomService {
    private final ChatRoomRepository chatRoomRepository;

    private final ChatMessageRepository chatMessageRepository;

    private final PartsRepository partsRepository;

    private final RedisUtil<String, String> redisUtil;

    public ChatDto.ChatEnterResponse createChatRoom(ChatDto.ChatEnterRequest request) {
        Parts parts = partsRepository.findById(request.getPartsId()).orElseThrow(PartsEmptyException::new);
        ChatRoom chatRoom = chatRoomRepository.save(
                ChatRoom.builder()
                        .name(request.getRoomName())
                        .parts(parts).build()
        );
        return ChatDto.ChatEnterResponse.builder().roomId(chatRoom.getId()).build();
    }

    public ChatDto.ChatSearchResponse getChatListByRoomId(Long roomId, String username, Pageable pageable) {
        ChatRoom chatRoom = chatRoomRepository.findById(roomId).orElseThrow(ChatRoomEmptyException::new);
        Map<String, String> enterMemberList = redisUtil.getHashKeys(roomId.toString());

        if (enterMemberList.containsKey(username)) {    // 재접속인 경우 입장 시간 이후의 데이터 반환
            LocalDateTime enterTime = LocalDateTime.parse(redisUtil.getHashValue(roomId.toString(), username));

            Page<ChatDto.ChatMessageResponse> messageDtoList = chatMessageRepository.findAllByChatRoomAndCreatedAtGreaterThanEqualOrderByCreatedAt(pageable, chatRoom, enterTime)
                    .map(ChatDto.ChatMessageResponse::create);
            return ChatDto.ChatSearchResponse.create(roomId, chatRoom.getName(), PageDto.create(messageDtoList, messageDtoList.getContent()));
        }
        return ChatDto.ChatSearchResponse.create(roomId, chatRoom.getName(),PageDto.create(new PageImpl<>(List.of()), List.of()));      // 처음 입장인 경우 빈 배열 반환
    }

    public void saveChatMessage(ChatDto.ChatMessageResponse chatMessage){
        ChatRoom chatRoom = chatRoomRepository.findById(chatMessage.getRoomId()).orElseThrow(ChatRoomEmptyException::new);
        chatMessageRepository.save(chatMessage.toEntity(chatRoom));
    }
}
