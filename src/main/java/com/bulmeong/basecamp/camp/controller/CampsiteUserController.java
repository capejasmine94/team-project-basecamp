package com.bulmeong.basecamp.camp.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import com.bulmeong.basecamp.camp.service.CampsiteService;
import com.bulmeong.basecamp.common.util.Utils;

@Controller
@RequestMapping("camp")
public class CampsiteUserController {
    @Autowired
    private CampsiteService campsiteService;
    @Autowired
    private Utils utils;
    

}
