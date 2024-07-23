package com.bulmeong.basecamp.secondHandProduct.dto;

import lombok.Data;

import java.util.Date;

@Data
public class ChatRoomDto {
    private int chat_room_id;
    private int product_id;
    private int buyer_user_id;
    private String message;
    private Date created_at;
}
