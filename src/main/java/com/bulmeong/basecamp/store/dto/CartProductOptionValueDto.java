package com.bulmeong.basecamp.store.dto;

import lombok.Data;

@Data
public class CartProductOptionValueDto {
    private int id;
    private int cart_product_id;
    private int option_value_id;
}