package com.bulmeong.basecamp.store.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("storeCenter")
public class StoreController {

    @RequestMapping("storeRegister")
    public String storeRegister(){
        return "/store/storeRegisterPage";
    }

    @RequestMapping("registerComplete")
    public String registerComplete(){
        return "/store/registerComplete";
    }

    @RequestMapping("dashboard")
    public String dashboard(){
        return "/store/dashboard";
    }

    @RequestMapping("productRegister")
    public String productRegister(){
        return "/store/productRegister";
    }
    
    @RequestMapping("sellerInfo")
    public String sellerInfo(){
        return "/store/sellerInfo";
    }
    
//////////////////////////////////////////////////////////////////////////

    @RequestMapping("productManage")
    public String productManage(){
        return "/store/XproductManage";
    }

    @RequestMapping("deliveryInfo")
    public String deliveryInfo(){
        return "/store/XdeliveryInfo";
    }

    @RequestMapping("orderIntegration")
    public String orderIntegration(){
        return "/store/XorderIntegration";
    }

    @RequestMapping("sendProcessing")
    public String sendProcessing(){
        return "/store/XsendProcessing";
    }

    @RequestMapping("cancelManage")
    public String cancelManage(){
        return "/store/XcancelManage";
    }

    @RequestMapping("returnManage")
    public String returnManage(){
        return "/store/XreturnManage";
    }

    @RequestMapping("manageQnA")
    public String manageQnA(){
        return "/store/XmanageQnA";
    }

    @RequestMapping("manageReview")
    public String manageReview(){
        return "/store/XmanageReview";
    }

    @RequestMapping("balanceAccountsList")
    public String balanceAccountsList(){
        return "/store/XbalanceAccountsList";
    }

    @RequestMapping("statistics")
    public String statistics(){
        return "/store/Xstatistics";
    }

    @RequestMapping("storeInfo")
    public String storeInfo(){
        return "/store/XstoreInfo";
    }
}
