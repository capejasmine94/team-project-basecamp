package com.bulmeong.basecamp.insta.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.bulmeong.basecamp.insta.dto.InstaArticleCommentDto;
import com.bulmeong.basecamp.insta.dto.InstaArticleLikeDto;
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


    // 좋아요
    @RequestMapping("like") // like insert
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

    @RequestMapping("deleteComment")
    public InstaRestResponseDto deleteComment(@RequestParam("id") int id){
        InstaRestResponseDto instaRestResponseDto = new InstaRestResponseDto();
        instaRestResponseDto.setResult("success");

        instaService.deleteComment(id);

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



















