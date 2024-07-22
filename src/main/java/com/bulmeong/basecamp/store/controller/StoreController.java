package com.bulmeong.basecamp.store.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;


@Controller
@RequestMapping("store")
public class StoreController {

    @RequestMapping("")
    public String main(){
        return "store/mStoreMain";
    }

    @RequestMapping("category")
    public String category(){
        return "store/mCategory";
    }

    @RequestMapping("productDetails")
    public String productDetails(){
        return "store/mProductDetails";
    }

    @RequestMapping("cart")
    public String cart(){
        return "store/mCart";
    }

    @RequestMapping("login")
    public String login(){
        return "store/tempUserLogin";
    }

}
