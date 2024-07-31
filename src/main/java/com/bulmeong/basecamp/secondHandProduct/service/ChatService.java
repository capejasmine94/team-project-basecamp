package com.bulmeong.basecamp.secondHandProduct.service;

import com.bulmeong.basecamp.secondHandProduct.dto.ChatMessageDto;
import com.bulmeong.basecamp.secondHandProduct.dto.ChatRoomDto;
import com.bulmeong.basecamp.secondHandProduct.dto.SaleChatRoomDto;
import com.bulmeong.basecamp.secondHandProduct.mapper.ChatSqlMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ChatService {

    @Autowired
    private ChatSqlMapper chatSqlMapper;

    public int getOrCreateChatRoom(ChatRoomDto chatRoomDto) {

        Integer chatRoomId = chatSqlMapper.findByChatId(chatRoomDto);

        if (chatRoomId == null || chatRoomId == 0) {
            chatSqlMapper.insertChatRoom(chatRoomDto);
            chatRoomId = chatRoomDto.getChat_room_id();

            chatSqlMapper.updateChatRoomCountP(chatRoomDto.getProduct_id());
            System.out.println("업데이트완료");
        }

        return chatRoomId;
    }


    public void insertMessageList(ChatMessageDto chatMessageDto) {
        chatSqlMapper.insertMessageList(chatMessageDto);
        chatSqlMapper.updateChatRoomLastMessage(chatMessageDto);
    }

    public List<SaleChatRoomDto> selectAllChatRoomList(int user_id) {
        return chatSqlMapper.selectAllChatRoomList(user_id);
    }
    public List<SaleChatRoomDto> selectBuyChatRoomList(int user_id) {
        return chatSqlMapper.selectBuyChatRoomList(user_id);
    }
    public List<SaleChatRoomDto> selectSaleChatRoomList(int user_id) {
        return chatSqlMapper.selectSaleChatRoomList(user_id);
    }
    public List<SaleChatRoomDto> selectByProductChatRoomList(int product_id) {
        return chatSqlMapper.selectByProductChatRoomList(product_id);
    }

    public List<ChatMessageDto> selectChatRoomMessage(int chat_room_id) {
        return chatSqlMapper.selectChatRoomMessage(chat_room_id);
    }

    public void selectFindChatRoomByNullMessage(int chat_room_id, int product_id) {

        String lastMessage = chatSqlMapper.selectFindChatRoomByNullMessage(chat_room_id);
        System.out.println("lastMessage: " + lastMessage);
        if (lastMessage == null) {
            chatSqlMapper.deleteChatRoom(chat_room_id);
            chatSqlMapper.updateChatRoomCountM(product_id);
            System.out.println("업데이트삭제");
        } else {
            System.out.println("대화가 존재합니다.");
        }
    }

}
