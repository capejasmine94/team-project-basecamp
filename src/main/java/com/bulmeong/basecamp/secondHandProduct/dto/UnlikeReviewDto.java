package com.bulmeong.basecamp.secondHandProduct.dto;

import lombok.Data;

@Data
public class UnlikeReviewDto {
    private int unlike_review_id;
    private String unlike_review_content;
    private int unlike_count;
}
