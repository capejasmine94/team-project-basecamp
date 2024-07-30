package com.bulmeong.basecamp.store.dto;

import java.util.Date;

import lombok.Data;

@Data
public class StoreOrderDto {
    private int id;
    private int user_id;
    private int used_point;
    private int payment_amount;
    private String receiver_name;
	private String phone;
    private String delivery_address;
    private String status;
    private Date created_at;
}
