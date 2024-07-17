package com.bulmeong.basecamp.club.dto;

import java.util.Date;

import lombok.Data;

@Data
public class ClubBookmarkDto {
    private int id;
    private int club_id;
    private int user_id;
    private Date created_at;
}
