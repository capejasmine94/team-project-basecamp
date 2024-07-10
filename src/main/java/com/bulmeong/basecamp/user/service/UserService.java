package com.bulmeong.basecamp.user.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bulmeong.basecamp.user.dto.UserDto;
import com.bulmeong.basecamp.user.mapper.UserSqlMapper;

@Service
public class UserService {
    @Autowired
    private UserSqlMapper userSqlMapper;
    //============================================================
    //  유저 Dto 가져오기
    //============================================================
    public UserDto getUserByAccountAndPassword(UserDto userDto){
        return userSqlMapper.getUserByAccountAndPassword(userDto);
    }
    //------------------------------------------------------------

    
}
