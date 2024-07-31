package com.bulmeong.basecamp.club.dto;


import java.util.Date;

import lombok.Data;

@Data
public class ClubDto {
    private int id;
    private int category_id;
    private int user_id;
    private int region_id;
    private String name;
    private String description;
    private int capacity;
    private Date created_at;
    private String main_image;

}
