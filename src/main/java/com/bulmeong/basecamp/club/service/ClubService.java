package com.bulmeong.basecamp.club.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bulmeong.basecamp.club.dto.ClubDto;
import com.bulmeong.basecamp.club.dto.ClubJoinConditionDto;
import com.bulmeong.basecamp.club.mapper.ClubSqlMapper;
import com.bulmeong.basecamp.user.mapper.UserSqlMapper;

@Service
public class ClubService {

    @Autowired ClubSqlMapper clubSqlMapper;
    @Autowired UserSqlMapper userSqlMapper;

    public void createNewClub(ClubDto clubDto, ClubJoinConditionDto clubJoinConditionDto ){
        clubSqlMapper.insertClubDto(clubDto);

        int club_id = clubDto.getId();
        ClubJoinConditionDto c  = new ClubJoinConditionDto();
        clubJoinConditionDto.setClub_id(club_id);

        clubSqlMapper.insertClubJoinCondition(clubJoinConditionDto);
        }
}
