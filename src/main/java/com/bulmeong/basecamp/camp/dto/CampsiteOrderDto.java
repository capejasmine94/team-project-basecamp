package com.bulmeong.basecamp.camp.dto;

import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

import lombok.Data;

@Data
public class CampsiteOrderDto {
    private int id;
    private int point_id;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date start_date;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date end_date;
    private String customer_name;
    private int prise;
    private int sale;
    private String customer_phone;
    private int adult_count;
    private int kid_count;
    private String progress;
    private String reservation_code;
    private Date created_at;
}
