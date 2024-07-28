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
import com.bulmeong.basecamp.store.dto.CartProductDto;
import com.bulmeong.basecamp.store.dto.ProductOptionNameDto;
import com.bulmeong.basecamp.store.dto.StoreDto;
import com.bulmeong.basecamp.store.dto.StoreProductDto;
import com.bulmeong.basecamp.store.dto.StoreRestResponseDto;
import com.bulmeong.basecamp.store.service.StoreService;
import com.bulmeong.basecamp.user.dto.UserDto;

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

    @RequestMapping("getOptionDataList")
    public RestResponseDto getOptionDataList(@RequestParam("product_id") int product_id){
        RestResponseDto restResponseDto = new RestResponseDto();

        List<Map<String, Object>> optionDataList = storeService.getOptionDataList(product_id);

        restResponseDto.add("optionDataList", optionDataList);

        return restResponseDto;
    }
    
    @RequestMapping("getAdditionalInfo")
    public RestResponseDto getAdditionalInfo(
        @RequestParam("product_id")int product_id, 
        @RequestParam("currentIndex") int currentIndex,
        @RequestParam("value_ids") int[] value_ids
    ){
        RestResponseDto restResponseDto = new RestResponseDto();

        if (value_ids == null) {
            value_ids = new int[0];
        }

        List<Map<String, Object>> additionalInfoDataList = storeService.getAdditionalInfoDataList(product_id, currentIndex, value_ids);

        restResponseDto.add("additionalInfoDataList", additionalInfoDataList);

        return restResponseDto;
    }

    @RequestMapping("getAdditionalInfoDto")
    public RestResponseDto getAdditionalInfoDto(@RequestParam("value_ids") int[] value_ids){
        RestResponseDto restResponseDto = new RestResponseDto();

        restResponseDto.add("additionalInfoDto", storeService.getAdditionalInfoDto(value_ids));

        return restResponseDto;
    }

    @RequestMapping("addToCart")
    public RestResponseDto addToCart(
        @RequestParam("quantity") int quantity,
        @RequestParam("product_id") int product_id,
        @RequestParam("value_ids") int[] value_ids,
        @RequestParam("user_id") int user_id
    ){
        RestResponseDto restResponseDto = new RestResponseDto();

        CartProductDto cartProductDto = new CartProductDto();
        cartProductDto.setProduct_id(product_id);
        cartProductDto.setQuantity(quantity);
        cartProductDto.setUser_id(user_id);

        storeService.insertCartProduct(cartProductDto, value_ids);

        return restResponseDto;
    }

    @RequestMapping("getCartProductList")
    public RestResponseDto getCartProductList(@RequestParam("user_id") int user_id){
        RestResponseDto restResponseDto = new RestResponseDto();

        List<Map<String, Object>> cartProductInfoDataList = storeService.getCartProductDataList(user_id);

        restResponseDto.add("cartProductInfoDataList", cartProductInfoDataList);

        return restResponseDto;
    }

    @RequestMapping("insertPendingOrder")
    public RestResponseDto insertPendingOrder(@RequestParam("user_id")int user_id, @RequestParam("cart_product_ids") int[] cart_product_ids){
        RestResponseDto restResponseDto = new RestResponseDto();

        storeService.insertPendingOrder(user_id, cart_product_ids);

        return restResponseDto;
    }

    // @RequestMapping("getPendingOrderProductList")
    // public RestResponseDto getPendingOrderProductList(@RequestParam("user_id") int user_id){
    //     RestResponseDto restResponseDto = new RestResponseDto();

    //     List<Map<String, Object>> pendingOrderProductInfoDataList = storeService.getPendingOrderDataList(user_id);

    //     restResponseDto.add("pendingOrderProductInfoDataList", pendingOrderProductInfoDataList);

    //     return restResponseDto;
    // }

    @RequestMapping("deletePendingOrderDtoList")
    public RestResponseDto deletePendingOrderDtoList(@RequestParam("user_id") int user_id){
        RestResponseDto restResponseDto = new RestResponseDto();

        storeService.deletePendingOrder(user_id);

        return restResponseDto;
    }

    @RequestMapping("addToOrdersheet")
    public RestResponseDto addToOrdersheet(
        @RequestParam("user_id")int user_id, 
        @RequestParam("cart_product_ids") int[] cart_product_ids,
        HttpSession session
    ){
        RestResponseDto restResponseDto = new RestResponseDto();

        session.setAttribute("pendingOrderCartProductIds", cart_product_ids);

        storeService.deletePendingOrder(user_id);
        storeService.insertPendingOrder(user_id, cart_product_ids);

        return restResponseDto;
    }

    @RequestMapping("orderProcess")
    public RestResponseDto orderProcess(
        @RequestParam("used_point")int used_point, 
        @RequestParam("delivery_address") String delivery_address, 
        @RequestParam("payment_amount") int payment_amount,
        HttpSession session
    ){
        RestResponseDto restResponseDto = new RestResponseDto();

        UserDto userDto = (UserDto)session.getAttribute("sessionUserInfo");
        int user_id = userDto.getId();
        int order_id = storeService.orderProcess(user_id, used_point, delivery_address, payment_amount);
        
        int[] pendingOrderCartProductIds = (int[])session.getAttribute("pendingOrderCartProductIds");

        if(pendingOrderCartProductIds!=null){
            storeService.deleteCartProductDataList(pendingOrderCartProductIds);
        }

        restResponseDto.add("order_id", order_id);

        

        return restResponseDto;
    }


    // 확인용
    @RequestMapping("getPendingOrderCartProductIds")
    public RestResponseDto getPendingOrderCartProductIds(HttpSession session){
        RestResponseDto restResponseDto = new RestResponseDto();

        restResponseDto.add("pendingOrderCartProductIds", session.getAttribute("pendingOrderCartProductIds"));

        return restResponseDto;
    }
}
