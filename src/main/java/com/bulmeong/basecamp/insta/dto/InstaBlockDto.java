package com.bulmeong.basecamp.insta.dto;

import java.util.Date;

import lombok.Data;

@Data
public class InstaBlockDto {
    private int id;
    private int blocker_id;
    private int blocked_id;
    private Date created_at;
}
