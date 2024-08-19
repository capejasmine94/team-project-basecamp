package com.bulmeong.basecamp.secondHandProduct.dto;

import lombok.Data;

@Data
public class LikeTransactionReviewListDto {

    private int like_transaction_review_id;
    private int buyer_user_id;
    private int like_review_id;
    private String like_review_content;
    private int like_count;

}
