package com.bulmeong.basecamp.club.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.bulmeong.basecamp.club.service.ClubService;
import com.bulmeong.basecamp.common.dto.RestResponseDto;

@RestController
@RequestMapping("api/club")
public class RestClubController {

    @Autowired
    private ClubService clubService;

    @GetMapping("comment")
    public RestResponseDto getComment (@RequestParam("post_id") int post_id){
        RestResponseDto restResponseDto = new RestResponseDto();

        List<Map<String, Object>> commentDataList = clubService.getPostCommentDetailList(post_id);
        restResponseDto.add("commentDataList", commentDataList);
        return restResponseDto;
    }

}
