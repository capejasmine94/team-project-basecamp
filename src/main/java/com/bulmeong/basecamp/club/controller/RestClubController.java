package com.bulmeong.basecamp.club.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.bulmeong.basecamp.club.dto.ClubMeetingMemberDto;
import com.bulmeong.basecamp.club.dto.ClubMemberDto;
import com.bulmeong.basecamp.club.dto.ClubNestedCommentDto;
import com.bulmeong.basecamp.club.dto.ClubPostCommentLikeDto;
import com.bulmeong.basecamp.club.mapper.ClubSqlMapper;
import com.bulmeong.basecamp.club.service.ClubService;
import com.bulmeong.basecamp.common.dto.RestResponseDto;
import com.bulmeong.basecamp.user.dto.UserDto;

import jakarta.servlet.http.HttpSession;

@RestController
@RequestMapping("api/club")
public class RestClubController {

    @Autowired
    private ClubService clubService;

    @Autowired
    private ClubSqlMapper clubSqlMapper;

    @GetMapping("comment")
    public RestResponseDto getComment (@RequestParam("post_id") int post_id){
        RestResponseDto restResponseDto = new RestResponseDto();

        List<Map<String, Object>> commentDataList = clubService.getPostCommentDetailList(post_id);
        restResponseDto.add("commentDataList", commentDataList);
        return restResponseDto;
    }

    @GetMapping("comment/like")
    public RestResponseDto confirmCommentLike (@RequestParam("comment_id") int comment_id, HttpSession session){
        RestResponseDto restResponseDto = new RestResponseDto();
        ClubPostCommentLikeDto newClubPostCommentLikeDto = new ClubPostCommentLikeDto();
        UserDto userDto = (UserDto)session.getAttribute("sessionUserInfo");
        newClubPostCommentLikeDto.setComment_id(comment_id);
        newClubPostCommentLikeDto.setUser_id(userDto.getId());

        return restResponseDto;
    }

    @DeleteMapping("comment/like")
    public RestResponseDto deleteCommentLike(@RequestParam("comment_id") int comment_id, HttpSession session){
        RestResponseDto restResponseDto = new RestResponseDto();
        UserDto userDto = (UserDto)session.getAttribute("sessionUserInfo");
        ClubPostCommentLikeDto clubPostCommentLikeDto = new ClubPostCommentLikeDto();
        clubPostCommentLikeDto.setComment_id(comment_id);
        clubPostCommentLikeDto.setUser_id(userDto.getId());
        clubService.deleteCommentLike(clubPostCommentLikeDto);

        return restResponseDto;
    }

    @PostMapping("comment/like")
    public RestResponseDto insertCommentLike(@RequestParam("comment_id") int comment_id, HttpSession session){
        RestResponseDto restResponseDto = new RestResponseDto();
        UserDto userDto = (UserDto)session.getAttribute("sessionUserInfo");
        ClubPostCommentLikeDto clubPostCommentLikeDto = new ClubPostCommentLikeDto();
        clubPostCommentLikeDto.setComment_id(comment_id);
        clubPostCommentLikeDto.setUser_id(userDto.getId());
        clubService.insertCommentLike(clubPostCommentLikeDto);

        return restResponseDto;
    }

    @PostMapping("nestedComment")
    public RestResponseDto writeNestedComment(@RequestBody ClubNestedCommentDto clubNestedCommentDto, HttpSession session){
        RestResponseDto restResponseDto = new RestResponseDto();
        // System.out.println(clubNestedCommentDto);
        UserDto userDto = (UserDto)session.getAttribute("sessionUserInfo");
        ClubNestedCommentDto nestedCommentDto = new ClubNestedCommentDto();
        nestedCommentDto.setUser_id(userDto.getId());
        nestedCommentDto.setContent(clubNestedCommentDto.getContent());
        nestedCommentDto.setComment_id(clubNestedCommentDto.getComment_id());

        clubService.writeNestedComment(nestedCommentDto);
        return restResponseDto;
    } 

