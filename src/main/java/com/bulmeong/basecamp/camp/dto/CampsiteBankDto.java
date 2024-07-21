package com.bulmeong.basecamp.camp.dto;

import java.util.Date;

import lombok.Data;

@Data
public class CampsiteBankDto {
    private int id;
    private int campsite_id;
    private String bank_name;
    private String bank_account;
    private String holder;
    private Date created_at;
}
