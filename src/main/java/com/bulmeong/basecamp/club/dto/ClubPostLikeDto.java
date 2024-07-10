package com.bulmeong.basecamp.club.dto;

import java.util.Date;

import lombok.Data;

@Data
public class ClubPostLikeDto {
    private int id;
    private int post_id;
    private int user_id;
    private Date created_at;
}
