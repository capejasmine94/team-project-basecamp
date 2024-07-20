package com.bulmeong.basecamp.store.dto;

import lombok.Data;
import java.util.*;

@Data
public class StoreProductDiscountDto {
    private int id;
    private Date start_date;
    private Date end_date;
    private double percentage;
}
