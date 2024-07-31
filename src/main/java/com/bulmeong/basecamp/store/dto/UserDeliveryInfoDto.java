package com.bulmeong.basecamp.store.dto;

import lombok.Data;

@Data
public class UserDeliveryInfoDto {
    private int id;
    private int user_id;
    private String receiver_name;
    private String phone;
    private String delivery_address;
    private int is_default_address;
}
