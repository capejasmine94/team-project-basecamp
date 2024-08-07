package com.bulmeong.basecamp.campingcar.dto;

import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

import lombok.Data;

@Data
public class ReservationDto {   
    int id;
    int rent_user_id;
    int product_id;
    @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm")
    Date start_date;
    @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm")
    Date end_date;
    String progress;
    Date created_at;
    Date cancellation_date; 

}
