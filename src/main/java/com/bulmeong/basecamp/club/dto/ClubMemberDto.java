package com.bulmeong.basecamp.club.dto;

import java.util.Date;

import lombok.Data;

@Data
public class ClubMemberDto {
    private int id;
    private int club_id;
    private int user_id;
    private int role_id;
    private Date updated_at;
    private Date joined_at;
}
