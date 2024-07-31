package com.bulmeong.basecamp.camp.dto;

import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

import lombok.Data;

@Data
public class CampsiteDto {
    private int id;
    private String account;
    private String password;
    private String seller_name;
    private String camp_name;
    private String postcode;
    private String address;
    private String detail_address;
    private String seller_phone;
    private String camp_phone;
    private String email;
    private String url;
    private String crn;
    private String map_image;
    private String profile_image;
    private String description;
   
    private String notice;
    private String facility_notice;
    private String refund_notice;

    @DateTimeFormat(pattern = "HH:mm")
    private Date manner_start;
    @DateTimeFormat(pattern = "HH:mm")
    private Date manner_end;
    @DateTimeFormat(pattern = "HH:mm")
    private Date check_in;
    @DateTimeFormat(pattern = "HH:mm")
    private Date check_out;

    private int adult_pay;
    private int kid_pay;
    private int car_pay;
    private int peak_pay;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date peak_start_date;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date peak_end_date;
    
    private int max_ordertime;
    private int read_count;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date opentime;
    private Date created_at;
    private String is_authenticated;
}
