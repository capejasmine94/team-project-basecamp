package com.bulmeong.basecamp.store.dto;

import lombok.Data;

@Data
public class ProductImageDto {
    private int id;
    private int product_id;
    private String location;
}
