package com.bulmeong.basecamp.insta.mapper;

import org.apache.ibatis.annotations.Mapper;

import com.bulmeong.basecamp.insta.dto.InstaArticleDto;
import com.bulmeong.basecamp.insta.dto.InstaArticleImgDto;
import com.bulmeong.basecamp.insta.dto.InstaUserInfoDto;
import com.bulmeong.basecamp.user.dto.UserDto;

@Mapper
public interface InstaSqlMapper {
    public int instaUserConfirom(UserDto userDto);
    public void insertInstaUserInfo(InstaUserInfoDto InstaUserInfoDto);
    public InstaUserInfoDto selectUserInfoByUserId(int user_id);
    public void insertInstaArticle(InstaArticleDto instaArticleDto);
    public void insertInstaArticleImg(InstaArticleImgDto instaArticleImgDto);
}
