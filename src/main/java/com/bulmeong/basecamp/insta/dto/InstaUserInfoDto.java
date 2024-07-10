package com.bulmeong.basecamp.insta.dto;

import java.util.Date;

import lombok.Data;

@Data
public class InstaUserInfoDto {
    private int id;
    private int user_id;
    private String insta_nickname;
    private String insta_profile_img;
    private String intro;
    private String is_private;
    private Date created_at;
}
