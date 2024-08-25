package com.bulmeong.basecamp.user.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.bulmeong.basecamp.user.dto.MileageLogDto;
import com.bulmeong.basecamp.user.dto.UserDto;

@Mapper
public interface UserSqlMapper {
    public void insertUser(UserDto userDto);
    public UserDto getUserByAccountAndPassword(UserDto userDto);
    public UserDto getUserById(UserDto userDto);
    public UserDto getUserByAccount(UserDto userDto);
    public List<UserDto> getAllUser();
    public int getAllUserCount();
    public int getLastUserCount();
    public int getUserCountByAccount(String account);

    public void insertKakaoUser(UserDto userDto);

    public List<MileageLogDto> selectUserMileageLogByUserId(int user_id);
}
