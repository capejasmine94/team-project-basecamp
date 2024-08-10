package com.bulmeong.basecamp.insta.dto;

import java.util.Date;

import lombok.Data;

@Data
public class InstaSearchDto {
    private int id;
    private int user_id;
    private int tag_id;
    private String content;
    private Date created_at;
}
