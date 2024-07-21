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
    private Date manner_start;
    private Date manner_end;
    private Date check_in;
    private Date check_out;
    private Date peak_startDate;
    private Date peak_endDate;
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
