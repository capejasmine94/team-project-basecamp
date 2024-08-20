package com.bulmeong.basecamp.campingcar.dto;

import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

import lombok.Data;

@Data
public class CampingcarDto {
    private int id;
    private int rental_company_id;
    private int category_id;
    private String product_name;
    private String main_img;
    private String short_description;
    private String detailed_description;
    private int rental_fee_weekdays;
    private int rental_fee_weekend;
    private String garage_address;
    private String overnight_stay;
    private int seating_capacity;
    private int sleeping_capacity;
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm")
    private Date avaid_time;
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm")
    private Date return_dateTime;
    private int driver_license_id;
    private int driver_experience_id;
    private int driver_age_id;
    private String pet_friendly;
    private String cancellation_policy;
    private String is_opened;
    private Date created_at;
    private Date update_date;


}
