package com.bulmeong.basecamp.store.dto;

import lombok.Data;
import java.util.*;

@Data
public class StoreProductDto {
    private int id;
    private int store_id;
    private int subcategory_id;
    private int discount_id;
    private String name;
    private String description;
    private String main_image;
    private int price;
    private int quantity;
    private Date created_at;
    private Date updated_at;
}