package com.bulmeong.basecamp.club.controller;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;


import com.bulmeong.basecamp.club.dto.ClubDto;
import com.bulmeong.basecamp.club.dto.ClubJoinConditionDto;
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
}
