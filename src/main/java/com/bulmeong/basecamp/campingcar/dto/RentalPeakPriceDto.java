package com.bulmeong.basecamp.campingcar.dto;

import java.util.Date;

import lombok.Data;

@Data
public class RentalPeakPriceDto {
    private int id;
    private int product_id;
    private int weekdays_price;
    private int weekend_price;
    private Date peak_start_date;
    private Date peak_end_date;
    private Date created_at;
    private Date updated_at;
}
