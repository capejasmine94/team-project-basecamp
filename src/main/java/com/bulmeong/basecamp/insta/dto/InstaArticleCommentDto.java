package com.bulmeong.basecamp.insta.dto;

import java.util.Date;

import lombok.Data;

@Data
public class InstaArticleCommentDto {
    private int id;
    private int user_id;
    private int article_id;
    private String comment;
    private Date created_at;
}
