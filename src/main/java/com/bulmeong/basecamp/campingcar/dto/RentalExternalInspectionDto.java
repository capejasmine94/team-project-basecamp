package com.bulmeong.basecamp.campingcar.dto;

import java.util.Date;

import lombok.Data;

@Data
public class RentalExternalInspectionDto {
    private int id;
    private int reservation_id;
    private String front_view;
    private String passenger_front_view;
    private String passenger_rear_view;
    private String rear_view;
    private String driver_rear_view;
    private String driver_front_view;
    private Date create_at;
}


