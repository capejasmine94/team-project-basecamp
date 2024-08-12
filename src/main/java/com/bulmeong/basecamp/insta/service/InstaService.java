package com.bulmeong.basecamp.insta.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.thymeleaf.util.StringUtils;

import com.bulmeong.basecamp.insta.dto.InstaArticleCommentDto;
import com.bulmeong.basecamp.insta.dto.InstaArticleDto;
import com.bulmeong.basecamp.insta.dto.InstaArticleImgDto;
import com.bulmeong.basecamp.insta.dto.InstaArticleLikeDto;
import com.bulmeong.basecamp.insta.dto.InstaArticleReplyDto;
import com.bulmeong.basecamp.insta.dto.InstaArticleTagDto;
import com.bulmeong.basecamp.insta.dto.InstaBookmarkDto;
import com.bulmeong.basecamp.insta.dto.InstaCommentLikeDto;
import com.bulmeong.basecamp.insta.dto.InstaFollowDto;
import com.bulmeong.basecamp.insta.dto.InstaTagDto;
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

    // 세션아이디 받아와서 인스타 정보 뽑는 쿼리
    public InstaUserInfoDto userInfoByUserId(int user_id){
        InstaUserInfoDto instaUserInfoDto = instaSqlMapper.selectUserInfoByUserId(user_id);

        return instaUserInfoDto;
    }

    // 인스타 id로 인스타유저 정보 뽑는 쿼리
    public InstaUserInfoDto selectUserInfo(int id){
        InstaUserInfoDto instaUserInfoDto = instaSqlMapper.selectUserInfoById(id);

        return instaUserInfoDto;
    }

    public int selectArticleCountByUserId(int user_id){
        int articleCount = instaSqlMapper.selectArticleCountByUserId(user_id);
        
        return articleCount;
    }

    // 게시판 글 작성
    public void writeArticle(InstaArticleDto instaArticleDto, String text){
        instaSqlMapper.insertInstaArticle(instaArticleDto);

        // System.out.println("게시글ID: " + instaArticleDto.getId());
        int article_id = instaArticleDto.getId();
        // System.out.println("게시글ID:" + article_id);

        String[] hashtags = text.split(",");

        for(String hashtag : hashtags){
            //리턴 타입에 null도 있을 수 있어서 반환 타입은 int(기본타입)이 아닌 Integer로 설정
            Integer tag_id = instaSqlMapper.instaHashtagConfirm(hashtag.trim()); // 메모리 사용량이 적은 int대신 참조 타입 Integer사용
            // trim = 양쪽 끝에 있는 공백(스페이스, 탭, 줄바꿈 등)을 제거하는 데 사용. 문자열의 내용 중간에 있는 공백은 제거하지 않음

            if(tag_id == null){
                instaSqlMapper.insertHashtagLetsGo(hashtag.trim());
                tag_id = instaSqlMapper.instaHashtagConfirm(hashtag.trim());
            }

            InstaArticleTagDto instaArticleTagDto = new InstaArticleTagDto();
            instaArticleTagDto.setArticle_id(article_id);
            instaArticleTagDto.setTag_id(tag_id);

            instaSqlMapper.insertArticletag(instaArticleTagDto);
        }
    }


    public void writeArticleImg(List<InstaArticleImgDto>instaArticleImgDtoList){
        for(InstaArticleImgDto instaArticleImgDto: instaArticleImgDtoList){
            instaSqlMapper.insertInstaArticleImg(instaArticleImgDto);
        }
    }

    // 게시글 List
    public List<Map<String, Object>> selectInstaArticleList(int insta_login_user_id){
        List<Map<String, Object>> result = new ArrayList<>();

        List<InstaArticleDto> instaArticleDtoList = instaSqlMapper.selectArticleAll();

        for(InstaArticleDto instaArticleDto : instaArticleDtoList){
            Map<String, Object> map = new HashMap<>();
            
            map.put("instaArticleDto", instaArticleDto);
            
            // html escape _ 개행 (하긴 했는데 개행하면 레이아웃 지대 이상해짐)
            String escapedContent = StringUtils.escapeXml(instaArticleDto.getContent());
            escapedContent = escapedContent.replaceAll("\n", "<br>");
            instaArticleDto.setContent(escapedContent);

            int article_id = instaArticleDto.getId();
            int user_id = instaArticleDto.getUser_id();
            InstaUserInfoDto instaUserInfoDto = instaSqlMapper.selectUserInfoById(user_id);
            map.put("instaUserInfoDto", instaUserInfoDto);
            // System.out.println("instaUserInfoDto: " + instaUserInfoDto);

            // 댓글 수 카운트
            int commentCount = instaSqlMapper.commentCountByArticleId(article_id);
            map.put("commentCount", commentCount);

            InstaArticleLikeDto instaArticleLikeDto = new InstaArticleLikeDto();
            instaArticleLikeDto.setArticle_id(article_id);
            instaArticleLikeDto.setUser_id(insta_login_user_id);
            int like = instaSqlMapper.countLikeByArticleIdAndUserId(instaArticleLikeDto); // 게시글 좋아요 상태 여부

            // instaSqlMapper.deleteLikeByArticleIdAndUserId(instaArticleLikeDto);

            int likeCount = instaSqlMapper.countLikeByArticleId(article_id);
            map.put("likeCount", likeCount);

            map.put("like", like);
            // System.out.println("like:" + like);


            // 북마크 유무
            InstaBookmarkDto instaBookmarkDto = new InstaBookmarkDto();
            instaBookmarkDto.setArticle_id(article_id);
            instaBookmarkDto.setUser_id(insta_login_user_id);
            int bookmark = instaSqlMapper.confirmBookmarkByArticleIdAndUserId(instaBookmarkDto);
            map.put("bookmark", bookmark);

            List<InstaArticleImgDto> instaArticleImgDtoList = instaSqlMapper.selectArticleImgByArticleId(article_id);
            map.put("instaArticleImgDtoList", instaArticleImgDtoList); // 이미지 리스트를 map에 추가
            // for(InstaArticleImgDto instaArticleImgDto : instaArticleImgDtoList){
            //     map.put("instaArticleImgDto", instaArticleImgDto);
            // } 이렇게 작성하면 instaArticleImgDto라는 키로 여러 이미지가 덮어쓰여짐

            // System.out.println(instaArticleImgDtoList);


            // 해시태그
            List<InstaTagDto> hashtags = instaSqlMapper.selectHashtagByArticleId(article_id);
            
            map.put("hashtags", hashtags);

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
            Map<String, Object> map = new HashMap<>();

            InstaUserInfoDto instaUserInfoDto = instaSqlMapper.selectUserInfoById(instaArticleCommentDto.getUser_id());

            // 대댓글 List 쿼리
            int comment_id = instaArticleCommentDto.getId();
            List<InstaArticleReplyDto> replyList = instaSqlMapper.getReplyList(comment_id);

            // 대댓글 리스트를 담을 리스트
            List<Map<String, Object>> getReplyList = new ArrayList<>();
            for(InstaArticleReplyDto instaArticleReplyDto : replyList){
                InstaUserInfoDto instaUserInfoDtoReply = instaSqlMapper.selectUserInfoById(instaArticleReplyDto.getUser_id());

                Map<String, Object> replyMap = new HashMap<>();
                replyMap.put("instaUserInfoDtoReply", instaUserInfoDtoReply);
                replyMap.put("instaArticleReplyDto", instaArticleReplyDto);

                getReplyList.add(replyMap);
            }

            // 댓글에 달린 답글의 개수
            int replyCount = instaSqlMapper.replyCountByCommentId(comment_id);
            map.put("replyCount", replyCount);

            map.put("instaUserInfoDto", instaUserInfoDto);
            map.put("instaArticleCommentDto", instaArticleCommentDto);
            map.put("replyList", getReplyList); // 대댓글 리스트 추가

            result.add(map);
        }

        return result;
    }

    public void deleteComment(int id){
        instaSqlMapper.deleteComment(id);
    }

    // 대댓글
    public void registerReply(InstaArticleReplyDto instaArticleReplyDto){
        instaSqlMapper.createReply(instaArticleReplyDto);
    }

    public List<Map<String, Object>> getReplyList(int comment_id){
        List<Map<String, Object>> result = new ArrayList<>();

        List<InstaArticleReplyDto> ReplyList = instaSqlMapper.getReplyList(comment_id);

        for(InstaArticleReplyDto instaArticleReplyDto : ReplyList){
            InstaUserInfoDto instaUserInfoDto = instaSqlMapper.selectUserInfoById(instaArticleReplyDto.getUser_id());

            Map<String, Object> map = new HashMap<>();

            map.put("instaArticleReplyDto", instaArticleReplyDto);
            map.put("instaUserInfoDto", instaUserInfoDto);

            result.add(map);
        }

        return result;
    }

    public void deleteReply(int id){
        instaSqlMapper.deleteReply(id);
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

    // 게시물 삭제
    public void deleteArticle(int id){
        instaSqlMapper.deleteArticleById(id);
    }




    // 댓글 좋아요 _ 자바스크립트
    public void commentLike(InstaCommentLikeDto instaCommentLikeDto){
        instaSqlMapper.createCommentLike(instaCommentLikeDto);
    }

    // 댓글 좋아요 개수
    public int commentLikeCount(int comment_id){
        int commentLikeCount = instaSqlMapper.countLikeByCommentId(comment_id);

        return commentLikeCount;
    }

    // 댓글 좋아요 상태 여부
    public boolean commentIsLiked(InstaCommentLikeDto instaCommentLikeDto){

        return instaSqlMapper.countLikeByCommentIdAndUserId(instaCommentLikeDto) > 0;
    }

    public void commentUnLike(InstaCommentLikeDto instaCommentLikeDto){
        instaSqlMapper.deleteLikeByCommentIdAndUserId(instaCommentLikeDto);
    }
    

    

    // 팔로우 _ 자바스크립트
    public void follow(InstaFollowDto instaFollowDto){
        instaSqlMapper.insertFollowByUserId(instaFollowDto);
    }

    // 몇번 회원이 몇명을 팔로우 했는지
    public int followerCount(int follower_user_id){
        int followCount = instaSqlMapper.followerCountByFollowerUserId(follower_user_id);

        return followCount;
    }

    // 몇번 회원을 몇명이 팔로잉 했지
    public int followingCount(int following_user_id){
        int followingCount = instaSqlMapper.followingCountByFollowingUserId(following_user_id);

        return followingCount;
    }

    // 내가 팔로우를 했는지
    public boolean confirmFollow(InstaFollowDto instaFollowDto){

        return instaSqlMapper.confirmFollowStatus(instaFollowDto) > 0; // 0보다 크면 follow한거
    }

    public void unFollow(InstaFollowDto instaFollowDto){
        instaSqlMapper.deleteFollowByFollowerUserIdAndFollowingUserId(instaFollowDto);
    }


    // 유저가 작성한 게시글 이미지 출력
    public List<InstaArticleImgDto> getInstaWriteArticleImgList(int user_id){

        List<InstaArticleImgDto> instaArticleImgDtoList = instaSqlMapper.instaWriteArticleGetImgByUserId(user_id);

        return instaArticleImgDtoList;
    }

    // 유저가 저장한 북마크 게시글 이미지List 출력
    public List<InstaArticleImgDto> getInstaSaveBookmarkImgList(int user_id){

        List<InstaArticleImgDto> instaBookmarkArticleImgDtoList = instaSqlMapper.instaSaveBookmarkByUserId(user_id);

        return instaBookmarkArticleImgDtoList;
    }



    // 북마크
    public void insertBookmark(InstaBookmarkDto instaBookmarkDto){
        instaSqlMapper.createBookmarkByUserIdAndArticleId(instaBookmarkDto);
    }

    public boolean confirmBookmark(InstaBookmarkDto instaBookmarkDto){

        // 0보다 크면 북마크 누른거
        return instaSqlMapper.confirmBookmarkByArticleIdAndUserId(instaBookmarkDto) > 0;
    }

    public void deleteBookmark(InstaBookmarkDto instaBookmarkDto){
        instaSqlMapper.deleteBookmarkByArticleIdAndUserId(instaBookmarkDto);
    }


    // 인스스 _ 로그인 유저가 팔로우 한 유저 출력
    public List<InstaUserInfoDto> likeInstaStoryButIsNotSoSad(int follower_user_id){
        List<InstaUserInfoDto> instaStory = instaSqlMapper.selectInstaStoryUserInfoByFollowerUserId(follower_user_id);

        return instaStory;
    }

    // 태그 클릭시 나오는 페이지
    public List<InstaArticleImgDto> selectArticleFirstImg(int tag_id){
        List<InstaArticleImgDto> instaArticleImgDtoList = instaSqlMapper.selectArticleFirstImgByTagId(tag_id);

        return instaArticleImgDtoList;
    }

    public InstaTagDto selectTag(int id){
        InstaTagDto instaTagDto = instaSqlMapper.selectTagByTagId(id);

        return instaTagDto;
    }

    // 검색
    // 일반 검색(content 사용)
    // public void insertSearch(String content, int user_id){
    //     instaSqlMapper.insertSearchContent(content, user_id);
    // }

    public List<InstaArticleImgDto> selectArticleImgFromSearchContent(String content){
        List<InstaArticleImgDto> instaArticleImgDtoListBySearchContent = instaSqlMapper.selectArticleImgByLikeSearchContentAndLikeArticleContent(content);

        return instaArticleImgDtoListBySearchContent;
    }

    public String selectResultContent(String content, int user_id){
        String selectResultContent = instaSqlMapper.selectResultContentText(content, user_id);

        return selectResultContent;
    }

    // 해시태그 검색(tag_id 사용)
    public void insertSearchContentOrTag(String searchWord, int user_id){

        // 태그가 있는지 확인
        InstaTagDto instaTagDto = instaSqlMapper.selectTagDtoByTagText(searchWord);

        if(instaTagDto != null){ // 태그가 존재하는 경우
            instaSqlMapper.insertSearchTagId(instaTagDto.getId(), user_id);
        }else{ // 태그가 없는 경우
            instaSqlMapper.insertSearchContent(searchWord, user_id);
        }
    }

    public void insertSearchTagId(int tag_id, int user_id){
        instaSqlMapper.insertSearchTagId(tag_id, user_id);
    }

    public List<InstaArticleImgDto> selectArticleImgBySearchTagText(String content){
        List<InstaArticleImgDto> instaArticleImgDtoListByTagText = instaSqlMapper.selectArticleImgBySearchTagText(content);

        return instaArticleImgDtoListByTagText;
    }

    public InstaTagDto selectTagDto(String text){
        InstaTagDto instaTagDto = instaSqlMapper.selectTagDtoByTagText(text);

        return instaTagDto;
    }

    // 최근 검색 _ resultType 해쉬맵 사용
    public List<Map<String, Object>> recentSearch(int user_id){
        List<Map<String, Object>> recentSearchList = instaSqlMapper.recentSearchByUserId(user_id);

        return recentSearchList;
    }

    // content로 일반 검색 삭제
    public void recentSearchDeleteContent(String content, int user_id){
        instaSqlMapper.recentSearchDeleteByContentAndUserId(content, user_id);
    }

    // 태그 id로 태그 검색 삭제
    public void recentSearchDeleteTag(int tag_id, int user_id){
        instaSqlMapper.recentSearchDeleteByTagIdAndUserId(tag_id, user_id);
    }

    // 검색 기록 전체 삭제
    public void recentSearchAllDelete(int user_id){
        instaSqlMapper.recentSearchAllDeleteByUserId(user_id);
    }

}




