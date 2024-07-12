package com.bulmeong.basecamp.club.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.bulmeong.basecamp.club.dto.ClubDto;
import com.bulmeong.basecamp.club.dto.ClubJoinConditionDto;
import com.bulmeong.basecamp.club.dto.ClubMemberDto;
import com.bulmeong.basecamp.club.dto.ClubPostDto;
import com.bulmeong.basecamp.club.dto.ClubPostImageDto;
import com.bulmeong.basecamp.club.service.ClubService;
import com.bulmeong.basecamp.common.dto.ImageDto;
import com.bulmeong.basecamp.common.util.ImageUtil;
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

    @RequestMapping("home")
    public String clubHome(){

        return "club/clubHomePage";
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

    @RequestMapping("writePost")
    public String writePost(){
        util.loginUser();
        return "/club/writePostPage";
    }

    @RequestMapping("writePostProcess")
    public String writePostProcess(ClubPostDto clubPostDto, @RequestParam("main_image") MultipartFile[]main_image){

        List<ClubPostImageDto> clubPostImageDtoList = new ArrayList<>(); 
        List<ImageDto> imgList = ImageUtil.saveImageAndReturnDtoList(main_image);
        for(ImageDto img : imgList){
            ClubPostImageDto clubPostImageDto = new ClubPostImageDto();
            clubPostImageDto.setPost_img_location(img.getLocation());
            clubPostImageDto.setPost_id(clubPostDto.getId());
            clubPostImageDtoList.add(clubPostImageDto);
        }


        clubService.writeClubPost(clubPostDto, clubPostImageDtoList);

        return "/club/clubMainPage";
    }

    // 소모임 회원가입
    @RequestMapping("joinClub")
    public String joinClub(){
        util.loginUser();
        return "club/joinClubPage";
    }

    @RequestMapping("joinClubProcess")
    public String joinClubProcess(ClubMemberDto clubMemberDto){
        util.loginUser();
        clubService.joinClub(clubMemberDto);

        return "/club/main";
    }



}


