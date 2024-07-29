package com.bulmeong.basecamp.user.dto;

import java.util.Date;

import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

@Data
public class UserDto {
    private int id;
    private String account;
    private String password;
    private String name;
    private String nickname;
    private String email;
    private String phone;
    private String profile_image;
    private int mileage;
    private String gender;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date birth;
    private Date created_at;
}
