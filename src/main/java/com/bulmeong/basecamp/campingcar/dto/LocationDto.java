package com.bulmeong.basecamp.campingcar.dto;

import java.util.Date;

import lombok.Data;

@Data
public class LocationDto  {
    
    private int id;
    private String name;
    private Date created_at;
    
}
