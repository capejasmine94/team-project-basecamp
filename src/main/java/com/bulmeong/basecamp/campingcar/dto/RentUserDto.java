package com.bulmeong.basecamp.campingcar.dto;

import java.util.Date;

import lombok.Data;

@Data
public class RentUserDto {
    private int id;
    private int user_id;
    private String driver_license_number;
    private int driver_license_id;
    private int driver_age_id;
    private int driver_experience_id;
    private String driver_license_image;
    private Date created_at;
}
