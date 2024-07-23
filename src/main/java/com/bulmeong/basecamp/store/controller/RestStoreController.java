package com.bulmeong.basecamp.store.controller;

import java.util.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.bulmeong.basecamp.common.dto.RestResponseDto;
import com.bulmeong.basecamp.common.util.ImageUtil;
import com.bulmeong.basecamp.store.dto.AdditionalInfoDto;
import com.bulmeong.basecamp.store.dto.ProductOptionNameDto;
import com.bulmeong.basecamp.store.dto.StoreDto;
import com.bulmeong.basecamp.store.dto.StoreProductDto;
import com.bulmeong.basecamp.store.dto.StoreRestResponseDto;
import com.bulmeong.basecamp.store.service.StoreService;

import jakarta.servlet.http.HttpSession;

@RestController
@RequestMapping("/api/store")
public class RestStoreController {

    @Autowired
    private StoreService storeService;

    @RequestMapping("getSessionStoreId")
    public RestResponseDto getSessionStoreId(HttpSession session){
        RestResponseDto restResponseDto = new RestResponseDto();
        restResponseDto.setResult("success");

        StoreDto sessionStore = (StoreDto)session.getAttribute("sessionStoreInfo");
        if(sessionStore != null){
            restResponseDto.add("id", sessionStore.getId());
        }else{
            restResponseDto.add("id", null);
        }

        return restResponseDto;
    }

    @RequestMapping("isExistStoreAccountId")
    public StoreRestResponseDto isExistAccountId(@RequestParam("account_id") String account_id){
        StoreRestResponseDto restResponseDto = new StoreRestResponseDto();

        restResponseDto.add("isExistAccountID", storeService.isExistAccountId(account_id));

        return restResponseDto;
    }

    @RequestMapping("isExistStoreName")
    public StoreRestResponseDto isExistStoreName(@RequestParam("name") String name){
        StoreRestResponseDto restResponseDto = new StoreRestResponseDto();

        restResponseDto.add("isExistName", storeService.isExistStoreName(name));

        return restResponseDto;
    }
    
    @RequestMapping("getProductSubcategoryDto")
    public RestResponseDto getProductSubcategoryDto(@RequestParam("category_id") int category_id){
        RestResponseDto restResponseDto = new RestResponseDto();
        
        restResponseDto.add("subcategoryDtoList", storeService.getProductSubcategoryByCategoryId(category_id));

        return restResponseDto;
    }

    @RequestMapping("registerProduct")
    public RestResponseDto registerProduct(
        StoreProductDto storeProductDto,
        @RequestParam("uploadMainImage") MultipartFile uploadMainImage, 
        @RequestParam("percentage") double percentage,
        HttpSession session
    ){
        RestResponseDto restResponseDto = new RestResponseDto();

        StoreDto storeDto = (StoreDto)session.getAttribute("sessionStoreInfo");

        storeProductDto.setMain_image(ImageUtil.saveImageAndReturnLocation(uploadMainImage));
        storeProductDto.setStore_id(storeDto.getId());

        int product_id = storeService.registerProductAndReturnId(storeProductDto,percentage);

        restResponseDto.add("product_id", product_id);

        return restResponseDto;
    }

    @RequestMapping("registerProductOption")
    public RestResponseDto registerProductOption(
        @RequestParam("product_id") int product_id,
        @RequestParam("name") String name,
        @RequestParam("optionValues") String[] optionValues
    ){
        RestResponseDto restResponseDto = new RestResponseDto();
        
        ProductOptionNameDto productOptionNameDto = new ProductOptionNameDto();
        productOptionNameDto.setProduct_id(product_id);
        productOptionNameDto.setName(name);

        storeService.registerProductOptionNameAndValue(productOptionNameDto, optionValues);

        return restResponseDto;
    }

    @RequestMapping("registerAdditionalInfo")
    public RestResponseDto registerAdditionalInfo(
        @RequestParam("valueNames") String[] valueNames, 
        @RequestParam("additional_price") int additional_price, 
        @RequestParam("quantity") int quantity
    ){
        RestResponseDto restResponseDto = new RestResponseDto();

        AdditionalInfoDto additionalInfoDto = new AdditionalInfoDto();
        additionalInfoDto.setAdditional_price(additional_price);
        additionalInfoDto.setQuantity(quantity);

        storeService.registerProductAddtionalInfo(valueNames, additionalInfoDto);

        return restResponseDto;
    }

    @RequestMapping("getProductDataByCategoryId")
    public RestResponseDto getProductDataByCategoryId(
        @RequestParam("category_id") int category_id,
        @RequestParam(value="subcategory_id", required = false) Integer subcategory_id
    ){
        RestResponseDto restResponseDto = new RestResponseDto();

        List<Map<String, Object>> productDataList = storeService.getProductDataByCategoryId(category_id, subcategory_id);

        restResponseDto.add("productDataList", productDataList);

        return restResponseDto;
    }

    @RequestMapping("countProductByCategoryId")
    public RestResponseDto countProductByCategoryId(
        @RequestParam("category_id") int category_id,
        @RequestParam(value="subcategory_id", required = false) Integer subcategory_id
    ){
        RestResponseDto restResponseDto = new RestResponseDto();

        int productCount = storeService.countProductDtoByCategoryId(category_id, subcategory_id);
        restResponseDto.add("productCount", productCount);

        return restResponseDto;
    }

}
