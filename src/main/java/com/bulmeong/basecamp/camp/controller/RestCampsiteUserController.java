package com.bulmeong.basecamp.camp.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.bulmeong.basecamp.camp.service.CampsiteService;
import com.bulmeong.basecamp.common.util.Utils;

@RestController

@RequestMapping("api/camp/")
public class RestCampsiteUserController {
    @Autowired
    private CampsiteService campsiteService;
    @Autowired
    private Utils utils;
}
