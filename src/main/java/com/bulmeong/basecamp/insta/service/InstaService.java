package com.bulmeong.basecamp.insta.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bulmeong.basecamp.insta.dto.InstaArticleCommentDto;
import com.bulmeong.basecamp.insta.dto.InstaArticleDto;
import com.bulmeong.basecamp.insta.dto.InstaArticleImgDto;
import com.bulmeong.basecamp.insta.dto.InstaArticleLikeDto;
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
    public List<Map<String, Object>> selectInstaArticleList(int s_user_id){
        List<Map<String, Object>> result = new ArrayList<>();

        List<InstaArticleDto> instaArticleDtoList = instaSqlMapper.selectArticleAll();

        for(InstaArticleDto instaArticleDto : instaArticleDtoList){
            Map<String, Object> map = new HashMap<>();
            
            map.put("instaArticleDto", instaArticleDto);

            int article_id = instaArticleDto.getId();
            int user_id = instaArticleDto.getUser_id();
            InstaUserInfoDto instaUserInfoDto = instaSqlMapper.selectUserInfoByUserId(user_id);
            map.put("instaUserInfoDto", instaUserInfoDto);

            // 댓글 수 카운트
            int commentCount = instaSqlMapper.commentCountByArticleId(article_id);
            map.put("commentCount", commentCount);

            InstaArticleLikeDto instaArticleLikeDto = new InstaArticleLikeDto();
            instaArticleLikeDto.setArticle_id(article_id);
            instaArticleLikeDto.setUser_id(s_user_id);
            int like = instaSqlMapper.countLikeByArticleIdAndUserId(instaArticleLikeDto);

            // instaSqlMapper.deleteLikeByArticleIdAndUserId(instaArticleLikeDto);

            int likeCount = instaSqlMapper.countLikeByArticleId(article_id);
            map.put("likeCount", likeCount);

            map.put("like", like);
            // System.out.println("like:" + like);

            List<InstaArticleImgDto> instaArticleImgDtoList = instaSqlMapper.selectArticleImgByArticleId(article_id);
            for(InstaArticleImgDto instaArticleImgDto : instaArticleImgDtoList){
                map.put("instaArticleImgDto", instaArticleImgDto);
            }

            result.add(map);

        }
        
        return result;

    }

    // 댓글
    public void registerComment(InstaArticleCommentDto instaArticleCommentDto){
        instaSqlMapper.createComment(instaArticleCommentDto);
    }

    public List<Map<String, Object>> getCommentList(int article_id){
        List<Map<String, Object>> result = new ArrayList<>();

        List<InstaArticleCommentDto> commentList = instaSqlMapper.getCommentList(article_id);

        for(InstaArticleCommentDto instaArticleCommentDto : commentList){
            InstaUserInfoDto instaUserInfoDto = instaSqlMapper.selectUserInfoByUserId(instaArticleCommentDto.getUser_id());

            Map<String, Object> map = new HashMap<>();

            map.put("instaArticleCommentDto", instaArticleCommentDto);
            map.put("instaUserInfoDto", instaUserInfoDto);

            result.add(map);
        }

        return result;
    }

    public void deleteComment(int id){
        instaSqlMapper.deleteComment(id);
    }


    // 좋아요
    public void like(InstaArticleLikeDto instaArticleLikeDto){
        instaSqlMapper.createLike(instaArticleLikeDto);
    }

    public void unLike(InstaArticleLikeDto instaArticleLikeDto){
        instaSqlMapper.deleteLikeByArticleIdAndUserId(instaArticleLikeDto);
    }

    // 어떤 글에 몇명이 좋아요 했는지 
    public int getLikeTotalCount(int article_id){
        return instaSqlMapper.countLikeByArticleId(article_id);
    }

    // 0보다 크면 true(=Like)
    // 몇번 회원이 몇번 글에 좋아요 했는지
    public boolean isLiked(InstaArticleLikeDto instaArticleLikeDto){
        return instaSqlMapper.countLikeByArticleIdAndUserId(instaArticleLikeDto) > 0;
    }
}



















