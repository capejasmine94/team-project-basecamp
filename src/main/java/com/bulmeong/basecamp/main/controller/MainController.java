package com.bulmeong.basecamp.main.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.bulmeong.basecamp.user.dto.UserDto;

import jakarta.servlet.http.HttpSession;

@Controller
public class MainController {

    @GetMapping("")
    public String basecampPublicPage(HttpSession session,
                                     Model model) {
        UserDto sessionUserInfo = (UserDto) session.getAttribute("sessionUserInfo");
        model.addAttribute("sessionUserInfo", sessionUserInfo);

        return "common/basecampPublicPage";
    }

}
