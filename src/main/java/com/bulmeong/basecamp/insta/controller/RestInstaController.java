package com.bulmeong.basecamp.insta.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.bulmeong.basecamp.insta.dto.InstaArticleCommentDto;
import com.bulmeong.basecamp.insta.dto.InstaArticleLikeDto;
import com.bulmeong.basecamp.insta.dto.InstaArticleReplyDto;
import com.bulmeong.basecamp.insta.dto.InstaCommentLikeDto;
import com.bulmeong.basecamp.insta.dto.InstaFollowDto;
import com.bulmeong.basecamp.insta.dto.InstaRestResponseDto;
import com.bulmeong.basecamp.insta.dto.InstaUserInfoDto;
import com.bulmeong.basecamp.insta.service.InstaService;
import com.bulmeong.basecamp.user.dto.UserDto;

import jakarta.servlet.http.HttpSession;

@RestController
@RequestMapping("/api/insta")
public class RestInstaController {

    @Autowired
    private InstaService instaService;

    // 게시글 List
    @RequestMapping("getArticleList")
    public InstaRestResponseDto getArticleList(@RequestParam("user_id") int s_user_id){
        InstaRestResponseDto instaRestResponseDto = new InstaRestResponseDto();
        instaRestResponseDto.setResult("success");

        instaRestResponseDto.add("articleList", instaService.selectInstaArticleList(s_user_id));

        return instaRestResponseDto;
    }


    // 좋아요 like insert
    @RequestMapping("like")
    public InstaRestResponseDto like(InstaArticleLikeDto params, HttpSession session){
        InstaRestResponseDto instaRestResponseDto = new InstaRestResponseDto();
        instaRestResponseDto.setResult("success");

        // 로그인이 되어있다는 가정하에
        UserDto userDto = (UserDto) session.getAttribute("sessionUserInfo");
        params.setUser_id(userDto.getId());

        instaService.like(params);

        return instaRestResponseDto;
    }

    @RequestMapping("unLike") // like delete
    public InstaRestResponseDto unLike(InstaArticleLikeDto params, HttpSession session){
        InstaRestResponseDto instaRestResponseDto = new InstaRestResponseDto();
        instaRestResponseDto.setResult("success");

        // 로그인이 되어있다는 가정하에
        UserDto userDto = (UserDto) session.getAttribute("sessionUserInfo");
        params.setUser_id(userDto.getId());

        instaService.unLike(params);

        return instaRestResponseDto;
    }

    // count의 경우 로그인 안 해도 보여야되기때문에 session을 받을 필요 없음
    @RequestMapping("getTotalLikeCount") // 해당 게시글 좋아요 개수
    public InstaRestResponseDto getTotalLikeCount(@RequestParam("article_id") int article_id){
        InstaRestResponseDto instaRestResponseDto = new InstaRestResponseDto();
        instaRestResponseDto.setResult("success");

        int count = instaService.getLikeTotalCount(article_id);
        instaRestResponseDto.add("count", count);

        return instaRestResponseDto; 
    }

    @RequestMapping("isLiked") // 해당 게시글에 내가 좋아요를 눌렀냐
    public InstaRestResponseDto isLiked(InstaArticleLikeDto params, HttpSession session){
        InstaRestResponseDto instaRestResponseDto = new InstaRestResponseDto();
        instaRestResponseDto.setResult("success");

        UserDto userDto = (UserDto) session.getAttribute("sessionUserInfo");
        params.setUser_id(userDto.getId());

        boolean x = instaService.isLiked(params);
        instaRestResponseDto.add("isLiked", x); // x값이 true, false로 변환이 됨

        return instaRestResponseDto;
    }


    // 댓글 좋아요
    @RequestMapping("commentLike")
    public InstaRestResponseDto commentLike(InstaCommentLikeDto params, HttpSession session){
        InstaRestResponseDto instaRestResponseDto = new InstaRestResponseDto();
        instaRestResponseDto.setResult("success");

        // 로그인이 되어있다는 가정하에
        UserDto userDto = (UserDto) session.getAttribute("sessionUserInfo");
        InstaUserInfoDto  instaUserInfoDto = instaService.userInfoByUserId(userDto.getId());
        params.setUser_id(instaUserInfoDto.getId());

        // System.out.println(params);

        // System.out.println("Received comment_id: " + params.getComment_id());
        // System.out.println("Received user_id: " + params.getUser_id());

        instaService.commentLike(params);

        return instaRestResponseDto;
    }

