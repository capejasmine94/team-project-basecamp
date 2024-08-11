package com.bulmeong.basecamp.camp.dto;

import java.util.Date;

import lombok.Data;

@Data
public class CampsiteReview {
    private int id;
    private int user_id;
    private int order_id;
    private String content;
    private Date created_at;
    private String answer_content;
    private Date answer_created_at;
}
