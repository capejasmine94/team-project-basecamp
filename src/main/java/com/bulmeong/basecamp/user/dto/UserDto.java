package com.bulmeong.basecamp.user.dto;

import java.util.Date;

import lombok.Data;

@Data
public class UserDto {
    private int id;
    private String account;
    private String password;
    private String nickname;
    private String email;
    private String phone;
    private String profile_image;
    private int point;
    private String gender;
    private Date birth;
    private Date created_at;
}
