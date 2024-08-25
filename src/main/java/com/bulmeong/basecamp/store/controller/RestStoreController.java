package com.bulmeong.basecamp.store.controller;

import java.util.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.bulmeong.basecamp.common.dto.ImageDto;
import com.bulmeong.basecamp.common.dto.RestResponseDto;
import com.bulmeong.basecamp.common.util.ImageUtil;
import com.bulmeong.basecamp.store.dto.AdditionalInfoDto;
import com.bulmeong.basecamp.store.dto.CartProductDto;
import com.bulmeong.basecamp.store.dto.OptionRequestData;
import com.bulmeong.basecamp.store.dto.OrderDeliveryInfoDto;
import com.bulmeong.basecamp.store.dto.OrderProductDto;
import com.bulmeong.basecamp.store.dto.ProductOptionNameDto;
import com.bulmeong.basecamp.store.dto.ProductRefundDto;
import com.bulmeong.basecamp.store.dto.ProductReviewDto;
import com.bulmeong.basecamp.store.dto.ProductSearchOptionData;
import com.bulmeong.basecamp.store.dto.ProductWishDto;
import com.bulmeong.basecamp.store.dto.StoreDto;
import com.bulmeong.basecamp.store.dto.StoreOrderDto;
import com.bulmeong.basecamp.store.dto.StoreProductDto;
import com.bulmeong.basecamp.store.dto.StoreRestResponseDto;
import com.bulmeong.basecamp.store.dto.StoreSellerReplyDto;
import com.bulmeong.basecamp.store.dto.UserDeliveryInfoDto;
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
        @RequestParam("uploadDetailImages") MultipartFile[] uploadDetailImages,
        @RequestParam("percentage") double percentage,
        HttpSession session
    ){
        RestResponseDto restResponseDto = new RestResponseDto();

        StoreDto storeDto = (StoreDto)session.getAttribute("sessionStoreInfo");

        storeProductDto.setMain_image(ImageUtil.saveImageAndReturnLocation(uploadMainImage));
        storeProductDto.setStore_id(storeDto.getId());

        List<ImageDto> imageDtoList = ImageUtil.saveImageAndReturnDtoList(uploadDetailImages);

        int product_id = storeService.registerProductAndReturnId(storeProductDto,percentage,imageDtoList);

        restResponseDto.add("product_id", product_id);

        return restResponseDto;
    }

    @RequestMapping("updateStoreInfo")
    public RestResponseDto updateStoreInfo(
        @RequestParam("description") String description,
        @RequestParam("newProfile") MultipartFile newProfile,
        HttpSession session
    ){
        RestResponseDto restResponseDto = new RestResponseDto();

        StoreDto storeDto = (StoreDto)session.getAttribute("sessionStoreInfo");
        storeDto.setDescription(description);
        storeDto.setMain_image(ImageUtil.saveImageAndReturnLocation(newProfile));

        System.out.println(storeDto);

        storeService.updateStoreDto(storeDto);

        StoreDto newStoreDto = storeService.getStoreDtoByAccountInfo(storeDto.getAccount_id(), storeDto.getAccount_pw());
        session.setAttribute("sessionStoreInfo", newStoreDto);

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
        @RequestBody OptionRequestData requestData
    ){
        System.out.println(requestData);
        RestResponseDto restResponseDto = new RestResponseDto();

        AdditionalInfoDto additionalInfoDto = new AdditionalInfoDto();
        additionalInfoDto.setAdditional_price(requestData.getAdditional_price());
        additionalInfoDto.setQuantity(requestData.getQuantity());

        storeService.insertProductAddtionalInfo(requestData, additionalInfoDto);

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

    @RequestMapping("getStockQuantityByProudctId")
    public RestResponseDto getStockQuantityByProudctId(@RequestParam("id") int id){
        RestResponseDto restResponseDto = new RestResponseDto();

        restResponseDto.add("stock_quantity", storeService.getStockQuantityByProudctId(id));

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

    @RequestMapping("getAdditionalInfoData")
    public RestResponseDto getAdditionalInfoData(@RequestParam("value_ids") int[] value_ids){
        RestResponseDto restResponseDto = new RestResponseDto();

        restResponseDto.add("additionalInfoData", storeService.getAdditionalInfoData(value_ids));

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

    //test
    @RequestMapping("getPendingOrderProductList")
    public RestResponseDto getPendingOrderProductList(@RequestParam("user_id") int user_id){
        RestResponseDto restResponseDto = new RestResponseDto();

        List<Map<String, Object>> pendingOrderProductInfoDataList = storeService.getPendingOrderDataList(user_id);

        restResponseDto.add("pendingOrderProductInfoDataList", pendingOrderProductInfoDataList);

        return restResponseDto;
    }

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


    @RequestMapping("removePendingOrder")
    public RestResponseDto removePendingOrder(HttpSession session){
        RestResponseDto restResponseDto = new RestResponseDto();

        session.removeAttribute("pendingOrderCartProductIds");

        UserDto userDto = (UserDto)session.getAttribute("sessionUserInfo");
        storeService.deletePendingOrder(userDto.getId());

        return restResponseDto;
    }

    @RequestMapping("addToOrdersheetBuyNow")
    public RestResponseDto addToOrdersheetBuyNow(
        @RequestParam("quantity") int quantity,
        @RequestParam("product_id") int product_id,
        @RequestParam("value_ids") int[] value_ids,
        @RequestParam("user_id") int user_id,
        HttpSession session
    ){
        RestResponseDto restResponseDto = new RestResponseDto();

        OrderProductDto orderProductDto = new OrderProductDto();
        orderProductDto.setProduct_id(product_id);
        orderProductDto.setQuantity(quantity);

        storeService.insertPendingOrderBuyNow(user_id, orderProductDto, value_ids);

        return restResponseDto;
    }

    @PostMapping("orderProcess")
    public RestResponseDto orderProcess(
        @RequestBody StoreOrderDto storeOrderDto,
        HttpSession session
    ){
        RestResponseDto restResponseDto = new RestResponseDto();

        UserDto userDto = (UserDto)session.getAttribute("sessionUserInfo");
        int user_id = userDto.getId();

        int order_id = storeService.orderProcess(user_id, storeOrderDto);
        
        int[] pendingOrderCartProductIds = (int[])session.getAttribute("pendingOrderCartProductIds");
        if(pendingOrderCartProductIds!=null){
            storeService.deleteCartProductDataList(pendingOrderCartProductIds);
        }

        restResponseDto.add("order_id", order_id);

        return restResponseDto;
    }

    @RequestMapping("getStoreOrderDataList")
    public RestResponseDto getStoreOrderDataList(HttpSession session){
        
        RestResponseDto restResponseDto = new RestResponseDto();

        StoreDto storeDto = (StoreDto)session.getAttribute("sessionStoreInfo");
        int store_id = storeDto.getId();

        restResponseDto.add("storeOrderDataList", storeService.getStoreOrderDataList(store_id));

        return restResponseDto;
    }


    // 확인용
    @RequestMapping("getPendingOrderCartProductIds")
    public RestResponseDto getPendingOrderCartProductIds(HttpSession session){
        RestResponseDto restResponseDto = new RestResponseDto();

        restResponseDto.add("pendingOrderCartProductIds", session.getAttribute("pendingOrderCartProductIds"));

        return restResponseDto;
    }

    // 확인용
    @RequestMapping("getOrderDataByOrderId")
    public RestResponseDto getOrderDataByOrderId(@RequestParam("id") int id){
        RestResponseDto restResponseDto = new RestResponseDto();

        restResponseDto.add("orderData", storeService.getOrderDataByOrderId(id));

        return restResponseDto;
    }

    @RequestMapping("confirmOrder")
    public RestResponseDto confirmOrder(@RequestParam("ids") int[] ids){
        RestResponseDto restResponseDto = new RestResponseDto();

        storeService.confirmOrder(ids);

        return restResponseDto;
    }

    @RequestMapping("insertDeliveryInfo")
    public RestResponseDto insertDeliveryInfo(OrderDeliveryInfoDto orderDeliveryInfoDto){
        RestResponseDto restResponseDto = new RestResponseDto();

        storeService.insertDeliveryInfo(orderDeliveryInfoDto);

        return restResponseDto;
    }

    @PostMapping("addressRegister")
    public RestResponseDto addressRegister(
        @RequestBody UserDeliveryInfoDto userDeliveryInfoDto,
        HttpSession session
    ){
        RestResponseDto restResponseDto = new RestResponseDto();

        UserDto userDto = (UserDto)session.getAttribute("sessionUserInfo");
        int user_id = userDto.getId();
        
        userDeliveryInfoDto.setUser_id(user_id);
        storeService.insertUserDeliveryInfo(userDeliveryInfoDto);

        System.out.println(userDeliveryInfoDto);

        return restResponseDto;
    }


    @RequestMapping("deleteDeliveryInfo")
    public RestResponseDto deleteDeliveryInfo(@RequestParam("id") int id){
        RestResponseDto restResponseDto = new RestResponseDto();

        storeService.deleteUserDeliveryInfoById(id);

        return restResponseDto;
    }

    @RequestMapping("getOrderDataList")
    public RestResponseDto getOrderDataList(HttpSession session, @RequestParam("filterOption")String filterOption){
        RestResponseDto restResponseDto = new RestResponseDto();

        UserDto userDto = (UserDto)session.getAttribute("sessionUserInfo");
        int user_id = userDto.getId();

        List<Map<String, Object>> orderDataList = storeService.getStoreOrderDataListByUserId(user_id, filterOption);

        restResponseDto.add("orderDataList", orderDataList);

        return restResponseDto;
    }

    
    @RequestMapping("getClaimDataList")
    public RestResponseDto getClaimDataList(HttpSession session, @RequestParam("filterOption")String filterOption){
        RestResponseDto restResponseDto = new RestResponseDto();

        UserDto userDto = (UserDto)session.getAttribute("sessionUserInfo");
        int user_id = userDto.getId();

        List<Map<String, Object>> orderDataList = storeService.getStoreClaimDataListByUserId(user_id, filterOption);

        restResponseDto.add("orderDataList", orderDataList);

        return restResponseDto;
    }

    @RequestMapping("getStoreProductList")
    public RestResponseDto getStoreProductList(HttpSession session){
        RestResponseDto restResponseDto = new RestResponseDto();

        StoreDto storeDto = (StoreDto)session.getAttribute("sessionStoreInfo");

        int store_id = storeDto.getId();
        
        restResponseDto.add("storeProductDataList", storeService.getStoreProductByStoreId(store_id));

        return restResponseDto;
    }

    @RequestMapping("purchaseConfirmation")
    public RestResponseDto purchaseConfirmation(@RequestParam("id") int id){
        RestResponseDto restResponseDto = new RestResponseDto();

        storeService.purchaseConfirmation(id);

        restResponseDto.setResult("success");

        return restResponseDto;
    }
    
    @RequestMapping("getOrderStatusCount")
    public RestResponseDto getOrderStatusCount(HttpSession session){
        RestResponseDto restResponseDto = new RestResponseDto();

        UserDto userDto = (UserDto)session.getAttribute("sessionUserInfo");
        int user_id = userDto.getId();

        Map<String, Object> map = storeService.getOrderStatusCountData(user_id);

        restResponseDto.add("orderStatusCount", map);
        
        return restResponseDto;
    }

    @PostMapping("writeReview")
    public RestResponseDto writeReview(@RequestBody ProductReviewDto productReviewDto, HttpSession session){
        RestResponseDto restResponseDto = new RestResponseDto();

        storeService.writeReview(productReviewDto);

        UserDto userDto = (UserDto)session.getAttribute("sessionUserInfo");
        int user_id = userDto.getId();
        storeService.getReviewPoint(user_id);

        return restResponseDto;
    }

    @RequestMapping("getPurchaseConfirmationOrderList")
    public RestResponseDto getPurchaseConfirmationOrderList(HttpSession session){
        RestResponseDto restResponseDto = new RestResponseDto();

        UserDto userDto = (UserDto)session.getAttribute("sessionUserInfo");

        restResponseDto.add("purchaseConfirmationList", storeService.getPurchaseConfirmationList(userDto.getId()));

        return restResponseDto;
    }

    @RequestMapping("getMyReviewList")
    public RestResponseDto getMyReviewList(HttpSession session){
        RestResponseDto restResponseDto = new RestResponseDto();
        
        UserDto userDto = (UserDto)session.getAttribute("sessionUserInfo");

        restResponseDto.add("reviewCompleteList", storeService.getReviewCompleteList(userDto.getId()));
        
        return restResponseDto;
    }

    @RequestMapping("getStoreReviewList")
    public RestResponseDto getStoreReviewList(HttpSession session){
        RestResponseDto restResponseDto = new RestResponseDto();

        StoreDto storeDto = (StoreDto)session.getAttribute("sessionStoreInfo");

        restResponseDto.add("storeReviewList", storeService.getStoreReviewList(storeDto.getId()));

        return restResponseDto;
    }

    @RequestMapping("getStoreReviewData")
    public RestResponseDto getStoreReviewData(@RequestParam("id") int id){
        RestResponseDto restResponseDto = new RestResponseDto();

        restResponseDto.add("reviewData", storeService.selectReviewData(id));

        return restResponseDto;
    }

    @RequestMapping("writeSellerReply")
    public RestResponseDto writeSellerReply(@RequestBody StoreSellerReplyDto storeSellerReplyDto){
        RestResponseDto restResponseDto = new RestResponseDto();

        storeService.writeSellerReply(storeSellerReplyDto);

        return restResponseDto;
    }

    @RequestMapping("getOrderProductDataForRefund")
    public RestResponseDto getOrderProductDataForRefund(@RequestParam("id") int id){
        RestResponseDto restResponseDto = new RestResponseDto();

        restResponseDto.add("orderProductData", storeService.getOrderProductDataForRefund(id));

        return restResponseDto;
    }

    @RequestMapping("requestRefund")
    public RestResponseDto requestRefund(@RequestBody ProductRefundDto productRefundDto){
        RestResponseDto restResponseDto = new RestResponseDto();

        int productRefundId = storeService.orderProductRefund(productRefundDto);
        restResponseDto.add("productRefundId", productRefundId);

        return restResponseDto;
    }

    @RequestMapping("deleteCartProduct")
    public RestResponseDto deleteCartProduct(@RequestParam("id") int id){
        RestResponseDto restResponseDto = new RestResponseDto();

        storeService.deleteCartProductData(id);

        return restResponseDto;

    }

    @RequestMapping("productWish")
    public RestResponseDto productWish(@RequestBody ProductWishDto productWishDto){
        RestResponseDto restResponseDto = new RestResponseDto();

        restResponseDto.add("productWishData", storeService.productWish(productWishDto));

        return restResponseDto;
    }

    @RequestMapping("getStoreOrderDataListByOrderId")
    public RestResponseDto getStoreOrderDataListByOrderId(@RequestParam("id") int id){
        RestResponseDto restResponseDto = new RestResponseDto();

        restResponseDto.add("storeOrderDataList", storeService.getStoreOrderDataListByOrderId(id));

        return restResponseDto;
    }

    @RequestMapping("hasDefaultAddress")
    public RestResponseDto hasDefaultAddress(HttpSession session){
        RestResponseDto restResponseDto = new RestResponseDto();

        UserDto userDto = (UserDto)session.getAttribute("sessionUserInfo");
        int user_id = userDto.getId();
        
        restResponseDto.add("hasDefaultAddress", storeService.hasDefaultAddress(user_id));

        return restResponseDto;
    }

    @RequestMapping("updateCartProductQuantity")
    public void updateCartProductQuantity(@RequestParam("quantity")int quantity, @RequestParam("cart_product_id") int cart_product_id){
        storeService.updateCartProductQuantity(quantity, cart_product_id);
    }

    @RequestMapping("selectNewTenProductDataListByCategoryId")
    public RestResponseDto selectNewTenProductDataListByCategoryId(@RequestParam("category_id") int category_id){
        RestResponseDto restResponseDto = new RestResponseDto();
        
        restResponseDto.add("newTenProductDataList", storeService.selectNewTenProductDataListByCategoryId(category_id));

        return restResponseDto;
    }

    @RequestMapping("selectNewTenProductDataListBySubcategoryId")
    public RestResponseDto selectNewTenProductDataListBySubcategoryId(@RequestParam("category_id") int category_id){
        RestResponseDto restResponseDto = new RestResponseDto();
        
        restResponseDto.add("newTenProductDataList", storeService.selectNewTenProductDataListBySubcategoryId(category_id));

        return restResponseDto;
    }

    @PostMapping("getStoreProductListByFilter")
    public RestResponseDto getStoreProductListByFilter(@RequestBody ProductSearchOptionData requestBody, HttpSession session){
        RestResponseDto restResponseDto = new RestResponseDto();

        StoreDto storeDto = (StoreDto)session.getAttribute("sessionStoreInfo");

        int store_id = storeDto.getId();
        requestBody.setStore_id(store_id);

        restResponseDto.add("storeProductDataList", storeService.getStoreProductByFilter(requestBody));

        return restResponseDto;
    }
}