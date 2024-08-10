package com.bulmeong.basecamp.store.dto;

import lombok.Data;

@Data
public class ProductRefundReasonDto {
    private int id;
    private boolean is_buyer_responsible;
    private String description; 
}
