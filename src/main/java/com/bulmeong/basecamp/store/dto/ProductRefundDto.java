package com.bulmeong.basecamp.store.dto;

import java.util.Date;

import lombok.Data;

@Data
public class ProductRefundDto {
    private int id;
    private int reason_refund_id;
    private int order_product_id;
    private String receiver_name;
    private String phone;
    private String return_address;
    private Date created_at;
}
