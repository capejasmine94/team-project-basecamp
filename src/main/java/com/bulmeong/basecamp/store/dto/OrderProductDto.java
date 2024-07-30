package com.bulmeong.basecamp.store.dto;

import java.util.Date;

import lombok.Data;

@Data
public class OrderProductDto {
    private int id;
    private int order_id;
    private int product_id;
    private int quantity;
    private String status;
    private int order_price;
    private int used_point;
    private int product_price;
    private double discount_percentage;
    private String product_name;
    private String product_main_image;
    private Date created_at;
}