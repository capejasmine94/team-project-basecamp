package com.bulmeong.basecamp.secondHandProduct.dto;

import lombok.Data;

import java.util.Date;

@Data
public class TransactionReviewDto {
    private int transaction_review_id;
    private int user_id;
    private int product_id;
    private String review_message;
    private Date created_at;
}
