package com.bulmeong.basecamp.secondHandProduct.dto;

import lombok.Data;

import java.util.Date;

@Data
public class SecondhandProductDto {
    private int product_id;
    private int user_id;
    private int category_id;
    private String title;
    private int price;
    private String explanation;
    private String status;
    private Date created_at;
}
