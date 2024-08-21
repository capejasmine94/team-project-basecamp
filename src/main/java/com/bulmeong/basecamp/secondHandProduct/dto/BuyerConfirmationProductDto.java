package com.bulmeong.basecamp.secondHandProduct.dto;

import lombok.Data;

import java.util.Date;

@Data
public class BuyerConfirmationProductDto {
    private int product_id;
    private int user_id;
    private int buyer_user_id;
    private int category_id;
    private int polygon_id;
    private String main_image;
    private String title;
    private int price;
    private String explanation;
    private String status;
    private int read_count;
    private int chat_room_count;
    private int wish_list_count;
    private Date created_at;
    private Date updated_at;
    private String category_name;
    private String polygon_name;

}
