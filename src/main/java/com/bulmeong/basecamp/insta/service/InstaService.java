package com.bulmeong.basecamp.insta.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bulmeong.basecamp.insta.dto.InstaUserInfoDto;
import com.bulmeong.basecamp.insta.mapper.InstaSqlMapper;

@Service
public class InstaService {
    @Autowired
    private InstaSqlMapper instaSqlMapper;

    public void instaUserInfo(InstaUserInfoDto InstaUserInfoDto){
        instaSqlMapper.insertInstaUserInfo(InstaUserInfoDto);
    }
}
