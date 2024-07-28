package com.bulmeong.basecamp.user.dto;

import java.util.Date;

import lombok.Data;

@Data
public class MileageLogDto {
    private int id;
    private int user_id;
    private String mileage_log;
    private int change_amount;
    private Date created_at;
}
