package com.bulmeong.basecamp.secondHandProduct.dto;

import lombok.Data;

@Data
public class LikeReviewDto {
    private int like_review_id;
    private String like_review_content;
    private int like_count;
}
