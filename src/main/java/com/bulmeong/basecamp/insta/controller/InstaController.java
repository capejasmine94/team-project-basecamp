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
import com.bulmeong.basecamp.insta.dto.InstaBookmarkDto;
import com.bulmeong.basecamp.insta.dto.InstaTagDto;
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
        instaArticleLikeDto.setUser_id(instaUserInfoDto.getId());

        // System.out.println(instaArticleListAll);
        List<Map<String, Object>> instaArticleListAll = instaService.selectInstaArticleList(instaUserInfoDto.getId());
        model.addAttribute("instaArticleListAll", instaArticleListAll);

        // 인스스 _ 로그인 유저가 팔로우 한 유저 출력
        List<InstaUserInfoDto> instaStoryList = instaService.likeInstaStoryButIsNotSoSad(instaUserInfoDto.getId());
        model.addAttribute("instaStoryList", instaStoryList);

        return "insta/instaMainPage";
    }

    // 좋아요
    @RequestMapping("pushArticleLikeProcess")
    public String pushArticleLikeProcess(HttpSession session, InstaArticleLikeDto instaArticleLikeDto){
        UserDto userDto = (UserDto) session.getAttribute("sessionUserInfo");

        instaService.like(instaArticleLikeDto);

        return "redirect:./instaMainPage?user_id=" + userDto.getId();
    }

    @RequestMapping("pushArticleLikeDeleteProcess")
    public String pushArticleLikeDeleteProcess(HttpSession session, InstaArticleLikeDto instaArticleLikeDto){
        UserDto userDto = (UserDto) session.getAttribute("sessionUserInfo");

        instaService.unLike(instaArticleLikeDto);

        return "redirect:./instaMainPage?user_id=" + userDto.getId();
    }

    @RequestMapping("instaWritePage")
    public String instaWritePage(Model model, @RequestParam("user_id") int id){
        InstaUserInfoDto instaUserInfoDto = instaService.selectUserInfo(id);
        model.addAttribute("instaUserInfoDto", instaUserInfoDto);

        return "insta/instaWritePage";
    }

    @RequestMapping("instaWriteProcess")
    public String instaWriteProcess(InstaArticleDto instaArticleDto, @RequestParam("text") String text,
                                    @RequestParam("insta") MultipartFile[]insta_article_img){

        instaService.writeArticle(instaArticleDto, text);
        // System.out.println("instaArticleDto : " + instaArticleDto);
        // System.out.println("instaId" + instaArticleDto.getId());

        InstaUserInfoDto instaUserInfoDto = instaService.selectUserInfo(instaArticleDto.getUser_id());

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

        return "redirect:./instaMainPage?user_id=" + instaUserInfoDto.getUser_id();
    }

    @RequestMapping("commentWritePage")
    public String commentWritePage(Model model, @RequestParam("user_id") int id){
        InstaUserInfoDto instaUserInfoDto = instaService.selectUserInfo(id);

        model.addAttribute("instaUserInfoDto", instaUserInfoDto);

        return "insta/commentWritePage";
    }

    @RequestMapping("instaMyPage")
    public String instaMyPage(Model model, @RequestParam("user_id") int id){ // id = userInfoDto id
        InstaUserInfoDto instaUserInfoDto = instaService.selectUserInfo(id);
        model.addAttribute("instaUserInfoDto", instaUserInfoDto);

        int articleCount = instaService.selectArticleCountByUserId(id);
        model.addAttribute("articleCount", articleCount);

        // 몇번 회원이 몇명을 팔로우 했는지
        int followerCount = instaService.followerCount(id);
        model.addAttribute("followerCount", followerCount);

        // 몇번 회원을 몇명이 팔로잉 했는지
        int followingCount = instaService.followingCount(id);
        model.addAttribute("followingCount", followingCount);

        // 유저가 작성한 게시글 이미지 출력
        List<InstaArticleImgDto> instaArticleImgDtoList = instaService.getInstaWriteArticleImgList(id); // id = user_id
        model.addAttribute("instaArticleImgDtoList", instaArticleImgDtoList);

        return "insta/instaMyPage";
    }

    @RequestMapping("instaBookmarkPage")
    public String instaBookmarkPage(Model model, @RequestParam("user_id") int id){
        InstaUserInfoDto instaUserInfoDto = instaService.selectUserInfo(id);
        model.addAttribute("instaUserInfoDto", instaUserInfoDto);

        int articleCount = instaService.selectArticleCountByUserId(id);
        model.addAttribute("articleCount", articleCount);

        // 몇번 회원이 몇명을 팔로우 했는지
        int followerCount = instaService.followerCount(id);
        model.addAttribute("followerCount", followerCount);

        // 몇번 회원을 몇명이 팔로잉 했는지
        int followingCount = instaService.followingCount(id);
        model.addAttribute("followingCount", followingCount);

        // 유저가 저장한 북마크 게시글 이미지List 출력
        List<InstaArticleImgDto> instaBookmarkArticleImgDtoList = instaService.getInstaSaveBookmarkImgList(id);
        model.addAttribute("instaBookmarkArticleImgDtoList", instaBookmarkArticleImgDtoList);

        return "insta/instaBookmarkPage";
    }

    @RequestMapping("pushBookmarkProcess")
    public String pushBookmarkProcess(HttpSession session, InstaBookmarkDto instaBookmarkDto){
        UserDto userDto = (UserDto) session.getAttribute("sessionUserInfo");

        instaService.insertBookmark(instaBookmarkDto);

        return "redirect:./instaMainPage?user_id=" + userDto.getId();
    }

    @RequestMapping("pushBookmarkDeleteProcess")
    public String pushBookmarkDeleteProcess(HttpSession session, InstaBookmarkDto instaBookmarkDto){
        UserDto userDto = (UserDto) session.getAttribute("sessionUserInfo");

        instaService.deleteBookmark(instaBookmarkDto);
        
        return "redirect:./instaMainPage?user_id=" + userDto.getId();
    }
    
    @RequestMapping("instaUserPage")
    public String instaUserPage(Model model, @RequestParam("user_id") int id){ // id = userInfoDto id
        InstaUserInfoDto instaUserInfoDto = instaService.selectUserInfo(id);

        int articleCount = instaService.selectArticleCountByUserId(id);
        model.addAttribute("articleCount", articleCount);

        // 몇번 회원이 몇명을 팔로우 했는지
        int followerCount = instaService.followerCount(id);
        model.addAttribute("followerCount", followerCount);

        model.addAttribute("instaUserInfoDto", instaUserInfoDto);

        // 유저가 작성한 게시글 이미지 출력
        List<InstaArticleImgDto> instaArticleImgDtoList = instaService.getInstaWriteArticleImgList(id); // id = user_id
        model.addAttribute("instaArticleImgDtoList", instaArticleImgDtoList);

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

    @RequestMapping("articleDeleteProcess")
    public String articleDeleteProcess(@RequestParam("article_id") int id, @RequestParam("user_id") int user_id){
        instaService.deleteArticle(id);

        return "redirect:./instaMainPage?user_id=" + user_id;
    }

    // 검색페이지
    @RequestMapping("instaSearchResultPage")
    public String instaSearchResultPage(Model model, @RequestParam("tag_id") int tag_id){
        List<InstaArticleImgDto> instaArticleImgDtoList = instaService.selectArticleFirstImg(tag_id);

        // 어떤 태그 검색했는지 태그 아이디 받아서 text 뽑는 쿼리
        InstaTagDto instaTagDto = instaService.selectTag(tag_id);
        model.addAttribute("instaTagDto", instaTagDto);

        model.addAttribute("instaArticleImgDtoList", instaArticleImgDtoList);

        return "insta/instaSearchResultPage";
    }
}




