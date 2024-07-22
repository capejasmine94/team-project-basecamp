package com.bulmeong.basecamp.user.controller;

import com.bulmeong.basecamp.user.dto.UserDto;
import com.bulmeong.basecamp.user.service.UserService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("common")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("basecampPublicPage")
    public String basecampPublicPage(HttpSession session,
                                     Model model) {
        UserDto sessionUserInfo = (UserDto) session.getAttribute("sessionUserInfo");
        model.addAttribute("sessionUserInfo", sessionUserInfo);

        return "common/basecampPublicPage";
    }

    @GetMapping("basecampLoginPage")
    public String basecampLoginPage() {

        return "common/basecampLoginPage";
    }

    @GetMapping("basecampMyInfoPage")
    public String basecampMyInfoPage() {

        return "common/basecampMyInfoPage";
    }

    @GetMapping("basecampSignPage")
    public String basecampSignPage() {

        return "common/basecampSignPage";
    }

    @PostMapping("signProcess")
    public String signProcess(@ModelAttribute UserDto userDto) {
        userService.insertUser(userDto);
        return "redirect:/common/basecampLoginPage";
    }

    @PostMapping("loginProcess")
    public String loginProcess(HttpSession session,
                               @ModelAttribute UserDto userDto) {

        UserDto sessionUserInfo = userService.getUserByAccountAndPassword(userDto);
        if (sessionUserInfo == null) {
            return "common/basecampLoginFailPage";
        } else {
            session.setAttribute("sessionUserInfo", sessionUserInfo);
            // 프로젝트 끝나면 지워
            String redirectAfterLogin = (String) session.getAttribute("redirectAfterLogin");
            if (redirectAfterLogin != null) {
                session.removeAttribute("redirectAfterLogin"); // 사용 후 세션에서 제거
                return "redirect:" + redirectAfterLogin;
            }

            return "redirect:/common/basecampPublicPage";
        }
    }

    @GetMapping("logoutProcess")
    public String logoutProcess(HttpSession session) {
        session.invalidate();
        return "redirect:/common/basecampLoginPage";
    }
}
