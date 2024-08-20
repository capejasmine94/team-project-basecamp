package com.bulmeong.basecamp.store.dto;

import java.util.Date;

import lombok.Data;

@Data
public class OrderDeliveryInfoDto {
    private int id;
    private int order_product_id;
    private String courier;
    private String tracking_number;
    private Date delivered_date;
}
