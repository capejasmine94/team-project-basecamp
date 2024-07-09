package com.bulmeong.basecamp.secondHandProduct.secondHandProductDto.secondHandProduct;

import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

@Data
public class RegionDto {
    private int region_id;
    private String region_name;
    private BigDecimal region_x;
    private BigDecimal region_y;
    private Date created_at;
    private Date updated_at;
}