package com.bulmeong.basecamp.secondHandProduct.service;

import com.bulmeong.basecamp.secondHandProduct.dto.ChatRoomDto;
import com.bulmeong.basecamp.secondHandProduct.mapper.ChatSqlMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ChatService {

    @Autowired
    private ChatSqlMapper chatSqlMapper;

    public void insertChatRoomMessage(ChatRoomDto chatRoomDto) {

        chatSqlMapper.insertChatRoomMessage(chatRoomDto);
    }
}