    @GetMapping("member")
    public RestResponseDto confirmJoinCondition(@RequestParam("club_id") int club_id, HttpSession session){
        RestResponseDto restResponseDto = new RestResponseDto();

        UserDto userDto = (UserDto)session.getAttribute("sessionUserInfo");
        boolean confirm = clubService.confirmUserCondition(club_id, userDto);
        restResponseDto.add("confirm", confirm);
        return restResponseDto;
    }

    @PostMapping("member")
    public RestResponseDto joinClubMember(@RequestParam("club_id")int id, HttpSession session){
        RestResponseDto restResponseDto = new RestResponseDto();
        UserDto userDto = (UserDto)session.getAttribute("sessionUserInfo");
        ClubMemberDto clubMemberDtoForRoleId3 = new ClubMemberDto();
        clubMemberDtoForRoleId3.setClub_id(id);
        clubMemberDtoForRoleId3.setUser_id(userDto.getId());
        clubMemberDtoForRoleId3.setRole_id(3);

        System.out.println(clubMemberDtoForRoleId3);
        clubService.joinClub(clubMemberDtoForRoleId3);

        return restResponseDto;
    }

    @GetMapping("meeting")
    public RestResponseDto getMeeting(@RequestParam("club_id") int id, HttpSession session){
        RestResponseDto restResponseDto = new RestResponseDto();
        
        UserDto userDto = (UserDto)session.getAttribute("sessionUserInfo");
        List<Map<String,Object>>  clubMeetingDataList =clubService.selectClubMeetingDtoList(id, userDto.getId());
        int totalMeetings = clubService.countTotalMeeting(id);
        
        ClubMemberDto clubMemberDto = new ClubMemberDto();
        clubMemberDto.setClub_id(id);
        clubMemberDto.setUser_id(userDto.getId());
        clubMemberDto.setRole_id(3);

        int isMemberInClub = clubSqlMapper.checkClubMembership(clubMemberDto);

        restResponseDto.add("clubMeetingDataList", clubMeetingDataList);
        restResponseDto.add("totalMeetings", totalMeetings);
        restResponseDto.add("isMemberInClub", isMemberInClub);


        return restResponseDto;
    }

    @PostMapping("meeting")
    public RestResponseDto joinMeeting(ClubMeetingMemberDto clubMeetingMemberDto, HttpSession session){
        RestResponseDto restResponseDto = new RestResponseDto();
        UserDto userDto = (UserDto)session.getAttribute("sessionUserInfo");
        clubMeetingMemberDto.setUser_id(userDto.getId());
        System.out.println(clubMeetingMemberDto);
        clubService.joinMeeting(clubMeetingMemberDto);

        return restResponseDto;
    }

    @DeleteMapping("meeting")
    public RestResponseDto declineMeeting(@RequestParam("meeting_id") int meeting_id, HttpSession session){
        UserDto userDto = (UserDto)session.getAttribute("sessionUserInfo");
        RestResponseDto restResponseDto = new RestResponseDto();
        clubService.declineMeeting(meeting_id, userDto.getId());
        
        return restResponseDto;
    }

    @PatchMapping("role")
    public RestResponseDto roleUpdate(ClubMemberDto clubMemberDto){
        RestResponseDto restResponseDto = new RestResponseDto();
        
        clubService.roleUpdate(clubMemberDto);
        // restResponseDto.add("result", "됐어유");5
        
        return restResponseDto;
    }
    @GetMapping("user")
    public RestResponseDto getUserData(@RequestParam("club_id") int club_id, @RequestParam("user_id") int user_id){
        RestResponseDto restResponseDto = new RestResponseDto();
        Map<String, Object> clubMemberData = clubService.findClubMemberData(club_id, user_id);
        restResponseDto.add("clubMemberData", clubMemberData);
        return restResponseDto;
    }

    @GetMapping("upcomingMeeting")
    public RestResponseDto getUpcommingMeetingData(@RequestParam("meeting_date") String meetind_date){
        RestResponseDto restResponseDto = new RestResponseDto();
        List<Map<String,Object>> upcomingMeetingList = clubService.getUpcomingMeetingData(meetind_date);

        restResponseDto.add("upcomingMeetingList", upcomingMeetingList);
        
        
        return restResponseDto;
    }

    

    public RestResponseDto template(){
        RestResponseDto restResponseDto = new RestResponseDto();
        
        return restResponseDto;
    }

    

   
}
