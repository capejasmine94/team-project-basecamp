package com.bulmeong.basecamp.store.controller;

import java.util.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.bulmeong.basecamp.store.dto.StoreOrderDto;
import com.bulmeong.basecamp.store.dto.UserDeliveryInfoDto;
import com.bulmeong.basecamp.store.service.StoreService;
import com.bulmeong.basecamp.user.dto.UserDto;

import jakarta.servlet.http.HttpSession;


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

        // return "store/mProductDetails";
        return "store/newProductDetails";
    }

    @RequestMapping("cart")
    public String cart(){

        return "store/mCart";
    }

    @RequestMapping("ordersheet")
    public String ordersheet(HttpSession session, Model model){

        UserDto userDto = (UserDto)session.getAttribute("sessionUserInfo");
        int user_id = userDto.getId();

        List<Map<String, Object>> pendingOrderProductInfoDataList = storeService.getPendingOrderDataList(user_id);

        model.addAttribute("pendingOrderProductInfoDataList", pendingOrderProductInfoDataList);

        UserDeliveryInfoDto userDeliveryInfoDto = storeService.selectDefaultAddressByUserId(user_id);
        model.addAttribute("userDeliveryInfoDto", userDeliveryInfoDto);        

        return "store/mOrdersheet(Npay)";
    }

    @RequestMapping("orderComplete")
    public String orderComplete(@RequestParam("id") int id, HttpSession session, Model model){
        UserDto userDto = (UserDto)session.getAttribute("sessionUserInfo");

        StoreOrderDto storeOrderDto = storeService.getStoreOrderDtoById(id);
        
        if(userDto==null||storeOrderDto.getUser_id()!=userDto.getId()){
            return "redirect:/store";
        }else{
            model.addAttribute("orderData", storeService.getOrderDataByOrderId(id));
            return "store/mOrderComplete";
        }
    }

    @RequestMapping("my")
    public String my(HttpSession session){
        UserDto userDto = (UserDto)session.getAttribute("sessionUserInfo");

        if(userDto == null){
            return "redirect:/user/login";
        }else{
            return "store/mMy";
        }
    }

    @RequestMapping("myOrderList")
    public String myOrderList(HttpSession session, Model model){
        UserDto userDto = (UserDto)session.getAttribute("sessionUserInfo");

        if(userDto==null){
            return "redirect:/user/login";
        }else{
            int user_id = userDto.getId();
            Map<String, Object> map = storeService.getOrderStatusCountData(user_id);
            model.addAttribute("orderStatusCountData", map);
        }

        return "store/mMyOrderList";
    }

    @RequestMapping("orderView")
    public String orderView(@RequestParam("id") int id, Model model){

        Map<String, Object> orderData = storeService.getStoreOrderDataListByOrderId(id);
        model.addAttribute("orderData", orderData);

        return "store/mOrderView";
    }

    @RequestMapping("orderRefund")
    public String orderRefund(){

        return "store/mOrderRefund";
    }

    @RequestMapping("addressBook")
    public String addressBook(HttpSession session, Model model){

        UserDto userDto = (UserDto)session.getAttribute("sessionUserInfo");

        if(userDto==null){
            return "redirect:/user/login";
        }else{
            List<UserDeliveryInfoDto> userDeliveryInfoDtoList = storeService.getUserDeliveryInfoByUserId(userDto.getId());
            model.addAttribute("userDeliveryInfoDtoList", userDeliveryInfoDtoList);

            return "store/mAddressBook";
        }
    }

    @RequestMapping("deliveryForm")
    public String deliveryForm(){

        return "store/mDeliveryForm";
    }

    @RequestMapping("writeReview")
    public String writeReview(@RequestParam("id")int id, Model model){
        //이 유저의 주문상품이 아니면 접근 불가...

        model.addAttribute("orderProductData", storeService.getOrderProductDataForReview(id));
        
        return "store/mWriteReview";
    }

    @RequestMapping("reviewComplete")
    public String reviewComplete(){

        return "store/mReviewComplete";
    }

    @RequestMapping("myReview")
    public String myReview(HttpSession session, Model model){
        UserDto userDto = (UserDto)session.getAttribute("sessionUserInfo");

        model.addAttribute("orderStatusCountData", storeService.getOrderStatusCountData(userDto.getId()));

        return "store/mMyReview";
    }

    // @RequestMapping("tempLogin")
    // public String tempLogin(){

    //     return "common/tempLogin";
    // }

}
