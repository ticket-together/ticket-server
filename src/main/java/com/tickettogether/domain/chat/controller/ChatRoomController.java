package com.tickettogether.domain.chat.controller;

import com.tickettogether.domain.chat.domain.ChatRoom;
import com.tickettogether.domain.chat.dto.ChatResponseMessage;
import com.tickettogether.domain.chat.repository.ChatRoomRepository;
import com.tickettogether.domain.chat.service.ChatRoomServiceImpl;
import com.tickettogether.global.config.security.annotation.LoginUser;
import com.tickettogether.global.error.dto.BaseResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import static com.tickettogether.domain.chat.dto.ChatDto.*;

@Controller
@RequestMapping("/api/v1/chat")
@RequiredArgsConstructor
public class ChatRoomController {

    private final ChatRoomServiceImpl chatRoomService;
    private final ChatRoomRepository chatRoomRepository;

    @PostMapping()
    public ResponseEntity<BaseResponse<ChatEnterResponse>> enterChatRoom(@RequestBody ChatEnterRequest request,
                                                                         @LoginUser Long memberId){
        return ResponseEntity.ok(BaseResponse.create(ChatResponseMessage.ENTER_CHATROOM_SUCCESS.getMessage(), chatRoomService.createChatRoom(request, memberId)));
    }

    @GetMapping("/{roomId}")
    public ResponseEntity<BaseResponse<ChatSearchResponse>> getChatRoom(@PathVariable("roomId") Long roomId,
                                                                        @PageableDefault(size = 10) Pageable pageable,
                                                                        @LoginUser Long memberId ) {
        return ResponseEntity.ok(BaseResponse.create(ChatResponseMessage.GET_CHATROOM_SUCCESS.getMessage(), chatRoomService.getChatListByRoomId(roomId, memberId, pageable)));
    }

    @DeleteMapping("/{roomId}")
    public ResponseEntity<BaseResponse<String>> getOutChatRoom(@PathVariable("roomId") Long roomId,
                                                               @LoginUser Long memberId){
        chatRoomService.leaveChatRoom(roomId, memberId);
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
