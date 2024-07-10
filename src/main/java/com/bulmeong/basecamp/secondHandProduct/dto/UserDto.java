package com.bulmeong.basecamp.secondHandProduct.dto;

import lombok.Data;

@Data
public class UserDto {
    private int user_id;
    private String account;
    private String password;
    private String nickname;
}
