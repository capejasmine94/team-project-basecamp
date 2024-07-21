package com.bulmeong.basecamp.club.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.bulmeong.basecamp.club.dto.ClubBookmarkDto;
import com.bulmeong.basecamp.club.dto.ClubDto;
import com.bulmeong.basecamp.club.dto.ClubJoinConditionDto;
import com.bulmeong.basecamp.club.dto.ClubMeetingDto;
import com.bulmeong.basecamp.club.dto.ClubMeetingMemberDto;
import com.bulmeong.basecamp.club.dto.ClubMemberDto;
import com.bulmeong.basecamp.club.dto.ClubPostCategoryDto;
import com.bulmeong.basecamp.club.dto.ClubPostCommentDto;
import com.bulmeong.basecamp.club.dto.ClubPostDto;
import com.bulmeong.basecamp.club.dto.ClubPostImageDto;
import com.bulmeong.basecamp.club.dto.ClubPostLikeDto;
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
        util.loginUser(4);

        UserDto userDto = (UserDto)session.getAttribute("sessionUserInfo");
        List<ClubRegionCategoryDto> regionCategoryDtoList = clubService.findRegionCategory();
        model.addAttribute("regionCategoryDtoList", regionCategoryDtoList);

        // 북마크한 소모임 목록
        List<Map<String, Object>> bookmarkedClubDataList = clubService.getBookmarkedClubDtoList(userDto.getId());
        model.addAttribute("bookmarkedClubDataList", bookmarkedClubDataList);

        //  내가 가입한 소모임 목록
        List<ClubDto> joinClubDtoList = clubService.findJoinClubDtoList(userDto.getId());
        List<ClubDto> limitedJoinClubDtoList = joinClubDtoList.stream()
        .limit(3)
        .collect(Collectors.toList());
        model.addAttribute("joinClubDtoList", limitedJoinClubDtoList);


        // 새로운 소모임 목록
        List<ClubDto> clubDtoList = clubService.findClubDtoList();
        List<ClubDto> limitedClubDtoList = clubDtoList.stream()
        .limit(3)
        .collect(Collectors.toList());
        model.addAttribute("clubDtoList", limitedClubDtoList);

        return "club/clubMainPage";
    }

    @RequestMapping("home")
    public String clubHome(@RequestParam("id") int id, Model model, HttpSession session){
        model.addAttribute("id", id);
        Map<String, Object>map = clubService.clubDetail(id);
        model.addAttribute("map", map);

        List<Map<String,Object>>  clubMeetingDataList =clubService.selectClubMeetingDtoList(id);
        model.addAttribute("clubMeetingDataList", clubMeetingDataList);


        ClubBookmarkDto clubBookmarkDto = new ClubBookmarkDto();
        UserDto userDto = (UserDto)session.getAttribute("sessionUserInfo");
        
        if(userDto != null){
            clubBookmarkDto.setUser_id(userDto.getId());
            clubBookmarkDto.setClub_id(id);
            int confirmBookmark = clubService.confirmBookmark(clubBookmarkDto);
            model.addAttribute("confirmBookmark", confirmBookmark);
        }

        int totalBookmark = clubService.countTotalBookmark(id);
        model.addAttribute("totalBookmark", totalBookmark);

        List<Map<String, Object>> clubMemberDataList = clubService.findClubMemerDataList(id);
        model.addAttribute("clubMemberDataList", clubMemberDataList);

        ClubMemberDto clubMemberDto = new ClubMemberDto();
        UserDto userDto2 = (UserDto)session.getAttribute("sessionUserInfo");

        if(userDto != null){
            clubMemberDto.setClub_id(id);
            clubMemberDto.setUser_id(userDto2.getId());
            int isMemberInClub = clubService.checkClubMembership(clubMemberDto);
            model.addAttribute("isMemberInClub", isMemberInClub);
        }

        return "club/clubHomePage";
    }

    @RequestMapping("createNewClub")
    public String createNewClub(Model model){
        // util.loginUser();

        List<ClubRegionCategoryDto> regionCategoryDtoList = clubService.findRegionCategory();
        model.addAttribute("regionCategoryDtoList", regionCategoryDtoList);
        
        return "club/createNewClubPage";
    }
    
    @RequestMapping("createNewClubProcess")
    public String createClubProcess(ClubDto clubDto, ClubJoinConditionDto clubJoinConditionDto, @RequestParam("main_img") MultipartFile main_img){
        clubDto.setMain_image(ImageUtil.saveImageAndReturnLocation(main_img));
        clubService.createNewClub(clubDto, clubJoinConditionDto);
        // util.loginUser();

        return "redirect:/club/main";
    }

    @RequestMapping("writePost")
    public String writePost(@RequestParam("id") int id, Model model){
        // util.loginUser();
        List<ClubPostCategoryDto> postCategoryDtoList = clubService.findPostCategory();
        model.addAttribute("id", id);
        model.addAttribute("postCategoryDtoList", postCategoryDtoList);
        return "club/writePostPage";
    }

    @RequestMapping("writePostProcess")
    public String writePostProcess(ClubPostDto clubPostDto, @RequestParam("main_image") MultipartFile[]main_image){
    

        List<ImageDto> imgList = ImageUtil.saveImageAndReturnDtoList(main_image);
        if(imgList == null || imgList.isEmpty()){
            imgList = new ArrayList<>();
        }

        clubService.writeClubPost(clubPostDto, imgList);

        return "redirect:/club/board?id=" + clubPostDto.getClub_id();
    }

    // 소모임 회원가입
    @RequestMapping("joinClub")
    public String joinClub(@RequestParam("id") int id, Model model){
        // util.loginUser();
        model.addAttribute("id", id);
        return "club/joinClubPage";
    }

    @RequestMapping("joinClubProcess")
    public String joinClubProcess(ClubMemberDto clubMemberDto, Model model){
        // util.loginUser();
        clubService.joinClub(clubMemberDto);

        return "redirect:/club/main";
    }

    @RequestMapping("board")
    public String clubBoard(@RequestParam("id") int id, Model model){
        model.addAttribute("id", id);
        
        List<Map<String,Object>> postDetailList = clubService.getClubPostDtoList(id);
        model.addAttribute("postDetailList", postDetailList);

        return "club/clubBoardPage";
    }

    @RequestMapping("album")
    public String clubAlbum(@RequestParam("id") int id, Model model){
        model.addAttribute("id", id);
        List<ClubPostImageDto> postImageDtoList = clubService.selectPostImageDtoByPostId(id);
        model.addAttribute("postImageDtoList", postImageDtoList);


        return "club/clubAlbumPage";
    }

    @RequestMapping("readPost")
    public String readPost(@RequestParam("id") int id, Model model, HttpSession session){
        model.addAttribute("id", id);
        Map<String, Object> map = clubService.getClubPostData(id);
        model.addAttribute("map", map);
        List<Map<String, Object>> postCommentDetailList = clubService.getPostCommentDetailList(id);
        clubService.increaseReadCount(id);

       List<ClubPostImageDto> clubPostImageDtoList = clubService.getPostImageDtoListById(id);
       model.addAttribute("clubPostImageDtoList", clubPostImageDtoList);


        int totalReadCount = clubService.totalReadCount(id);
        model.addAttribute("totalReadCount", totalReadCount);


        model.addAttribute("postCommentDetailList", postCommentDetailList);

        ClubPostLikeDto clubPostLikeDto = new ClubPostLikeDto();
        UserDto userDto = (UserDto)session.getAttribute("sessionUserInfo");
        if(userDto != null){
            clubPostLikeDto.setUser_id(userDto.getId());
            clubPostLikeDto.setPost_id(id);
            int confirmPostLike = clubService.confirmPostLike(clubPostLikeDto);
            model.addAttribute("confirmPostLike", confirmPostLike);
        }


        return "club/readPostPage";
    }

    @RequestMapping("writeCommentProcess")
    public String writeComment(ClubPostCommentDto clubPostCommentDto){
        clubService.writeClubPostComment(clubPostCommentDto);
    
        return "redirect:/club/readPost?id="+ clubPostCommentDto.getPost_id();
    }

    @RequestMapping("newClubs")
    public String newClubs(Model model){
        List<ClubDto> clubDtoList = clubService.findClubDtoList();
        model.addAttribute("clubDtoList", clubDtoList);

        return "club/newClubListPage";
    }


    @RequestMapping("bookmarkClubs")
    public String localClubs(HttpSession session, Model model){
        UserDto userDto = (UserDto)session.getAttribute("sessionUserInfo");
        List<Map<String, Object>> bookmarkedClubDataList = clubService.getBookmarkedClubDtoList(userDto.getId());
        model.addAttribute("bookmarkedClubDataList", bookmarkedClubDataList);

        return "club/bookmarkClubListPage";
    }

    @RequestMapping("myClubs")
    public String myClubs(HttpSession session, Model model){
        List<Map<String,Object>> myClubsDataList = new ArrayList<>();
        UserDto userDto = (UserDto)session.getAttribute("sessionUserInfo");
        List<ClubDto> joinClubDtoList = clubService.findJoinClubDtoList(userDto.getId());
        
        for(ClubDto clubDto : joinClubDtoList){
            Map<String, Object> myClubListMap = new HashMap<>();
           int region_id = clubDto.getRegion_id();
           ClubRegionCategoryDto clubRegionCategoryDto = clubService.findRegionCategoryDtoById(region_id);
            myClubListMap.put("clubRegionCategoryDto", clubRegionCategoryDto);
            myClubListMap.put("clubDto", clubDto);
            myClubsDataList.add(myClubListMap);
        }
        model.addAttribute("myClubsDataList", myClubsDataList);
        model.addAttribute("userDto", userDto);
        

        return "club/myClubListPage";
    }

    @RequestMapping("bookmarkProcess")
    public String bookmarkProcess(ClubBookmarkDto clubBookmarkDto){
        int bookmarkCount = clubService.confirmBookmark(clubBookmarkDto);

        if(bookmarkCount == 0){
            clubService.insertBookmark(clubBookmarkDto);
        }else{
            clubService.delteBookmarkDto(clubBookmarkDto);
        }
        return "redirect:/club/home?id="+ clubBookmarkDto.getClub_id();
    }

    @RequestMapping("postLikeProcess")
    public String postLikeProcess(ClubPostLikeDto clubPostLikeDto){

        System.out.println(clubPostLikeDto);
        int postLikeCount = clubService.confirmPostLike(clubPostLikeDto);
        System.out.println(postLikeCount);

        if(postLikeCount == 0){
            clubService.insertPostLike(clubPostLikeDto);
        }else{
            clubService.deletePostLike(clubPostLikeDto);
        }
        
        return "redirect:/club/readPost?id=" + clubPostLikeDto.getPost_id();
        
    }



