package com.bulmeong.basecamp.club.dto;

import java.util.Date;

import lombok.Data;

@Data
public class ClubBlacklistDto {
    private int id;
    private int club_id;
    private int user_id;
    private String reason;
    private Date blacklisted_at;
    private String blacklist_type;
    private Date unban_date;
}
