package com.bulmeong.basecamp.campingcar.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.bulmeong.basecamp.campingcar.dto.BasicFacilitiesDto;
import com.bulmeong.basecamp.campingcar.dto.ProductDetailImgDto;
import com.bulmeong.basecamp.campingcar.service.CampingcarService;
import com.bulmeong.basecamp.common.util.Utils;

@Controller
@RequestMapping("campingcar")
public class CampingcarController {

    @Autowired
    private Utils utils;

    @Autowired
    private CampingcarService campingcarService;

    @RequestMapping("main")
    public String main(){
        utils.loginUser();

        return "campingcar/main";

    }

    @RequestMapping("campingCarDetailPage")
    public String campingCarDetailPage(@RequestParam("id") int id, Model model) {
        utils.loginUser();
        Map<String,Object> campingcarDetails = campingcarService.getCampingCarDetailByid(id);
        model.addAttribute("campingcarDetails", campingcarDetails);

        return "campingcar/campingCarDetailPage";
    }

    @RequestMapping("dRules")
    public String dRules(@RequestParam("id") int id, Model model) {
        utils.loginUser();
        Map<String,Object> campingcarDetails = campingcarService.getCampingCarDetailByid(id);
        model.addAttribute("campingcarDetails", campingcarDetails);
        return "campingcar/dRules";
    }

    @RequestMapping("dCarInfo")
    public String dCarInfo(@RequestParam("id") int id, Model model) {
        utils.loginUser();
        Map<String,Object> campingcarDetails = campingcarService.getCampingCarDetailByid(id);
        model.addAttribute("campingcarDetails", campingcarDetails);
        
        List<ProductDetailImgDto> detailImgDto = campingcarService.getProductDetailImgByProductId(id);
        model.addAttribute("detailImgDto", detailImgDto);
        return "campingcar/dCarInfo";
    }

    @RequestMapping("dCarOption")
    public String dCarOption(@RequestParam("id") int id, Model model) {
        utils.loginUser();
        Map<String,Object> campingcarDetails = campingcarService.getCampingCarDetailByid(id);
        model.addAttribute("campingcarDetails", campingcarDetails);

        List<BasicFacilitiesDto> facilities = campingcarService.getBasicFacilitiesByProductId(id);
        model.addAttribute("facilities", facilities);

        return "campingcar/dCarOption";
    }

    @RequestMapping("dRentalCondition")
    public String dRentalCondition(@RequestParam("id") int id, Model model) {
        utils.loginUser();
        Map<String,Object> campingcarDetails = campingcarService.getCampingCarDetailByid(id);
        model.addAttribute("campingcarDetails", campingcarDetails);

        return "campingcar/dRentalCondition";
    }

    @RequestMapping("dReviews") 
    public String dReviews(@RequestParam("id") int id, Model model) {
        utils.loginUser();
        Map<String,Object> campingcarDetails = campingcarService.getCampingCarDetailByid(id);
        model.addAttribute("campingcarDetails", campingcarDetails);
        return "campingcar/dReviews";
    }


    @RequestMapping("dCancelPolicy")
    public String dCancelPolicy(@RequestParam("id") int id, Model model) {
        utils.loginUser();
        Map<String,Object> campingcarDetails = campingcarService.getCampingCarDetailByid(id);
        model.addAttribute("campingcarDetails", campingcarDetails);
        return "campingcar/dCancelPolicy";
    }

    @RequestMapping("test")
    public String test() {

        return "campingcar/commontest";
    }
}
