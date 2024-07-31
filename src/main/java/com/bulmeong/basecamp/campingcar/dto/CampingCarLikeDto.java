package com.bulmeong.basecamp.campingcar.dto;

import java.util.Date;

import lombok.Data;

@Data
public class CampingCarLikeDto {
    private int id; 
    private int product_id;
    private int rent_user_id;
    private Date created_at;
}
