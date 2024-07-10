package com.bulmeong.basecamp.secondHandProduct.controller;

import com.bulmeong.basecamp.secondHandProduct.dto.UserDto;
import com.bulmeong.basecamp.secondHandProduct.service.ProductService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("secondhandProduct")
public class ProductController {

    @Autowired
    ProductService productService;

    @GetMapping("mainPage")
    public String mainPage() {

        return "/secondhandProduct/mainPage";
    }

    @GetMapping("productRegistrationPage")
    public String productRegistrationPage() {

        return "/secondhandProduct/productRegistrationPage";
    }

    @GetMapping("transactionListPage")
    public String transactionListPage() {

        return "/secondhandProduct/transactionListPage";
    }

    @GetMapping("neighborhoodSelectionPage")
    public String neighborhoodSelectionPage() {

        return "/secondhandProduct/neighborhoodSelectionPage";
    }

    @GetMapping("myPage")
    public String myPage() {

        return "/secondhandProduct/myPage";
    }

    @GetMapping("loginPage")
    public String loginPage() {

        return "/secondhandProduct/loginPage";
    }

    @GetMapping("loginProcess")
    public String loginProcess(HttpSession session,
                               @ModelAttribute UserDto userDto) {

        UserDto userInfo = productService.userTest(userDto);
        if (userInfo == null) {
            return "/secondhandProduct/loginPage";
        } else {
            session.setAttribute("userInfo", userInfo);
            return "redirect:/secondhandProduct/mainPage";
        }
    }
}
