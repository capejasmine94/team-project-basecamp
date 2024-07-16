package com.bulmeong.basecamp.store.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.bulmeong.basecamp.store.dto.StoreBankAccountDto;
import com.bulmeong.basecamp.store.dto.StoreDeliveryInfoDto;
import com.bulmeong.basecamp.store.dto.StoreDto;
import com.bulmeong.basecamp.store.service.StoreService;

import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("storeCenter")
public class StoreController {

    @Autowired
    private StoreService storeService;

    @RequestMapping("login")
    public String login(){
        return "/seller/login";
    }

    @RequestMapping("loginProcess")
    public String loginProcess(@RequestParam("account_id") String account_id, @RequestParam("account_pw") String account_pw, HttpSession session){
        
        StoreDto storeDto = storeService.getStoreDtoByAccountInfo(account_id, account_pw);
        session.setAttribute("sessionStoreInfo", storeDto);

        return "redirect:/storeCenter/dashboard";
    }

    @RequestMapping("logoutProcess")
    public String logoutProcess(HttpSession session){
        session.invalidate();

        return "redirect:/storeCenter/login";
    }

    @RequestMapping("storeRegister")
    public String storeRegister(){
        return "/store/storeRegisterPage";
    }

    @RequestMapping("storeRegisterProcess")
    public String storeRegisterProcess(StoreDto storeDto, StoreDeliveryInfoDto storeDeliveryInfoDto, StoreBankAccountDto storeBankAccountDto){

        storeService.registerStore(storeDto, storeDeliveryInfoDto, storeBankAccountDto);

        return "redirect:/storeCenter/registerComplete";
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
    public String sellerInfo(HttpSession session, Model model){

        StoreDto sessionStore = (StoreDto)session.getAttribute("sessionStoreInfo");

        if(sessionStore!=null){
            StoreBankAccountDto storeBankAccountDto = storeService.getStoreBankAccountDtoByStoreId(sessionStore.getId());
            model.addAttribute("storeBankAccountDto", storeBankAccountDto);

            return "/store/sellerInfo";
        }else{
            return "redirect:/storeCenter/login";
        }
        
    }

    @RequestMapping("deliveryInfo")
    public String deliveryInfo(){
        return "/store/deliveryInfo";
    }
    
//////////////////////////////////////////////////////////////////////////

    @RequestMapping("productManage")
    public String productManage(){
        return "/store/XproductManage";
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
