package com.bulmeong.basecamp.secondHandProduct.dto;

import lombok.Data;

import java.util.Date;

@Data
public class ProductBuyerDto {

    private int product_id;
    private String title;
    private String main_image;
    private int buyer_user_id;
    private int seller_user_id;
    private String nickname;
    private String profile_image;
    private Date message_time;

}
