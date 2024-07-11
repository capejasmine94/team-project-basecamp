package com.bulmeong.basecamp.club.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bulmeong.basecamp.club.dto.ClubDto;
import com.bulmeong.basecamp.club.dto.ClubJoinConditionDto;
import com.bulmeong.basecamp.club.dto.ClubPostDto;
import com.bulmeong.basecamp.club.mapper.ClubSqlMapper;
import com.bulmeong.basecamp.user.mapper.UserSqlMapper;
import java.util.Map;

@Service
public class ClubService {

    @Autowired ClubSqlMapper clubSqlMapper;
    @Autowired UserSqlMapper userSqlMapper;

    // 소모임 개설하기

    public void createNewClub(ClubDto clubDto, ClubJoinConditionDto clubJoinConditionDto ){
        clubSqlMapper.insertClubDto(clubDto);

        int club_id = clubDto.getId();
        ClubJoinConditionDto clubJoinConditionDtoIncludeClubId  = new ClubJoinConditionDto();
        clubJoinConditionDto.setClub_id(club_id);

        clubSqlMapper.insertClubJoinCondition(clubJoinConditionDto);
        }

    //  소모임 게시판 글 작성하기

    public void writeClubPost(ClubPostDto clubPostDto){
        clubSqlMapper.insertClubPostDto(clubPostDto);
    

        System.out.println("글작성데이터 확인" + clubPostDto);
    }

    public ClubDto selectClubDto(){
        ClubDto clubDto = clubSqlMapper.selectClubDto();
        return clubDto;
    }
}
