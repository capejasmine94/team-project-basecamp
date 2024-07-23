package com.bulmeong.basecamp.store.dto;

import lombok.Data;

@Data
public class ProductWishDto {
    private int id;
    private int user_id;
    private int product_id;
}
