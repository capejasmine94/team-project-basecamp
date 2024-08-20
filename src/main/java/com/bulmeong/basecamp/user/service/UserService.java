package com.bulmeong.basecamp.user.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

    // 유저등록
    public void insertUser(UserDto userDto) {
        if(getUserByAccount(userDto) != null) return;
        userSqlMapper.insertUser(userDto);
    }
    //아이디 / 비밀번호
    public UserDto getUserByAccountAndPassword(UserDto userDto){
        return userSqlMapper.getUserByAccountAndPassword(userDto);
    }

    //아이디
    public UserDto getUserByAccount(String account){
        account = account.replace(" ", "");
        UserDto userDto = new UserDto();
        userDto.setAccount(account);
        return userSqlMapper.getUserByAccount(userDto);   
    }
    public UserDto getUserByAccount(UserDto userDto){
        return userSqlMapper.getUserByAccount(userDto);   
    }

    public boolean isExistAccount(String account){
        account = account.replace(" ", "");

        return userSqlMapper.getUserCountByAccount(account)>0;
    }

    //고유번호
    public UserDto getUserById(int id) {
        UserDto userDto = new UserDto();
        userDto.setId(id);
        return userSqlMapper.getUserById(userDto);
    }
    public UserDto getUserById(UserDto userDto) {
        return userSqlMapper.getUserById(userDto);
    }
    
    public int getLastUserCount() {
        return userSqlMapper.getLastUserCount();
    }

    /**모든 유저의 정보를 담은 맵입니다.
     @param dtoList => [List(UserDto)] 유저의 정보가 담긴 리스트
     @param count => [int] 유저의 수
    */
    public Map<String, Object> getAllUserInfo() {
        Map<String,Object> result = new HashMap<>();

        int userCount = userSqlMapper.getAllUserCount();
        List<UserDto> userList = userSqlMapper.getAllUser();

        result.put("dtoList", userList);
        result.put("count", userCount);

        return result;
    }
    //------------------------------------------------------------

    public void registerKakaoUser(UserDto userDto){
        userSqlMapper.insertKakaoUser(userDto);
    }
}
