package com.bulmeong.basecamp.camp.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.bulmeong.basecamp.camp.dto.CampsiteAreaDto;
import com.bulmeong.basecamp.camp.dto.CampsiteDto;
import com.bulmeong.basecamp.camp.service.CampsiteService;
import com.bulmeong.basecamp.common.dto.RestResponseDto;
import com.bulmeong.basecamp.common.util.Utils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;



@RestController
@RequestMapping("/api/camp/")
public class RestCampsiteController {
    @Autowired
    private CampsiteService campsiteService;
    @Autowired
    private Utils utils;

    @RequestMapping("isExistAccount")
    public RestResponseDto isExistAccount(@RequestParam("account") String account) {
        RestResponseDto restResponseDto = new RestResponseDto();
        restResponseDto.setResult("success");
        boolean isExistAccount = campsiteService.getCampsiteDtoByAccount(account) != null;
        restResponseDto.add("isExistAccount", isExistAccount);
        return restResponseDto;
    }

    
    @RequestMapping("isExistCampName")
    public RestResponseDto isExistCampName(@RequestParam("name") String name) {
        RestResponseDto restResponseDto = new RestResponseDto();
        restResponseDto.setResult("success");
        boolean isExistCampName = campsiteService.getCampsiteDtoByName(name) != null;
        restResponseDto.add("isExistCampName", isExistCampName);
        return restResponseDto;
    }

    @RequestMapping("checkNeedLogin")
    public RestResponseDto checkNeedLogin() {
        RestResponseDto restResponseDto = new RestResponseDto();
        restResponseDto.setResult("success");
        restResponseDto.add("needLogin", utils.getSession("campsite") == null);
        return restResponseDto;
    }
    
    @RequestMapping("getCampsite")
    public RestResponseDto getCampsite() {
        RestResponseDto result = new RestResponseDto();
        result.setResult("success");
        CampsiteDto campsiteDto = utils.getSession("campsite");
        result.add("cmapsite", campsiteDto);
        return result;
    }
        
    @RequestMapping("getAreaInfoList")
    public RestResponseDto getAreaInfoList() {
        RestResponseDto result = new RestResponseDto();
        result.setResult("success");
        CampsiteDto campsiteDto = utils.getSession("campsite");
        result.add("areaList", campsiteService.getAreaList(campsiteDto.getId()));
        return result;
    }


    @RequestMapping("insertArea")
    public RestResponseDto insertArea(Model model) {
        RestResponseDto result = new RestResponseDto();
        result.setResult("success");
        CampsiteDto campsiteDto = utils.getSession("campsite");
        CampsiteAreaDto areaDto = new CampsiteAreaDto();
        areaDto.setCampsite_id(campsiteDto.getId());
        campsiteService.insertArea(areaDto);
        areaDto.setName("구역 " + areaDto.getId());
        campsiteService.createNameForArea(areaDto);
        result.add("selectedArea", campsiteService.getAreaInfo(areaDto.getId()));
        return result;
    }

    @RequestMapping("insertPoint")
    public RestResponseDto insertPoint(Model model, @RequestParam("id") String id) {
        RestResponseDto result = new RestResponseDto();
        result.setResult("success");
        int area_id = Integer.parseInt(id);
        campsiteService.insertPoint(area_id);
        result.add("getPointList", campsiteService.getPointList(area_id));
        return result;
    }

    @RequestMapping("deletePoint")
    public RestResponseDto deletePoint(@RequestParam("id") String id, @RequestParam("area") String area_id) {
        RestResponseDto result = new RestResponseDto();
        result.setResult("success");
        campsiteService.deletePoint(Integer.parseInt(id));
        int area = Integer.parseInt(area_id);
        result.add("getPointList", campsiteService.getPointList(area));
        return result;
    }
    

    @RequestMapping("updateArea")
    public RestResponseDto updateArea(
        CampsiteAreaDto areaDto,
        @RequestParam("area_category") String[] categories, 
        @RequestParam("mainImage") MultipartFile[] mainImage, 
        @RequestParam("mapImage") MultipartFile mapImage
    ) {
        RestResponseDto result = new RestResponseDto();
        result.setResult("success");
        campsiteService.updateArea(areaDto, categories,mainImage,mapImage);
        return result;
    }
    
    
}
