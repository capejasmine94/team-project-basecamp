package com.bulmeong.basecamp.club.dto;

import lombok.Data;

@Data
public class ClubJoinConditionDto {
    private int id;
    private int club_id;
    private int start_year;
    private int end_year;
    private String gender;

}
