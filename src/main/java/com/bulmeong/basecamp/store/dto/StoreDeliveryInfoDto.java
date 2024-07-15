package com.bulmeong.basecamp.store.dto;

import lombok.Data;

@Data
public class StoreDeliveryInfoDto {
    private int id;
    private int store_id;
    private int origin_postcode;
    private String origin_address;
    private String detail_origin_address;
    private int fee;
    private int min_amount;
    private int return_postcode;
    private String return_address;
    private String detail_return_address;
    private int return_delivery_fee;
}