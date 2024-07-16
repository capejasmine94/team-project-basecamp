package com.bulmeong.basecamp.club.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.bulmeong.basecamp.club.dto.ClubDto;
import com.bulmeong.basecamp.club.dto.ClubJoinConditionDto;
import com.bulmeong.basecamp.club.dto.ClubMemberDto;
import com.bulmeong.basecamp.club.dto.ClubPostCategoryDto;
import com.bulmeong.basecamp.club.dto.ClubPostCommentDto;
import com.bulmeong.basecamp.club.dto.ClubPostDto;
import com.bulmeong.basecamp.club.dto.ClubRegionCategoryDto;
import com.bulmeong.basecamp.club.service.ClubService;
import com.bulmeong.basecamp.common.dto.ImageDto;
import com.bulmeong.basecamp.common.util.ImageUtil;
import com.bulmeong.basecamp.common.util.Utils;
import com.bulmeong.basecamp.user.dto.UserDto;
import com.bulmeong.basecamp.user.service.UserService;

import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("club")
public class ClubController {

    @Autowired
    private Utils util;

    @Autowired UserService userService;

    @Autowired
    private ClubService clubService;

    @RequestMapping("main")
    public String clubMain(HttpSession session, Model model){
        util.loginUser();

        UserDto userDto = (UserDto)session.getAttribute("sessionUserInfo");
        List<ClubRegionCategoryDto> regionCategoryDtoList = clubService.findRegionCategory();
        model.addAttribute("regionCategoryDtoList", regionCategoryDtoList);

        //  내가 가입한 소모임 목록
        List<ClubDto> joinClubDtoList = clubService.findJoinClubDtoList(userDto.getId());
        model.addAttribute("joinClubDtoList", joinClubDtoList);

        // 새로운 소모임 목록
        List<ClubDto> clubDtoList = clubService.findClubDtoList();
        model.addAttribute("clubDtoList", clubDtoList);

        return "club/clubMainPage";
    }

    @RequestMapping("home")
    public String clubHome(@RequestParam("id") int id, Model model){
        model.addAttribute("id", id);

        return "club/clubHomePage";
    }

    @RequestMapping("createNewClub")
    public String createNewClub(Model model){
        util.loginUser();
        
        List<ClubRegionCategoryDto> regionCategoryDtoList = clubService.findRegionCategory();
        model.addAttribute("regionCategoryDtoList", regionCategoryDtoList);
        
        return "/club/createNewClubPage";
    }
    
    @RequestMapping("createNewClubProcess")
    public String createClubProcess(ClubDto clubDto, ClubJoinConditionDto clubJoinConditionDto){
        clubService.createNewClub(clubDto, clubJoinConditionDto);
        util.loginUser();


        System.out.println("모임생성페이지" + clubDto);

        return "redirect:/club/main";
    }

    @RequestMapping("writePost")
    public String writePost(@RequestParam("id") int id, Model model){
        util.loginUser();
        List<ClubPostCategoryDto> postCategoryDtoList = clubService.findPostCategory();
        model.addAttribute("id", id);
        model.addAttribute("postCategoryDtoList", postCategoryDtoList);
        return "/club/writePostPage";
    }

    @RequestMapping("writePostProcess")
    public String writePostProcess(ClubPostDto clubPostDto, @RequestParam("main_image") MultipartFile[]main_image){
    
        List<ImageDto> imgList = ImageUtil.saveImageAndReturnDtoList(main_image);


        clubService.writeClubPost(clubPostDto, imgList);

        

        return "redirect:/club/board?id=" + clubPostDto.getClub_id();
    }

    // 소모임 회원가입
    @RequestMapping("joinClub")
    public String joinClub(@RequestParam("id") int id, Model model){
        util.loginUser();
        model.addAttribute("id", id);
        return "club/joinClubPage";
    }

    @RequestMapping("joinClubProcess")
    public String joinClubProcess(ClubMemberDto clubMemberDto, Model model){
        util.loginUser();
        clubService.joinClub(clubMemberDto);

        List<ClubDto> clubDtoList = clubService.findClubDtoList();
        model.addAttribute("clubDtoList", clubDtoList);

        return "redirect:/club/main";
    }

    @RequestMapping("board")
    public String clubBoard(@RequestParam("id") int id, Model model){
        model.addAttribute("id", id);
        
        List<Map<String,Object>> postDetailList = clubService.getClubPostDtoList(id);
        model.addAttribute("postDetailList", postDetailList);

        
        return "club/clubBoardPage";
    }

    // @RequestMapping("album")
    // public String clubAlbum(){

    //     return "club/clubAlbumPage";
    // }

    @RequestMapping("readPost")
    public String readPost(@RequestParam("id") int id, Model model, HttpSession session){
        model.addAttribute("id", id);
        Map<String, Object> map = clubService.getClubPostData(id);
        model.addAttribute("map", map);
        List<Map<String, Object>> postCommentDetailList = clubService.getPostCommentDetailList(id);
        model.addAttribute("postCommentDetailList", postCommentDetailList);

        return "/club/readPostPage";
    }

    @RequestMapping("writeCommentProcess")
    public String writeComment(ClubPostCommentDto clubPostCommentDto){
        clubService.writeClubPostComment(clubPostCommentDto);
        
        
            
        return "redirect:/club/readPost?id="+ clubPostCommentDto.getPost_id();
    }

    @RequestMapping("newClubs")
    public String newClubs(){

        return "/club/newClubListPage";
    }


    @RequestMapping("localClubs")
    public String localClubs(){

        return "/club/localClubListPage";
    }

    @RequestMapping("myClubs")
    public String myClubs(){

        return "/club/myClubListPage";
    }
}


