package com.bulmeong.basecamp.insta.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bulmeong.basecamp.club.dto.ClubPostDto;
import com.bulmeong.basecamp.club.dto.ClubPostImageDto;
import com.bulmeong.basecamp.insta.dto.InstaArticleDto;
import com.bulmeong.basecamp.insta.dto.InstaArticleImgDto;
import com.bulmeong.basecamp.insta.dto.InstaUserInfoDto;
import com.bulmeong.basecamp.insta.mapper.InstaSqlMapper;

import java.util.List;

@Service
public class InstaService {
    @Autowired
    private InstaSqlMapper instaSqlMapper;

    public void instaUserInfo(InstaUserInfoDto InstaUserInfoDto){
        instaSqlMapper.insertInstaUserInfo(InstaUserInfoDto);
    }

    public InstaUserInfoDto userInfoByUserId(int user_id){
        InstaUserInfoDto instaUserInfoDto = instaSqlMapper.selectUserInfoByUserId(user_id);

        return instaUserInfoDto;
    }

    // 게시판 글 작성
    public void writeArticle(InstaArticleDto instaArticleDto){
        instaSqlMapper.insertInstaArticle(instaArticleDto);
    }


    public void writeArticleImg(List<InstaArticleImgDto>instaArticleImgDtoList){
        for(InstaArticleImgDto instaArticleImgDto: instaArticleImgDtoList){
            instaSqlMapper.insertInstaArticleImg(instaArticleImgDto);
        }
    }
}
