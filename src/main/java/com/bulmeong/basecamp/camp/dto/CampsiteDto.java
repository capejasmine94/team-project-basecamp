package com.bulmeong.basecamp.camp.dto;

import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

import lombok.Data;

@Data
public class CampsiteDto {
    private int id;
    private String account;
    private String password;
    private String name;
    private String seller;
    private String postcode;
    private String address;
    private String detail_address;
    private String phone;
    private String camp_phone;
    private String email;
    private String url;
    private String map_image;
    private String profile_image;
    private String description;
    private String manner_start;
    private String manner_end;
    private String check_in;
    private String check_out;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date peak_start_date;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date peak_end_date;
    private String notice;
    private int adult_pay;
    private int kid_pay;
    private int car_pay;
    private int peak_pay;
    private int max_ordertime;
    private int read_count;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date opentime;
    private Date created_at;
    private String facility_notice;
    private String refund_notice;
    private String is_authenticated;
}
