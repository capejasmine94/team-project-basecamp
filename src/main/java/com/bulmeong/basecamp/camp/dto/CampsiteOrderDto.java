package com.bulmeong.basecamp.camp.dto;

import java.util.Date;

import lombok.Data;

@Data
public class CampsiteOrderDto {
    private int id;
    private int point_id;
    private Date start_date;
    private Date end_Date;
    private String customer_name;
    private int prise;
    private int sale;
    private String customer_phone;
    private int adult_count;
    private int kid_count;
    private String progress;
    private Date created_at;
}
