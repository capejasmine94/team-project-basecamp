package com.bulmeong.basecamp.store.controller;

import java.util.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.bulmeong.basecamp.store.service.StoreService;


@Controller
@RequestMapping("store")
public class StoreController {

    @Autowired
    private StoreService storeService;

    @RequestMapping("")
    public String main(Model model){
        model.addAttribute("allProductDataList", storeService.getAllProductDataList());

        return "store/mStoreMain";
    }

    @RequestMapping("category")
    public String category(@RequestParam("category_id") int category_id, Model model){
        
        Map<String, Object> productCategoryData = storeService.getProductCategoryDataByCategoryId(category_id);
        model.addAttribute("productCategoryData", productCategoryData);

        return "store/mCategory";
    }

    @RequestMapping("productDetails")
    public String productDetails(@RequestParam("id") int id, Model model){

        Map<String, Object> productData = storeService.getProductDataByProductId(id);
        model.addAttribute("productData", productData);

        return "store/mProductDetails";
    }

    @RequestMapping("cart")
    public String cart(){
        return "store/mCart";
    }

    @RequestMapping("ordersheet")
    public String ordersheet(){
        return "store/mOrdersheet";
    }

    @RequestMapping("orderComplete")
    public String orderComplete(){
        return "store/mOrderComplete";
    }

    @RequestMapping("login")
    public String login(){
        return "store/tempUserLogin";
    }

}
