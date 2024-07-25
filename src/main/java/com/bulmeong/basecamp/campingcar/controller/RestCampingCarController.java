package com.bulmeong.basecamp.campingcar.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.bulmeong.basecamp.campingcar.service.AdminService;

@RestController
@RequestMapping("api/campingcar")
public class RestCampingCarController {

    @Autowired 
    private AdminService adminService;



}
