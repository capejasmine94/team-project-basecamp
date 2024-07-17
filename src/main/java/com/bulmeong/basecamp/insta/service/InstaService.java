package com.bulmeong.basecamp.insta.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bulmeong.basecamp.insta.dto.InstaArticleDto;
import com.bulmeong.basecamp.insta.dto.InstaArticleImgDto;
import com.bulmeong.basecamp.insta.dto.InstaUserInfoDto;
import com.bulmeong.basecamp.insta.mapper.InstaSqlMapper;
import com.bulmeong.basecamp.user.dto.UserDto;

import java.util.*;

@Service
public class InstaService {
    @Autowired
    private InstaSqlMapper instaSqlMapper;

    public int instaUserC(UserDto userDto){
        // 인스타 최초 접속 유저이면 instaUser = 0을 return
        // 인스타 프로필 등록한 유저이면 instaUser = 1을 return
        int instaUser = instaSqlMapper.instaUserConfirom(userDto);

        return instaUser;
    }

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

    // 게시글 List
    public List<Map<String, Object>> selectInstaArticleList(){
        List<Map<String, Object>> result = new ArrayList<>();

        List<InstaArticleDto> instaArticleDtoList = instaSqlMapper.selectArticleAll();

        for(InstaArticleDto instaArticleDto : instaArticleDtoList){
            Map<String, Object> map = new HashMap<>();
            
            map.put("instaArticleDto", instaArticleDto);

            int article_id = instaArticleDto.getId();
            int user_id = instaArticleDto.getUser_id();
            InstaUserInfoDto instaUserInfoDto = instaSqlMapper.selectUserInfoByUserId(user_id);
            map.put("instaUserInfoDto", instaUserInfoDto);

            List<InstaArticleImgDto> instaArticleImgDtoList = instaSqlMapper.selectArticleImgByArticleId(article_id);
            for(InstaArticleImgDto instaArticleImgDto : instaArticleImgDtoList){
                map.put("instaArticleImgDto", instaArticleImgDto);
            }

            result.add(map);

        }
        
        return result;

    }
}









