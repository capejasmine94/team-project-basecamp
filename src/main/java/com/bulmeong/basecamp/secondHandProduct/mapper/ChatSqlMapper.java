package com.bulmeong.basecamp.secondHandProduct.mapper;

import com.bulmeong.basecamp.secondHandProduct.dto.ChatMessageDto;
import com.bulmeong.basecamp.secondHandProduct.dto.ChatRoomDto;
import com.bulmeong.basecamp.secondHandProduct.dto.SaleChatRoomDto;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface ChatSqlMapper {

    //채팅방생성(방 없을시)
    public void insertChatRoom(ChatRoomDto chatRoomDto);
    //채팅방ID(방 있을시)
    public Integer findByChatId(ChatRoomDto chatRoomDto);
    //채팅방 대화 등록
    public void insertMessageList(ChatMessageDto chatMessageDto);
    //채팅방 마지막 메세지 업뎃
    public void updateChatRoomLastMessage(ChatMessageDto chatMessageDto);
    //채팅방목록(전체)
    public List<SaleChatRoomDto> selectAllChatRoomList(int user_id);
    //채팅방목록(구매)
    public List<SaleChatRoomDto> selectBuyChatRoomList(int user_id);
    //채팅방목록(판매)
    public List<SaleChatRoomDto> selectSaleChatRoomList(int user_id);
    //메시지 채팅방 목록(게시글 판매목록)
    public List<SaleChatRoomDto> selectByProductChatRoomList(int product_id);
    // 채팅방 메세지 내용
    public List<ChatMessageDto> selectChatRoomMessage(int chat_room_id);
    // 채팅방 개수 업데이트 플러스
    public void updateChatRoomCountP(int product_id);
    // 채팅방 개수 업데이트 마이너스
    public void updateChatRoomCountM(int product_id);
    // 채팅방 생성후 메세지 확인
    public String selectFindChatRoomByNullMessage(int product_id);
    // 채팅방 삭제
    public void deleteChatRoom(int chat_room_id);




    // 채팅방아이디 확인
    public int selectCountRoomById(int chat_room_id);

}
