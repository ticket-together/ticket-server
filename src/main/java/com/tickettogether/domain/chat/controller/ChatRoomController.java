package com.tickettogether.domain.chat.controller;

import com.tickettogether.domain.chat.domain.ChatRoom;
import com.tickettogether.domain.chat.dto.ChatDto;
import com.tickettogether.domain.chat.dto.ChatResponseMessage;
import com.tickettogether.domain.chat.repository.ChatRoomRepository;
import com.tickettogether.domain.chat.service.ChatRoomServiceImpl;
import com.tickettogether.global.error.dto.BaseResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/api/v1/chat")
@RequiredArgsConstructor
public class ChatRoomController {

    private final ChatRoomServiceImpl chatRoomService;
    private final ChatRoomRepository chatRoomRepository;

    @PostMapping()
    public ResponseEntity<BaseResponse<ChatDto.ChatEnterResponse>> enterChatRoom(@RequestBody ChatDto.ChatEnterRequest request){
        return ResponseEntity.ok(BaseResponse.create(ChatResponseMessage.ENTER_CHATROOM_SUCCESS.getMessage(), chatRoomService.createChatRoom(request)));
    }

//    @GetMapping("/{roomId}")
//    public String getChatRoom(@PathVariable("roomId") Long roomId, Model model){
//        ChatRoom chatRoom = chatRoomRepository.getById(roomId);
//        model.addAttribute("room", chatRoom);
//        return "Room";
//    }
}
