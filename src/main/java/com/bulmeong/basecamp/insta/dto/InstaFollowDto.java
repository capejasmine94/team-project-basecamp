package com.bulmeong.basecamp.insta.dto;

import java.util.Date;

import lombok.Data;

@Data
public class InstaFollowDto {
    private int id;
    private int follower_user_id;
    private int following_user_id;
    private Date created_at;
}
