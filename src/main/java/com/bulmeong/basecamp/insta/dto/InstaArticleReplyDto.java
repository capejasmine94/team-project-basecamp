package com.bulmeong.basecamp.insta.dto;

import java.util.Date;

import lombok.Data;

@Data
public class InstaArticleReplyDto {
    private int id;
    private int user_id;
    private int comment_id;
    private String content;
    private Date created_at;
}
