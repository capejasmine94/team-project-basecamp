package com.bulmeong.basecamp.secondHandProduct.secondHandProductDto.secondHandProduct;

import lombok.Data;

import java.util.Date;

@Data
public class ChatMessageDto {
    private int chat_message_id;
    private int chat_user_id;
    private int chat_room_id;
    private String message;
    private Date created_at;
}