//  정모 개설하기
    @RequestMapping("createNewMeeting")
    public String createNewMeeting(@RequestParam("id") int id, Model model){
        model.addAttribute("clubId", id);
        return "club/createNewMeetingPage";
    }

    @RequestMapping("createNewMeetingProcess")
    public String createNewMeetingProcess(ClubMeetingDto clubMeetingDto,
                                          Model model,
                                          @RequestParam("main_img")
                                          MultipartFile main_img){
        String mainImageUrl = ImageUtil.saveImageAndReturnLocation(main_img);
        clubMeetingDto.setMain_image(mainImageUrl);
        clubService.insertClubMeetingDto(clubMeetingDto);

        ClubMeetingMemberDto meetingMemberDto = new ClubMeetingMemberDto();
        meetingMemberDto.setMeeting_id(clubMeetingDto.getId());
        meetingMemberDto.setUser_id(clubMeetingDto.getUser_id());

        clubService.insertClubMeetingMemberDto(meetingMemberDto);

        ClubDto clubDto = clubService.selectClubDtoById(clubMeetingDto.getClub_id());
        model.addAttribute("clubDto", clubDto);
        return "redirect:/club/home?id="+clubMeetingDto.getClub_id();
    }

    // 정모 회원가입

    // public String joinMeetingProcess(ClubMeetingMemberDto clubMeetingMemberDto){
    //     clubService.insertClubMeetingMemberDto(clubMeetingMemberDto);
        

    //     return "redirect:/club/home?id=";
    // }
    

   


}


