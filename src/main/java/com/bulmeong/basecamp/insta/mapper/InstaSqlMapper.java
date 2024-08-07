package com.bulmeong.basecamp.insta.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.bulmeong.basecamp.insta.dto.InstaArticleCommentDto;
import com.bulmeong.basecamp.insta.dto.InstaArticleDto;
import com.bulmeong.basecamp.insta.dto.InstaArticleImgDto;
import com.bulmeong.basecamp.insta.dto.InstaArticleLikeDto;
import com.bulmeong.basecamp.insta.dto.InstaArticleReplyDto;
import com.bulmeong.basecamp.insta.dto.InstaArticleTagDto;
import com.bulmeong.basecamp.insta.dto.InstaBookmarkDto;
import com.bulmeong.basecamp.insta.dto.InstaCommentLikeDto;
import com.bulmeong.basecamp.insta.dto.InstaFollowDto;
import com.bulmeong.basecamp.insta.dto.InstaUserInfoDto;
import com.bulmeong.basecamp.user.dto.UserDto;

import java.util.*;

@Mapper
public interface InstaSqlMapper {
    public int instaUserConfirom(UserDto userDto);
    public void insertInstaUserInfo(InstaUserInfoDto InstaUserInfoDto);
    public InstaUserInfoDto selectUserInfoByUserId(@Param("user_id") int user_id);
    public void insertInstaArticle(InstaArticleDto instaArticleDto);
    public void insertInstaArticleImg(InstaArticleImgDto instaArticleImgDto);
    public List<InstaArticleDto> selectArticleAll();
    public List<InstaArticleImgDto> selectArticleImgByArticleId(@Param("article_id") int article_id);
    public InstaUserInfoDto selectUserInfoById(@Param("id") int id);
    public int selectArticleCountByUserId(@Param("user_id") int user_id);
    public void deleteArticleById(@Param("id") int id);

    // 댓글 _ 자바스크립트
    public void createComment(InstaArticleCommentDto instaArticleCommentDto);
    public List<InstaArticleCommentDto> getCommentList(@Param("article_id") int article_id);
    public void deleteComment(@Param("id") int id);
    public int commentCountByArticleId(@Param("article_id") int article_id);

    // 대댓글 _ 자바스크립트
    public void createReply(InstaArticleReplyDto instaArticleReplyDto);
    public List<InstaArticleReplyDto> getReplyList(int comment_id);
    public void deleteReply (int id);
    public int replyCountByCommentId(int comment_id);

    // 좋아요
    public void createLike(InstaArticleLikeDto instaArticleLikeDto);
    public int countLikeByArticleId(@Param("article_id") int article_id); // 게시글 좋아요 개수
    public int countLikeByArticleIdAndUserId(InstaArticleLikeDto instaArticleLikeDto); // 게시글 좋아요 상태 여부
    public void deleteLikeByArticleIdAndUserId(InstaArticleLikeDto instaArticleLikeDto);

    // 팔로우 _ 자바스크립트
    public void insertFollowByUserId(InstaFollowDto instaFollowDto);
    public int followerCountByFollowerUserId(@Param("follower_user_id") int follower_user_id); // 몇번 회원이 몇명을 팔로우 했는지
    public int followingCountByFollowingUserId(@Param("following_user_id") int following_user_id); // 몇번 회원을 몇명이 팔로잉 했지
    public int confirmFollowStatus(InstaFollowDto instaFollowDto); // 내가 팔로우를 했는지
    public void deleteFollowByFollowerUserIdAndFollowingUserId(InstaFollowDto instaFollowDto);

    // 댓글 좋아요 _ 자바스크립트
    public void createCommentLike(InstaCommentLikeDto instaCommentLikeDto);
    public int countLikeByCommentId(@Param("comment_id") int comment_id); // 댓글 좋아요 개수
    public int countLikeByCommentIdAndUserId(InstaCommentLikeDto instaCommentLikeDto); // 댓글 좋아요 상태 여부
    public void deleteLikeByCommentIdAndUserId(InstaCommentLikeDto instaCommentLikeDto);

    // 해시태그 레츠고 _ 자바스크립트 사용 안 함
    public Integer instaHashtagConfirm(@Param("text") String text); // 이미 존재하는 태그 ID 확인
    // null을 반환 받아야 할 때는 리턴 값으로 int(기본타입)은 안되기 때문에 Integer로 반환 받아줘야 함

    public void insertHashtagLetsGo(@Param("text") String text); // 새로운 태그 삽입
    public void insertArticletag(InstaArticleTagDto instaArticleTagDto); // 게시글-태그 관계 삽입
    public List<String> selectHashtagByArticleId(int article_id); // 게시글 해시태그 출력

    // 유저가 작성한 게시글 이미지List 출력
    public List<InstaArticleImgDto> instaWriteArticleGetImgByUserId(int user_id);
    // 유저가 저장한 북마크 게시글 이미지List 출력
    public List<InstaArticleImgDto> instaSaveBookmarkByUserId(int user_id);

    // 북마크
    public void createBookmarkByUserIdAndArticleId(InstaBookmarkDto instaBookmarkDto);
    public int confirmBookmarkByArticleIdAndUserId(InstaBookmarkDto instaBookmarkDto); // 게시글 북마크 상태 여부
    public void deleteBookmarkByArticleIdAndUserId(InstaBookmarkDto instaBookmarkDto);

    // 인스스 _ 로그인 유저가 팔로우 한 유저 출력
    public List<InstaUserInfoDto> selectInstaStoryUserInfoByFollowerUserId(int follower_user_id); // follower_user_id =  로그인 한 유저 아이디
}









