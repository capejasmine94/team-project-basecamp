package com.bulmeong.basecamp.club.dto;

import java.util.Date;

import lombok.Data;

@Data
public class ClubPostCommentLIkeDto {
    private int id;
    private int comment_id;
    private int user_id;
    private Date created_at;
}
