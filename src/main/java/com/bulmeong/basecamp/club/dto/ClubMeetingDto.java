package com.bulmeong.basecamp.club.dto;

import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

import lombok.Data;

@Data
public class ClubMeetingDto {
    private int id;
    private int club_id;
    private int user_id;
    private String main_image;
    private String name;
    private String description;
    private String location;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date meeting_date;
    private int fee;
    private int capacity;
    private Date created_at;
    
}
