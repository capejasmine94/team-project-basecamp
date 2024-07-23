package com.bulmeong.basecamp.secondHandProduct.mapper;

import com.bulmeong.basecamp.secondHandProduct.dto.ChatRoomDto;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface ChatSqlMapper {

    public void insertChatRoomMessage(ChatRoomDto chatRoomDto);
}
