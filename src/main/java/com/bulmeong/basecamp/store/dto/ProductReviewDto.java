package com.bulmeong.basecamp.store.dto;

import java.util.*;
import lombok.Data;

@Data
public class ProductReviewDto {
    private int id;
    private int user_id;
    private int order_product_id;
    private String content;
    private int rating;
    private Date created_at;
}
