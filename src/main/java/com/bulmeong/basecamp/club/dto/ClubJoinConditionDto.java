package com.bulmeong.basecamp.club.dto;

import lombok.Data;

@Data
public class ClubJoinConditionDto {
    private int id;
    private int club_id;
    private int min_age;
    private int max_age;
    private String gender;

}
