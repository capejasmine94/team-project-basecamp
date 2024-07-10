package com.bulmeong.basecamp.club.dto;

import java.util.Date;

import lombok.Data;

@Data
public class ClubPostDto {
    private int id;
    private int club_id;
    private int user_id;
    private int category_id;
    private String title;
    private String content;
    private Date created_at;
    private int read_count;

}
