package com.tickettogether.domain.chat.controller;

import com.tickettogether.domain.chat.domain.ChatRoom;
import com.tickettogether.domain.chat.dto.ChatDto;
import com.tickettogether.domain.chat.dto.ChatResponseMessage;
import com.tickettogether.domain.chat.repository.ChatRoomRepository;
import com.tickettogether.domain.chat.service.ChatRoomServiceImpl;
import com.tickettogether.global.config.redis.util.RedisUtil;
import com.tickettogether.global.error.dto.BaseResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/chat")
@RequiredArgsConstructor
public class ChatRoomController {

    private final ChatRoomServiceImpl chatRoomService;
    private final ChatRoomRepository chatRoomRepository;

    private final RedisUtil<String, String> redisUtil;

    @PostMapping()
    public ResponseEntity<BaseResponse<ChatDto.ChatEnterResponse>> enterChatRoom(@RequestBody ChatDto.ChatEnterRequest request){
        return ResponseEntity.ok(BaseResponse.create(ChatResponseMessage.ENTER_CHATROOM_SUCCESS.getMessage(), chatRoomService.createChatRoom(request)));
    }

    @GetMapping("/{roomId}/{username}")
    public ResponseEntity<BaseResponse<ChatDto.ChatSearchResponse>> getChatRoom(@PathVariable("roomId") Long roomId,
                                                                                @PathVariable("username") String username, @PageableDefault(size = 10) Pageable pageable) {
        return ResponseEntity.ok(BaseResponse.create(ChatResponseMessage.GET_CHATROOM_SUCCESS.getMessage(), chatRoomService.getChatListByRoomId(roomId, username, pageable)));
    }

    @DeleteMapping("/{roomId}/{username}")
    public ResponseEntity<BaseResponse<String>> getOutChatRoom(@PathVariable("roomId") Long roomId, @PathVariable("username") String username){
        redisUtil.deleteHashValue(roomId.toString(), username);
        return ResponseEntity.ok(BaseResponse.create(ChatResponseMessage.QUIT_CHATROOM_SUCCESS.getMessage()));
    }

    @GetMapping("/{roomId}/test/{username}")    // STOMP 테스트용
    public String getChatRoom(@PathVariable("roomId") Long roomId, @PathVariable("username") String name, Model model){
        ChatRoom chatRoom = chatRoomRepository.getById(roomId);
        model.addAttribute("room", chatRoom);
        model.addAttribute("user", name);
        return "Room";
    }
}
