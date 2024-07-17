package com.bulmeong.basecamp.campingcar.dto;

import java.util.Date;

import lombok.Data;

@Data
public class CampingcarDto {
    private int id;
    private int rentalCompanyId;
    private int categoryId;
    private String productName;
    private String mainImg;
    private String shortDescription;
    private String detailedDescription;
    private int rentalFee;
    private String garageAddress;
    private String overnightStay;
    private int seatingCapacity;
    private int sleepingCapacity;
    private Date avaidTime;
    private Date returnDateTime;
    private int driverLicenseId;
    private int driverExperienceId;
    private int driverAgeId;
    private String petFriendly;
    private String cancellationPolicy;
    private String isOpened;
    private Date created_at;
    private Date updateDate;

}
