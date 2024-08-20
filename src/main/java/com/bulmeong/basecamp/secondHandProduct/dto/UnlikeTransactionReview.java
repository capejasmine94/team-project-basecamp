package com.bulmeong.basecamp.secondHandProduct.dto;

import lombok.Data;

@Data
public class UnlikeTransactionReview {
    private int unlike_transaction_review_id;
    private int product_id;
    private int buyer_user_id;
    private int seller_user_id;
    private int unlike_review_id;
}
