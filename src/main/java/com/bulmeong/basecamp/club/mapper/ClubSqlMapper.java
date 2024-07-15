package com.bulmeong.basecamp.club.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.bulmeong.basecamp.club.dto.ClubDto;
import com.bulmeong.basecamp.club.dto.ClubJoinConditionDto;
import com.bulmeong.basecamp.club.dto.ClubMemberDto;
import com.bulmeong.basecamp.club.dto.ClubPostCategoryDto;
import com.bulmeong.basecamp.club.dto.ClubPostCommentDto;
import com.bulmeong.basecamp.club.dto.ClubPostDto;
import com.bulmeong.basecamp.club.dto.ClubPostImageDto;
import com.bulmeong.basecamp.club.dto.ClubRegionCategoryDto;
import com.bulmeong.basecamp.user.dto.UserDto;


@Mapper
public interface ClubSqlMapper {
    public void insertClubDto(ClubDto clubDto);
    public void insertClubJoinCondition(ClubJoinConditionDto clubJoinConditionDto);
    public void insertClubPostDto(ClubPostDto clubPostsDto);
    public void insertClubPostImage(ClubPostImageDto clubPostImageDto);
    public void insertClubMemberDto(ClubMemberDto clubMemberDto);
    public List<ClubRegionCategoryDto> selectRegionCategory();
    public List<ClubDto> selectClubList();
    public List<ClubDto> selectJoinClubList(int user_id);
    public List<ClubPostDto> selectClubPostDtoList(int club_id);
    public UserDto selectUserDtoById(int id);
    public ClubPostCategoryDto selectPostCategoryDtoById(int id);
    public List<ClubPostCategoryDto> selectPostCategoryDto();
    public List<ClubPostImageDto> seleClubPostImageDtoList();
    public ClubPostDto selectPostDtoById(int id);
    public void insertClubPostCommentDto(ClubPostCommentDto clubPostCommentDto);
}
