package com.bulmeong.basecamp.club.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bulmeong.basecamp.club.dto.ClubDto;
import com.bulmeong.basecamp.club.mapper.ClubSqlMapper;
import com.bulmeong.basecamp.user.mapper.UserSqlMapper;

@Service
public class ClubService {

    @Autowired ClubSqlMapper clubSqlMapper;
    @Autowired UserSqlMapper userSqlMapper;

    public void createNewClub(ClubDto clubDto ){
        clubSqlMapper.insertClubDto(clubDto);
        
        }
}
