package com.bulmeong.basecamp.insta.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.bulmeong.basecamp.insta.dto.InstaArticleCommentDto;
import com.bulmeong.basecamp.insta.dto.InstaRestResponseDto;
import com.bulmeong.basecamp.insta.service.InstaService;
import com.bulmeong.basecamp.user.dto.UserDto;

import jakarta.servlet.http.HttpSession;

@RestController
@RequestMapping("/api/insta")
public class RestInstaController {

    @Autowired
    private InstaService instaService;

    @RequestMapping("registerComment")
    public InstaRestResponseDto registerComment(InstaArticleCommentDto params, HttpSession session){
        InstaRestResponseDto instaRestResponseDto = new InstaRestResponseDto();
        instaRestResponseDto.setResult("success");

        UserDto userDto = (UserDto) session.getAttribute("sessionUserInfo");
        params.setUser_id(userDto.getId());

        instaService.registerComment(params);

        return instaRestResponseDto;
    }

    @RequestMapping("getCommentList")
    public InstaRestResponseDto getCommentList(int article_id){
        InstaRestResponseDto instaRestResponseDto = new InstaRestResponseDto();
        instaRestResponseDto.setResult("success");

        instaRestResponseDto.add("commentList", instaService.getCommentList(article_id));

        return instaRestResponseDto;
    }

    @RequestMapping("deleteComment")
    public InstaRestResponseDto deleteComment(int id){
        InstaRestResponseDto instaRestResponseDto = new InstaRestResponseDto();
        instaRestResponseDto.setResult("success");

        instaService.deleteComment(id);

        return instaRestResponseDto;
    }
}
