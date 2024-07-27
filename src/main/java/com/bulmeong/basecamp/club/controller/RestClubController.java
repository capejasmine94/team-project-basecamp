package com.bulmeong.basecamp.club.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.bulmeong.basecamp.club.dto.ClubNestedCommentDto;
import com.bulmeong.basecamp.club.dto.ClubPostCommentLikeDto;
import com.bulmeong.basecamp.club.service.ClubService;
import com.bulmeong.basecamp.common.dto.RestResponseDto;
import com.bulmeong.basecamp.user.dto.UserDto;

import jakarta.servlet.http.HttpSession;

@RestController
@RequestMapping("api/club")
public class RestClubController {

    @Autowired
    private ClubService clubService;

    @GetMapping("comment")
    public RestResponseDto getComment (@RequestParam("post_id") int post_id){
        RestResponseDto restResponseDto = new RestResponseDto();

        List<Map<String, Object>> commentDataList = clubService.getPostCommentDetailList(post_id);
        restResponseDto.add("commentDataList", commentDataList);
        return restResponseDto;
    }

    @GetMapping("comment/like")
    public RestResponseDto confirmCommentLike (@RequestParam("comment_id") int comment_id, HttpSession session){
        RestResponseDto restResponseDto = new RestResponseDto();
        ClubPostCommentLikeDto newClubPostCommentLikeDto = new ClubPostCommentLikeDto();
        UserDto userDto = (UserDto)session.getAttribute("sessionUserInfo");
        newClubPostCommentLikeDto.setComment_id(comment_id);
        newClubPostCommentLikeDto.setUser_id(userDto.getId());

        return restResponseDto;
    }

    @DeleteMapping("comment/like")
    public RestResponseDto deleteCommentLike(@RequestParam("comment_id") int comment_id, HttpSession session){
        RestResponseDto restResponseDto = new RestResponseDto();
        UserDto userDto = (UserDto)session.getAttribute("sessionUserInfo");
        ClubPostCommentLikeDto clubPostCommentLikeDto = new ClubPostCommentLikeDto();
        clubPostCommentLikeDto.setComment_id(comment_id);
        clubPostCommentLikeDto.setUser_id(userDto.getId());
        clubService.deleteCommentLike(clubPostCommentLikeDto);

        return restResponseDto;
    }

    @PostMapping("comment/like")
    public RestResponseDto insertCommentLike(@RequestParam("comment_id") int comment_id, HttpSession session){
        RestResponseDto restResponseDto = new RestResponseDto();
        UserDto userDto = (UserDto)session.getAttribute("sessionUserInfo");
        ClubPostCommentLikeDto clubPostCommentLikeDto = new ClubPostCommentLikeDto();
        clubPostCommentLikeDto.setComment_id(comment_id);
        clubPostCommentLikeDto.setUser_id(userDto.getId());
        clubService.insertCommentLike(clubPostCommentLikeDto);

        return restResponseDto;
    }

    @PostMapping("nestedComment")
    public RestResponseDto writeNestedComment(@RequestBody ClubNestedCommentDto clubNestedCommentDto){
        RestResponseDto restResponseDto = new RestResponseDto();
        System.out.println(clubNestedCommentDto);

        clubService.writeNestedComment(clubNestedCommentDto);
        return restResponseDto;
    } 


    public RestResponseDto template(){
        RestResponseDto restResponseDto = new RestResponseDto();
        
        return restResponseDto;
    }

}
