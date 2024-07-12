package com.bulmeong.basecamp.insta.controller;

import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.boot.autoconfigure.ssl.SslProperties.Bundles.Watch.File;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.bulmeong.basecamp.common.util.ImageUtil;
import com.bulmeong.basecamp.common.util.Utils;
import com.bulmeong.basecamp.insta.dto.InstaUserInfoDto;
import com.bulmeong.basecamp.insta.service.InstaService;

@Controller
@RequestMapping("insta")
public class InstaController {
    @Autowired
    private Utils util;
    @Autowired
    private InstaService instaService;

    // 고객이 인스타 최초 접속시 나타나는 Page
    @RequestMapping("confirmPage")
    public String confirmPage(){
        util.loginUser();

        return "insta/confirmPage";
    }

    @RequestMapping("confirmProcess") // MultipartFile = 한개의 파일만 받을 때 / MultipartFile[] = 여러개의 파일만 받을 때
    public String confirmProcess(InstaUserInfoDto instaUserInfoDto, @RequestParam("insta_img") MultipartFile insta_img){

        instaUserInfoDto.setInsta_profile_img(ImageUtil.saveImageAndReturnLocation(insta_img));

        System.out.println(instaUserInfoDto.getInsta_profile_img());

        instaService.instaUserInfo(instaUserInfoDto);
        
        // return "insta/confirmProcess";
        return "redirect:./mainPage";
    }

    @RequestMapping("mainPage")
    public String mainPage(){

        return "insta/mainPage";
    }

    @RequestMapping("instaWritePage")
    public String instaWritePage(){

        return "insta/instaWritePage";
    }

    @RequestMapping("instaWriteProcess")
    public String instaWriteProcess(){

        return "insta/instaWriteProcess";
    }

}
















