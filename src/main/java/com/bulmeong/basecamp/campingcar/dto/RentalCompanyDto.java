package com.bulmeong.basecamp.campingcar.dto;

import java.util.Date;

import lombok.Data;

@Data
public class RentalCompanyDto {
    private int id;
    private int locationCategory;
    private String sellerAccount;
    private String sellerPassword;
    private String crn;
    private String compName;
    private String representativeName;
    private String compEmail;
    private String compAddress;
    private String contactNumber;
    private String compProfileImage;
    private String compNickname;
    private String compUrl;
    private Date createdAt;
}
