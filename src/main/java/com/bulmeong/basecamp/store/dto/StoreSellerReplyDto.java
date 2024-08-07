package com.bulmeong.basecamp.store.dto;

import java.util.Date;

import lombok.Data;

@Data
public class StoreSellerReplyDto {
    private int id;
    private int review_id;
    private String content;
    private Date created_at;
}
