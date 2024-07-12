package com.bulmeong.basecamp.insta.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.boot.autoconfigure.ssl.SslProperties.Bundles.Watch.File;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.bulmeong.basecamp.club.dto.ClubPostImageDto;
import com.bulmeong.basecamp.common.dto.ImageDto;
import com.bulmeong.basecamp.common.util.ImageUtil;
import com.bulmeong.basecamp.common.util.Utils;
import com.bulmeong.basecamp.insta.dto.InstaArticleDto;
import com.bulmeong.basecamp.insta.dto.InstaArticleImgDto;
import com.bulmeong.basecamp.insta.dto.InstaUserInfoDto;
import com.bulmeong.basecamp.insta.service.InstaService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("insta")
public class InstaController {
    // @Autowired
    // private HttpServletRequest request;
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
        return "redirect:./mainPage?user_id=" + instaUserInfoDto.getUser_id();
    }

    @RequestMapping("mainPage")
    public String mainPage(HttpSession session, Model model , @RequestParam("user_id") int user_id){
        InstaUserInfoDto instaUserInfoDto = instaService.userInfoByUserId(user_id);
        model.addAttribute("instaUserInfoDto", instaUserInfoDto);

        // System.out.println(instaUserInfoDto.getInsta_profile_img());
        // System.out.println(session);

        // session에 로그인한 유저 정보를 받아 instaInfoDto정보 추가
        session.setAttribute("sessionInstaUserInfo", instaUserInfoDto);
        // System.out.println(instaUserInfoDto);
        System.out.println("Session Info: " + session.getAttribute("sessionInstaUserInfo"));

        return "insta/mainPage";
    }

    @RequestMapping("instaWritePage")
    public String instaWritePage(){

        return "insta/instaWritePage";
    }

    @RequestMapping("instaWriteProcess")
    public String instaWriteProcess(InstaArticleDto instaArticleDto, @RequestParam("insta_article_img") MultipartFile[]insta_article_img){
        instaService.writeArticle(instaArticleDto);
        System.out.println("instaArticleDto" + instaArticleDto);
        // System.out.println("instaId" + instaArticleDto.getId());

        List<InstaArticleImgDto> instaAtricleImgDtoList = new ArrayList<>();
        List<ImageDto> instaImgList = ImageUtil.saveImageAndReturnDtoList(insta_article_img);
        for(ImageDto imageDto : instaImgList){
            InstaArticleImgDto instaArticleImgDto = new InstaArticleImgDto();
            instaArticleImgDto.setImg_link(imageDto.getLocation());
            instaArticleImgDto.setArticle_id(instaArticleDto.getId());
            instaAtricleImgDtoList.add(instaArticleImgDto);
        }

        instaService.writeArticleImg(instaAtricleImgDtoList);

        return "redirect:./mainPage?user_id=" + instaArticleDto.getUser_id();
    }

}
















