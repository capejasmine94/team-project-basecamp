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
     @DateTimeFormat(pattern = "HH:mm")
    private Date manner_start;
    @DateTimeFormat(pattern = "HH:mm")
    private Date manner_end;
    @DateTimeFormat(pattern = "HH:mm")
    private Date check_in;
    @DateTimeFormat(pattern = "HH:mm")
    private Date check_out;
    @DateTimeFormat(pattern = "YYYY:MM:dd")
    private Date peak_start_date;
    @DateTimeFormat(pattern = "YYYY:MM:dd")
    private Date peak_end_date;
    private String notice;
    private int adult_pay;
    private int kid_pay;
    private int car_pay;
    private int peak_pay;
    private int max_ordertime;
    private int read_count;
    private Date opentime;
    private Date created_at;
    private String is_authenticated;
}
