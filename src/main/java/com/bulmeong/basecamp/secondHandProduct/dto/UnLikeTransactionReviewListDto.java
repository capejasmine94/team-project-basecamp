package com.bulmeong.basecamp.secondHandProduct.dto;

import lombok.Data;

@Data
public class UnLikeTransactionReviewListDto {
    private int unlike_transaction_review_id;
    private int buyer_user_id;
    private int unlike_review_id;
    private String unlike_review_content;
    private int unlike_count;
}
