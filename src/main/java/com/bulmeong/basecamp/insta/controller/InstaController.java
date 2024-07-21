package com.bulmeong.basecamp.insta.controller;

import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.boot.autoconfigure.ssl.SslProperties.Bundles.Watch.File;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.bulmeong.basecamp.common.dto.ImageDto;
import com.bulmeong.basecamp.common.util.ImageUtil;
import com.bulmeong.basecamp.common.util.Utils;
import com.bulmeong.basecamp.insta.dto.InstaArticleDto;
import com.bulmeong.basecamp.insta.dto.InstaArticleImgDto;
import com.bulmeong.basecamp.insta.dto.InstaArticleLikeDto;
import com.bulmeong.basecamp.insta.dto.InstaUserInfoDto;
import com.bulmeong.basecamp.insta.service.InstaService;
import com.bulmeong.basecamp.user.dto.UserDto;

import jakarta.servlet.http.HttpSession;

import java.util.*;

@Controller
@RequestMapping("insta")
public class InstaController {
    // @Autowired
    // private HttpServletRequest request;
    @Autowired
    private Utils util;
    @Autowired
    private InstaService instaService;

    // insta용 임시 loginPage
    @RequestMapping("instaLoginPage")
    public String instaLoginPage(){
        

        return "insta/instaLoginPage";
    }

    // insta 접속 할 때 두가지 접속 경로가 있음
    @RequestMapping("instaLoginProcess")
    public String instaLoginProcess(HttpSession session, @RequestParam("id") int id){
        util.loginUser(id);
        UserDto userDto = (UserDto) session.getAttribute("sessionUserInfo");
        System.out.println("userDto" + userDto);

        int userC = instaService.instaUserC(userDto);

        // System.out.println("userC" + userC);

        if(userC == 1){
            return "redirect:./instaMainPage?user_id=" + userDto.getId();
        }else{
            return "redirect:./instaConfirmPage";
        }

    }

    // 고객이 인스타 최초 접속시 나타나는 Page
    @RequestMapping("instaConfirmPage")
    public String instaConfirmPage(UserDto userDto){

        return "insta/instaConfirmPage";
    }

    

    @RequestMapping("confirmProcess") // MultipartFile = 한개의 파일만 받을 때 / MultipartFile[] = 여러개의 파일만 받을 때
    public String confirmProcess(InstaUserInfoDto instaUserInfoDto, @RequestParam("insta_img") MultipartFile insta_img){

        instaUserInfoDto.setInsta_profile_img(ImageUtil.saveImageAndReturnLocation(insta_img));

        System.out.println(instaUserInfoDto.getInsta_profile_img());

        instaService.instaUserInfo(instaUserInfoDto);
        
        // return "insta/confirmProcess";
        return "redirect:./instaMainPage?user_id=" + instaUserInfoDto.getUser_id();
    }

    // 인스타 메인페이지
    @RequestMapping("instaMainPage")
    public String instaMainPage(Model model, @RequestParam("user_id") int s_user_id){
        InstaUserInfoDto instaUserInfoDto = instaService.userInfoByUserId(s_user_id);
        model.addAttribute("instaUserInfoDto", instaUserInfoDto);

        // System.out.println(instaUserInfoDto.getInsta_profile_img());


        // session.setAttribute("instaUserInfoDto", instaUserInfoDto);
        // System.out.println("instaUserInfoDto" + instaUserInfoDto);

        InstaArticleLikeDto instaArticleLikeDto = new InstaArticleLikeDto();
        instaArticleLikeDto.setUser_id(s_user_id);

        List<Map<String, Object>> instaArticleListAll = instaService.selectInstaArticleList(s_user_id);
        model.addAttribute("instaArticleListAll", instaArticleListAll);

        // System.out.println(instaArticleListAll);

        return "insta/instaMainPage";
    }

    // 좋아요
    @RequestMapping("pushArticleLikeProcess")
    public String pushArticleLikeProcess(InstaArticleLikeDto instaArticleLikeDto){
        instaService.like(instaArticleLikeDto);

        return "redirect:./instaMainPage?user_id=" + instaArticleLikeDto.getUser_id();
    }

    @RequestMapping("pushArticleLikeDeleteProcess")
    public String pushArticleLikeDeleteProcess(InstaArticleLikeDto instaArticleLikeDto){
        instaService.unLike(instaArticleLikeDto);

        return "redirect:./instaMainPage?user_id=" + instaArticleLikeDto.getUser_id();
    }

    @RequestMapping("instaWritePage")
    public String instaWritePage(){

        return "insta/instaWritePage";
    }

    @RequestMapping("instaWriteProcess")
    public String instaWriteProcess(InstaArticleDto instaArticleDto, @RequestParam("insta") MultipartFile[]insta_article_img){
        instaService.writeArticle(instaArticleDto);
        System.out.println("instaArticleDto : " + instaArticleDto);
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

        // System.out.println("instaArticleDto : " + instaArticleDto);
        // System.out.println("instaAtricleImgDtoList : " + instaAtricleImgDtoList);

        return "redirect:./instaMainPage?user_id=" + instaArticleDto.getUser_id();
    }

    @RequestMapping("commentWritePage")
    public String commentWritePage(){

        return "insta/commentWritePage";
    }

    @RequestMapping("instaMyPage")
    public String instaMyPage(){

        return "insta/instaMyPage";
    }

    @RequestMapping("instaBookmarkPage")
    public String instaBookmarkPage(){

        return "insta/instaBookmarkPage";
    }
    
    @RequestMapping("instaUserPage")
    public String instaUserPage(){

        return "insta/instaUserPage";
    }
    
    @RequestMapping("instaUserDetailPage")
    public String instaUserDetailPage(){

        return "insta/instaUserDetailPage";
    }

    @RequestMapping("pushLikeUserPage")
    public String pushLikeUserPage(){

        return "insta/pushLikeUserPage";
    }

    @RequestMapping("userFollowersPage")
    public String userFollowersPage(){

        return "insta/userFollowersPage";
    }

    @RequestMapping("userFollowingPage")
    public String userFollowingPage(){

        return "insta/userFollowingPage";
    }
}













































