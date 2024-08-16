package com.bulmeong.basecamp.secondHandProduct.dto;

import lombok.Data;

import java.util.Date;

@Data
public class ProductBuyerDto {

    private int product_id;
    private int buyer_user_id;
    private String nickname;
    private String profile_image;
    private String title;
    private Date message_time;

}
