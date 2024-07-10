package com.bulmeong.basecamp.camp.dto;

import java.util.Date;

import lombok.Data;

@Data
public class CampsiteAreaDto {
    private int id;
    private int campsite_id;
    private String name;
    private int prise;
    private int size_x;
    private int size_y;
    private String main_image;
    private String map_image;
    private String description;
    private int max_people;
    private int max_car;
    private int limit_people;
    private int min_ordertime;
    private int adult_pay;
    private int kid_pay;
    private int car_pay;
    private Date created_at;
}