    @RequestMapping("commentUnLike")
    public InstaRestResponseDto commentUnLike(InstaCommentLikeDto params , HttpSession session){
        InstaRestResponseDto instaRestResponseDto = new InstaRestResponseDto();
        instaRestResponseDto.setResult("success");

        // 로그인이 되어있다는 가정하에
        UserDto userDto = (UserDto) session.getAttribute("sessionUserInfo");
        InstaUserInfoDto  instaUserInfoDto = instaService.userInfoByUserId(userDto.getId());
        params.setUser_id(instaUserInfoDto.getId());

        // session_id = following_user_id
        // user_id  follower_user_id
        instaService.commentUnLike(params);

        return instaRestResponseDto;

    }

    // 댓글 좋아요 유무 확인
    @RequestMapping("commentIsLiked")
    public InstaRestResponseDto commentIsLiked(InstaCommentLikeDto params, HttpSession session){
        InstaRestResponseDto instaRestResponseDto = new InstaRestResponseDto();
        instaRestResponseDto.setResult("success");

        // 로그인이 되어있다는 가정하에
        UserDto userDto = (UserDto) session.getAttribute("sessionUserInfo");
        InstaUserInfoDto  instaUserInfoDto = instaService.userInfoByUserId(userDto.getId());
        params.setUser_id(instaUserInfoDto.getId());


        boolean x = instaService.commentIsLiked(params);
        instaRestResponseDto.add("confirmCommentLike", x); // json으로 변환하면서 true 혹은 false 값을 변환해줌

        return instaRestResponseDto;
    }

    @RequestMapping("commentLikeCount")
    public InstaRestResponseDto commentLikeCount(@RequestParam("comment_id") int comment_id){
        InstaRestResponseDto instaRestResponseDto = new InstaRestResponseDto();
        instaRestResponseDto.setResult("success");

        int commentLikeCount = instaService.commentLikeCount(comment_id);
        instaRestResponseDto.add("likeCount", commentLikeCount);

        // System.out.println(commentLikeCount);

        return instaRestResponseDto;
    }



    // 댓글
    @RequestMapping("registerComment") // insert
    public InstaRestResponseDto registerComment(InstaArticleCommentDto params, HttpSession session){
        InstaRestResponseDto instaRestResponseDto = new InstaRestResponseDto();
        instaRestResponseDto.setResult("success");

        // 로그인이 되어있다는 가정하에
        UserDto userDto = (UserDto) session.getAttribute("sessionUserInfo");
        InstaUserInfoDto instaUserInfoDto = instaService.userInfoByUserId(userDto.getId());


        params.setUser_id(instaUserInfoDto.getId());

        instaService.registerComment(params);

        return instaRestResponseDto;
    }

    @RequestMapping("getCommentList")
    public InstaRestResponseDto getCommentList(@RequestParam("article_id") int article_id){
        InstaRestResponseDto instaRestResponseDto = new InstaRestResponseDto();
        instaRestResponseDto.setResult("success");

        instaRestResponseDto.add("commentList", instaService.getCommentList(article_id));

        return instaRestResponseDto;
    }

    // 댓글 삭제
    @RequestMapping("deleteComment")
    public InstaRestResponseDto deleteComment(@RequestParam("id") int id){
        InstaRestResponseDto instaRestResponseDto = new InstaRestResponseDto();
        instaRestResponseDto.setResult("success");

        instaService.deleteComment(id);

        return instaRestResponseDto;
    }

    // 대댓글 삭제
    @RequestMapping("deleteReply")
    public InstaRestResponseDto deleteReply(@RequestParam("id") int id){
        InstaRestResponseDto instaRestResponseDto = new InstaRestResponseDto();
        instaRestResponseDto.setResult("success");

        instaService.deleteReply(id);

        return instaRestResponseDto;
    }

