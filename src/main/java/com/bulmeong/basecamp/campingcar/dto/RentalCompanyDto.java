package com.bulmeong.basecamp.campingcar.dto;

import java.util.Date;

import lombok.Data;

@Data
public class RentalCompanyDto {
    private int id;
    private int location_category_id;
    private String account_id;
    private String account_pw;
    private String crn;
    private String comp_name;
    private String representative_name;
    private String comp_email;
    private String comp_address;
    private String contact_number;
    private String comp_profile_image;
    private String comp_nickname;
    private String comp_url;
    private Date created_at;
}
