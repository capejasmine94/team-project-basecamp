package com.bulmeong.basecamp.secondHandProduct.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("secondhandProduct")
public class ProductController {

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
}
