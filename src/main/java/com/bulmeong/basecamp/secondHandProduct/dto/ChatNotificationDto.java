package com.bulmeong.basecamp.secondHandProduct.dto;

import lombok.Data;

@Data
public class ChatNotificationDto {
    private int chat_notification_id;
    private int receiver_user_id;
    private String notification_message;
}
