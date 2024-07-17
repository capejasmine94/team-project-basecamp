package com.bulmeong.basecamp.insta.mapper;

import org.apache.ibatis.annotations.Mapper;

import com.bulmeong.basecamp.insta.dto.InstaArticleCommentDto;
import com.bulmeong.basecamp.insta.dto.InstaArticleDto;
import com.bulmeong.basecamp.insta.dto.InstaArticleImgDto;
import com.bulmeong.basecamp.insta.dto.InstaUserInfoDto;
import com.bulmeong.basecamp.user.dto.UserDto;

import java.util.*;

@Mapper
public interface InstaSqlMapper {
    public int instaUserConfirom(UserDto userDto);
    public void insertInstaUserInfo(InstaUserInfoDto InstaUserInfoDto);
    public InstaUserInfoDto selectUserInfoByUserId(int user_id);
    public void insertInstaArticle(InstaArticleDto instaArticleDto);
    public void insertInstaArticleImg(InstaArticleImgDto instaArticleImgDto);
    public List<InstaArticleDto> selectArticleAll();
    public List<InstaArticleImgDto> selectArticleImgByArticleId(int article_id);

    // 댓글
    public void createComment(InstaArticleCommentDto instaArticleCommentDto);
    public List<InstaArticleCommentDto> getCommentList(int article_id);
    public void deleteComment(int id);
}
