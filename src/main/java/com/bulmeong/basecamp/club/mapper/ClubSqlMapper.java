package com.bulmeong.basecamp.club.mapper;

import org.apache.ibatis.annotations.Mapper;

import com.bulmeong.basecamp.club.dto.ClubDto;
import com.bulmeong.basecamp.club.dto.ClubJoinConditionDto;


@Mapper
public interface ClubSqlMapper {
    public void insertClubDto(ClubDto clubDto);
    public void insertClubJoinCondition(ClubJoinConditionDto clubJoinConditionDto);
}
