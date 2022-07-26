package com.tickettogether.domain.chat.controller;

import com.tickettogether.domain.chat.dto.ChatDto;
import com.tickettogether.domain.chat.dto.ChatResponseMessage;
import com.tickettogether.domain.chat.service.ChatRoomService;
import com.tickettogether.global.error.dto.BaseResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/chat")
@RequiredArgsConstructor
public class ChatRoomController {

    private final ChatRoomService chatRoomService;

    @PostMapping()
    public ResponseEntity<BaseResponse<ChatDto.ChatEnterResponse>> enterChatRoom(@RequestBody ChatDto.ChatEnterRequest request){
        return ResponseEntity.ok(BaseResponse.create(ChatResponseMessage.ENTER_CHATROOM_SUCCESS.getMessage(), chatRoomService.createChatRoom(request)));
    }

    @GetMapping("/{roomId}")
    public ResponseEntity<BaseResponse<ChatDto.ChatSearchResponse>> getChatRoom(@PathVariable("roomId") String roomId){
        return ResponseEntity.ok(BaseResponse.create(ChatResponseMessage.GET_CHATROOM_SUCCESS.getMessage(), chatRoomService.getChatListByRoomId(roomId)));
    }

}
