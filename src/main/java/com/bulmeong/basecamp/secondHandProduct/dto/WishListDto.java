package com.bulmeong.basecamp.secondHandProduct.dto;

import lombok.Data;

@Data
public class WishListDto {
    private int wish_list_id;
    private int user_id;
    private int product_id;
    private int wish_list_count;
}
