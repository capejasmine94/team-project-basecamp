package com.bulmeong.basecamp.secondHandProduct.dto;

import lombok.Data;

import java.util.Date;

@Data
public class ChatMessageDto {
    private int chat_message_id;
    private int chat_room_id;
    private int sender_id;
    private String message;
    private String message_image;
    private Date message_time;
}


