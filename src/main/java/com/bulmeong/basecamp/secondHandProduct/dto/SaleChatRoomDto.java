package com.bulmeong.basecamp.secondHandProduct.dto;

import lombok.Data;

import java.util.Date;

@Data
public class SaleChatRoomDto {
    private int chat_room_id;
    private int product_id;
    private int buyer_user_id;
    private int seller_user_id;
    private String message;
    private Date message_time;
    private int user_id;
    private String seller_nickname;
    private String buyer_nickname;
    private String main_image;
}
