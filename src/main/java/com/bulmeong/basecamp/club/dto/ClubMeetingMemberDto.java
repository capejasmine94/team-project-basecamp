package com.bulmeong.basecamp.club.dto;

import java.util.Date;

import lombok.Data;

@Data
public class ClubMeetingMemberDto {
    private int id;
    private int meeting_id;
    private int user_id;
    private Date joined_at;
}
