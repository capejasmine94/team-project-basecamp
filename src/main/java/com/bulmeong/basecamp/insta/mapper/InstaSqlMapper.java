package com.bulmeong.basecamp.insta.mapper;

import org.apache.ibatis.annotations.Mapper;

import com.bulmeong.basecamp.insta.dto.InstaArticleDto;
import com.bulmeong.basecamp.insta.dto.InstaArticleImgDto;
import com.bulmeong.basecamp.insta.dto.InstaUserInfoDto;

@Mapper
public interface InstaSqlMapper {
    public void insertInstaUserInfo(InstaUserInfoDto InstaUserInfoDto);
    public InstaUserInfoDto selectUserInfoByUserId(int user_id);
    public void insertInstaArticle(InstaArticleDto instaArticleDto);
    public void insertInstaArticleImg(InstaArticleImgDto instaArticleImgDto);
}
