//package com.bulmeong.basecamp.secondHandProduct.controller;
//
//import com.bulmeong.basecamp.common.dto.RestResponseDto;
//import com.bulmeong.basecamp.secondHandProduct.dto.LocationDto;
//import com.bulmeong.basecamp.secondHandProduct.service.LocationService;
//import com.bulmeong.basecamp.user.dto.UserDto;
//import jakarta.servlet.http.HttpSession;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.web.bind.annotation.*;
//
//@RestController
//@RequestMapping("api/location")
//public class RestLocationController {
//
//    @Autowired
//    private LocationService locationService;
//
//    @PostMapping("saveLocation")
//    public RestResponseDto saveLocation(@RequestBody LocationDto locationDto) {
//
//        RestResponseDto restResponseDto = new RestResponseDto();
//        restResponseDto.setResult("success");
//
////        locationService.myLocation(locationDto);
//
//        return restResponseDto;
//    }
//
//
//    @GetMapping("loadLocation")
//    public RestResponseDto loadLocation(HttpSession session) {
//
//        RestResponseDto restResponseDto = new RestResponseDto();
//        restResponseDto.setResult("success");
//
//        UserDto userDto = (UserDto) session.getAttribute("sessionUserInfo");
//
////        LocationDto locationDto = locationService.selectMyLocation(userDto.getId());
////        restResponseDto.add("location", locationDto);
//
//        return restResponseDto;
//    }
//}