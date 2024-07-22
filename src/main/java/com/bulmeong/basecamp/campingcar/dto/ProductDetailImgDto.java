package com.bulmeong.basecamp.campingcar.dto;

import lombok.Data;

@Data
public class ProductDetailImgDto {
    private int id;
    private int product_id;
    private String location;
    private String original_filename;
}
