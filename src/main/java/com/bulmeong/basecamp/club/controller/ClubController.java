package com.bulmeong.basecamp.club.controller;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;


import com.bulmeong.basecamp.club.dto.ClubDto;
import com.bulmeong.basecamp.club.dto.ClubJoinConditionDto;
import com.bulmeong.basecamp.club.dto.ClubPostDto;
import com.bulmeong.basecamp.club.service.ClubService;
import com.bulmeong.basecamp.common.util.Utils;

import com.bulmeong.basecamp.user.service.UserService;

@Controller
@RequestMapping("club")
public class ClubController {

    @Autowired
    private Utils util;

    @Autowired UserService userService;

    @Autowired
    private ClubService clubService;

    @RequestMapping("main")
    public String clubMain(){

        return "club/clubMainPage";
    }

    @RequestMapping("createNewClub")
    public String createNewClub(){
        util.loginUser();
        return "/club/createNewClubPage";
    }

    @RequestMapping("createNewClubProcess")
    public String createClubProcess(ClubDto clubDto, ClubJoinConditionDto clubJoinConditionDto){
        clubService.createNewClub(clubDto, clubJoinConditionDto);
        util.loginUser();


        System.out.println("모임생성페이지" + clubDto);

        return "/club/clubMainPage";
    }

    @RequestMapping("writeNewPost")
    public String writeNewPost(){
        util.loginUser();
        return "/club/writeNewPostPage";
    }

    @RequestMapping("writeNewPostProcess")
    public String writeNewPostProcess(ClubPostDto clubPostDto){
        clubService.writeClubPost(clubPostDto);

        System.out.println("butter" +  clubPostDto);

        return "/club/clubMainPage";

    }


}


