package com.bulmeong.basecamp.camp.dto;

import java.util.Date;

import lombok.Data;

@Data
public class CampsiteDto {
    private int id;
    private String account;
    private String password;
    private String name;
    private String seller;
    private String address;
    private String phone;
    private String email;
    private String url;
    private String main_image;
    private String map_image;
    private String description;
    private Date manner_start;
    private Date manner_end;
    private Date check_in;
    private Date check_out;
    private String notice;
    private int adult_pay;
    private int kid_pay;
    private int car_pay;
    private int peak_pay;
    private int max_ordertime;
    private int read_count;
    private Date opentime;
    private Date created_at;
}