    // 대댓글
    @RequestMapping("registerReply")
    public InstaRestResponseDto registerReply(InstaArticleReplyDto params, HttpSession session){
        InstaRestResponseDto instaRestResponseDto = new InstaRestResponseDto();
        instaRestResponseDto.setResult("success");

        // 로그인이 되어있다는 가정하에
        UserDto userDto = (UserDto) session.getAttribute("sessionUserInfo");
        InstaUserInfoDto instaUserInfoDto = instaService.userInfoByUserId(userDto.getId());

        params.setUser_id(instaUserInfoDto.getId());

        instaService.registerReply(params);

        return instaRestResponseDto;
    }



    // session id 받아와서 인스타 정보 뽑는 쿼리
    @RequestMapping("getMyInstaId")
    public InstaRestResponseDto getMyInstaId(HttpSession session){
        InstaRestResponseDto instaRestResponseDto = new InstaRestResponseDto();
        instaRestResponseDto.setResult("success");

        UserDto userDto = (UserDto)session.getAttribute("sessionUserInfo");

        InstaUserInfoDto instaUserInfoDto = instaService.userInfoByUserId(userDto.getId());

        instaRestResponseDto.add("myInstaId", instaUserInfoDto.getId());

        return instaRestResponseDto;
    }


    // 팔로우
    @RequestMapping("follow")
    public InstaRestResponseDto follow(InstaFollowDto params , HttpSession session){
        InstaRestResponseDto instaRestResponseDto = new InstaRestResponseDto();
        instaRestResponseDto.setResult("success");

        // 로그인이 되어있다는 가정하에
        UserDto userDto = (UserDto) session.getAttribute("sessionUserInfo");
        InstaUserInfoDto  instaUserInfoDto = instaService.userInfoByUserId(userDto.getId());
        params.setFollower_user_id(instaUserInfoDto.getId());

        // session_id = following_user_id
        // user_id  follower_user_id
        instaService.follow(params);

        return instaRestResponseDto;
    }

    @RequestMapping("unFollow")
    public InstaRestResponseDto unFollow(InstaFollowDto params , HttpSession session){
        InstaRestResponseDto instaRestResponseDto = new InstaRestResponseDto();
        instaRestResponseDto.setResult("success");

        // 로그인이 되어있다는 가정하에
        UserDto userDto = (UserDto) session.getAttribute("sessionUserInfo");
        InstaUserInfoDto  instaUserInfoDto = instaService.userInfoByUserId(userDto.getId());
        params.setFollower_user_id(instaUserInfoDto.getId());

        // session_id = following_user_id
        // user_id  follower_user_id
        instaService.unFollow(params);

        return instaRestResponseDto;

    }

    // 몇번 회원이 몇명을 팔로우 했는지 _ 자바스크립트 사용 안 함
    @RequestMapping("getFollowerCount")
    public InstaRestResponseDto getFollowerCount(@RequestParam("user_id") int follower_user_id){
        InstaRestResponseDto instaRestResponseDto = new InstaRestResponseDto();
        instaRestResponseDto.setResult("success");

        int followCount = instaService.followerCount(follower_user_id);
        instaRestResponseDto.add("followCount", followCount);

        return instaRestResponseDto;
    }

    // 몇번 회원을 몇명이 팔로잉 했는지
    @RequestMapping("getFollowingCount")
    public InstaRestResponseDto getFollowingCount(@RequestParam("following_user_id") int following_user_id){
        InstaRestResponseDto instaRestResponseDto = new InstaRestResponseDto();
        instaRestResponseDto.setResult("success");

        int followingCount = instaService.followingCount(following_user_id);
        instaRestResponseDto.add("followingCount", followingCount);

        return instaRestResponseDto;
    }

    // 내가 팔로우를 했는지
    @RequestMapping("confirmFollow")
    public InstaRestResponseDto confirmFollow(InstaFollowDto params , HttpSession session){
        InstaRestResponseDto instaRestResponseDto = new InstaRestResponseDto();
        instaRestResponseDto.setResult("success");

        // 로그인이 되어있다는 가정하에
        UserDto userDto = (UserDto) session.getAttribute("sessionUserInfo");
        InstaUserInfoDto  instaUserInfoDto = instaService.userInfoByUserId(userDto.getId());
        params.setFollower_user_id(instaUserInfoDto.getId());

        // session_id = following_user_id
        // user_id  follower_user_id
        boolean x = instaService.confirmFollow(params);
        instaRestResponseDto.add("confirmFollow", x); // json으로 변환하면서 true 혹은 false 값을 변환해줌

        return instaRestResponseDto;
    }
}




