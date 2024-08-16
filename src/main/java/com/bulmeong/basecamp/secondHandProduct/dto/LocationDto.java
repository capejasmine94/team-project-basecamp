package com.bulmeong.basecamp.secondHandProduct.dto;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class LocationDto {

    private int location_id;
    private int user_id;
    private String selected_area_name;
}
