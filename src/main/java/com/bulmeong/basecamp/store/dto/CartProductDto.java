package com.bulmeong.basecamp.store.dto;

import java.util.Date;

import lombok.Data;

@Data
public class CartProductDto {
    private int id;
    private int user_id;
    private int product_id;
    private int quantity;
    private Date created_at;
    private Date updated_at;
}
