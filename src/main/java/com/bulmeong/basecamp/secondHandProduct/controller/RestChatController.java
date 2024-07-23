package com.bulmeong.basecamp.secondHandProduct.controller;


import com.bulmeong.basecamp.common.dto.RestResponseDto;
import com.bulmeong.basecamp.secondHandProduct.dto.ChatRoomDto;
import com.bulmeong.basecamp.secondHandProduct.service.ChatService;
import com.bulmeong.basecamp.user.dto.UserDto;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/chatPolling")
public class RestChatController {

    @Autowired
    private ChatService chatService;

    @RequestMapping("send")
    public RestResponseDto send(HttpSession session,
                                @RequestBody ChatRoomDto chatRoomDto) {

        RestResponseDto restResponseDto = new RestResponseDto();
        restResponseDto.setResult("success");

        UserDto userDto = (UserDto) session.getAttribute("sessionUserInfo");

        chatRoomDto.setBuyer_user_id(userDto.getId());
        chatService.insertChatRoomMessage(chatRoomDto);

        return restResponseDto;
    }

}
