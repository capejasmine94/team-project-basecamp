package com.bulmeong.basecamp.secondHandProduct.dto;

import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

@Data
public class neighborhoodDto {
    private int neighborhood_id;
    private int user_id;
    private int region_id;
    private String neighborhood_certification_status;
    private String main_neighborhood;
    private String neighborhood_selectionRange;
    private BigDecimal region_x;
    private BigDecimal region_y;
    private Date created_at;
    private Date updated_at;

}
