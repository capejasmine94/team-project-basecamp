package com.bulmeong.basecamp.store.dto;

import lombok.Data;

@Data
public class OrderProductOptionValueDto {
    private int id;
    private int order_product_id;
    private int option_value_id;
}