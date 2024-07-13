package com.bulmeong.basecamp.club.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import com.bulmeong.basecamp.club.dto.ClubDto;
import com.bulmeong.basecamp.club.dto.ClubJoinConditionDto;
import com.bulmeong.basecamp.club.dto.ClubMemberDto;
import com.bulmeong.basecamp.club.dto.ClubPostDto;
import com.bulmeong.basecamp.club.dto.ClubPostImageDto;
import com.bulmeong.basecamp.club.dto.ClubRegionCategoryDto;
import com.bulmeong.basecamp.club.mapper.ClubSqlMapper;
import com.bulmeong.basecamp.common.dto.ImageDto;
import com.bulmeong.basecamp.user.dto.UserDto;
import com.bulmeong.basecamp.user.mapper.UserSqlMapper;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
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

    public void writeClubPost(ClubPostDto clubPostDto, List<ImageDto>imageDtolist){
        
        clubSqlMapper.insertClubPostDto(clubPostDto);

        for(ImageDto imageDto : imageDtolist){
            int id = clubPostDto.getId();
            ClubPostImageDto clubPostImageDto = new ClubPostImageDto();
            clubPostImageDto.setPost_id(id);
            clubPostImageDto.setPost_img_location(imageDto.getLocation());
            clubSqlMapper.insertClubPostImage(clubPostImageDto);
        }

    }

    // 소모임 회원가입
    public void joinClub(ClubMemberDto clubMemberDto){
        clubSqlMapper.insertClubMemberDto(clubMemberDto);
    }

    // 지역 카테고리
    public List<ClubRegionCategoryDto> findRegionCategory(){

        List<ClubRegionCategoryDto> regionCategroyDtoList = clubSqlMapper.selectRegionCategory();

        return regionCategroyDtoList;
    }

    // 모든 소모임 목록 뽑기
    public List<ClubDto> findClubDtoList(){

        List<ClubDto> clubDtoList = clubSqlMapper.selectClubList();
        return clubDtoList;
    }

    // 가입한 소모임 목록 뽑기 (by user_id)
    public List<ClubDto> findJoinClubDtoList(int user_id){

        List<ClubDto> joinClubDtoList =  clubSqlMapper.selectJoinClubList(user_id);
        return joinClubDtoList;
    }

    // 각 소모임 게시글 목록 뽑기 (by club_id)
    public  List<Map<String, Object>> getClubPostDtoList(int club_id){
        List<Map<String, Object>>postDetailList = new ArrayList<>();
        List<ClubPostDto> clubPostDtoList = clubSqlMapper.selectClubPostDtoList(club_id);
       
        for(ClubPostDto clubPostDto : clubPostDtoList){
            int userPk = clubPostDto.getUser_id();
            UserDto userDto = clubSqlMapper.selectUserDtoById(userPk);

            Map<String, Object> postDetailMap = new HashMap<>();
            postDetailMap.put("clubPostDto", clubPostDto);
            postDetailMap.put("userDto", userDto);

            postDetailList.add(postDetailMap);
        }

        return postDetailList;
    }

    


    

}
