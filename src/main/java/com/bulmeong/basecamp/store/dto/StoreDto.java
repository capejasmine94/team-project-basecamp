package com.bulmeong.basecamp.store.dto;

import lombok.Data;
import java.util.Date;

@Data
public class StoreDto {
    private int id;
    private String account_id;
    private String account_pw;
    private String seller;
    private String phone;
    private String email;
    private String name;
    private int crn;
    private String postCode;
    private String address;
    private String detail_address;
    // private String web_link;
    private String description;
    private String main_image;
    private String status;
    private Date created_at;
}