package com.bulmeong.basecamp.insta.dto;

import java.util.Date;

import lombok.Data;

@Data
public class InstaReplyLikeDto {
    private int id;
    private int user_id;
    private int reply_id;
    private Date created_at;
}
