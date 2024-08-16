package com.bulmeong.basecamp.secondHandProduct.controller;


import com.bulmeong.basecamp.common.dto.RestResponseDto;
import com.bulmeong.basecamp.secondHandProduct.dto.ChatMessageDto;
import com.bulmeong.basecamp.secondHandProduct.dto.ChatRoomDto;
import com.bulmeong.basecamp.secondHandProduct.dto.SaleChatRoomDto;
import com.bulmeong.basecamp.secondHandProduct.service.ChatService;
import com.bulmeong.basecamp.user.dto.UserDto;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@RestController
@RequestMapping("api/chatPolling")
public class RestChatController {

    @Autowired
    private ChatService chatService;

    @RequestMapping("getOrCreateRoom")
    public RestResponseDto getOrCreateRoom(@RequestBody ChatRoomDto chatRoomDto) {

        RestResponseDto restResponseDto = new RestResponseDto();
        restResponseDto.setResult("success");

        int chatRoomId = chatService.getOrCreateChatRoom(chatRoomDto);

        restResponseDto.add("chatRoomId", chatRoomId);
        return restResponseDto;
    }

    @RequestMapping("send")
    public RestResponseDto send(@RequestBody ChatMessageDto chatMessageDto) {

        RestResponseDto restResponseDto = new RestResponseDto();
        restResponseDto.setResult("success");

        chatService.insertMessageList(chatMessageDto);

        return restResponseDto;
    }

    @RequestMapping("getMessages")
    public RestResponseDto getMessages(@RequestParam("chat_room_id") int chat_room_id) {
        RestResponseDto restResponseDto = new RestResponseDto();
        restResponseDto.setResult("success");

        List<ChatMessageDto> message = chatService.selectChatRoomMessage(chat_room_id);
        restResponseDto.add("message", message);

        return restResponseDto;
    }

    @RequestMapping("findByNullMessage")
    public RestResponseDto findByNullMessage(@RequestParam("chat_room_id") int chat_room_id,
                                             @RequestParam("product_id") int product_id) {
        RestResponseDto restResponseDto = new RestResponseDto();
        restResponseDto.setResult("success");

        chatService.selectFindChatRoomByNullMessage(chat_room_id,product_id);

        return restResponseDto;
    }

}
