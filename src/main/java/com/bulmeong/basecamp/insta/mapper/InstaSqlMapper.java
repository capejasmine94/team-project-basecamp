package com.bulmeong.basecamp.insta.mapper;

import org.apache.ibatis.annotations.Mapper;

import com.bulmeong.basecamp.insta.dto.InstaArticleCommentDto;
import com.bulmeong.basecamp.insta.dto.InstaArticleDto;
import com.bulmeong.basecamp.insta.dto.InstaArticleImgDto;
import com.bulmeong.basecamp.insta.dto.InstaArticleLikeDto;
import com.bulmeong.basecamp.insta.dto.InstaFollowDto;
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
    public InstaUserInfoDto selectUserInfoById(int id);
    public int selectArticleCountByUserId(int user_id);
    public void deleteArticleById(int id);

    // 댓글
    public void createComment(InstaArticleCommentDto instaArticleCommentDto);
    public List<InstaArticleCommentDto> getCommentList(int article_id);
    public void deleteComment(int id);
    public int commentCountByArticleId(int article_id);

    // 좋아요
    public void createLike(InstaArticleLikeDto instaArticleLikeDto); // 좋아요 insert
    public int countLikeByArticleId(int article_id); // 어떤 글에 몇명이 좋아요 했는지 
    public int countLikeByArticleIdAndUserId(InstaArticleLikeDto instaArticleLikeDto); // 몇번 회원이 몇번 글에 좋아요 했는지
    public void deleteLikeByArticleIdAndUserId(InstaArticleLikeDto instaArticleLikeDto);

    // 팔로우 _ 자바스크립트
    public void insertFollowByUserId(int follower_user_id, int following_user_id);
    public int followCountByFollowerUserId(int follower_user_id); // 몇번 회원이 몇명을 팔로우 했는지
    public int followingCountByFollowingUserId(int following_user_id); // 몇번 회원을 몇명이 팔로잉 했지
    public int confirmFollowStatus(InstaFollowDto instaFollowDto); // 팔로우 했는지
    public void deleteFollowByFollowerUserIdAndFollowingUserId(InstaFollowDto instaFollowDto);


}









