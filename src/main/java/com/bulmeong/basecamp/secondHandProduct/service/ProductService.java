package com.bulmeong.basecamp.secondHandProduct.service;

import com.bulmeong.basecamp.secondHandProduct.dto.*;
import com.bulmeong.basecamp.secondHandProduct.mapper.ProductSqlMapper;
import com.bulmeong.basecamp.user.dto.UserDto;
import com.bulmeong.basecamp.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class ProductService {

    @Autowired
    private ProductSqlMapper productSqlMapper;
    @Autowired
    private UserService userService;

    public void insertProduct(SecondhandProductDto secondhandProductDto, List<ImageDto> imageDtoList) {
        productSqlMapper.insertSecondhandProduct(secondhandProductDto);

        for (ImageDto imageDto : imageDtoList) {
            imageDto.setProduct_id(secondhandProductDto.getProduct_id());
            productSqlMapper.insertSecondhandProductImg(imageDto);
        }
    }

    // 이미지 리스트
    public List<ImageDto> selectSecondhandProductImgList(int product_id) {
        return productSqlMapper.selectSecondhandProductImgList(product_id);
    }

    // 이미지 삭제
    public void deleteImageByUrl(String image_url) {
        productSqlMapper.deleteImageByUrl(image_url);
    }

    public void updateProduct(SecondhandProductDto secondhandProductDto, List<ImageDto> imageDtoList) {
        productSqlMapper.updateSecondhandProduct(secondhandProductDto);

        for (ImageDto imageDto : imageDtoList) {
            imageDto.setProduct_id(secondhandProductDto.getProduct_id());
            productSqlMapper.insertSecondhandProductImg(imageDto);
        }
    }


    public List<AllContentsProductDto> selectSecondhandProductList() {
        return productSqlMapper.selectSecondhandProductList();
    }

    public List<AllContentsProductDto> selectSecondhandProductByCategoryNameList(int category_id) {
        return productSqlMapper.selectSecondhandProductByCategoryList(category_id);
    }

    public List<AllContentsProductDto> selectSecondhandProductByWishList(int user_id) {
        return productSqlMapper.selectSecondhandProductByWishList(user_id);
    }

    public String selectCategoryName(int category_id) {
        return productSqlMapper.selectCategoryName(category_id);
    }

    public Map<String, Object> selectSecondhandDetailProduct(int product_id) {

        Map<String, Object> map = new HashMap<>();

        AllContentsProductDto AllContentsProductDto = productSqlMapper.selectSecondhandDetailProduct(product_id);
        UserDto userDto = userService.getUserById(AllContentsProductDto.getUser_id());
        List<ImageDto> imageDtoList = productSqlMapper.selectSecondhandProductImgList(product_id);

        map.put("userDto", userDto);
        map.put("AllContentsDto", AllContentsProductDto);
        map.put("imageDtoList", imageDtoList);


        return map;
    }

    public void updateSecondhandDetailProductCount(int product_id) {
        productSqlMapper.updateSecondhandDetailProductCount(product_id);
    }

    public List<CategoryDto> selectCategoryList() {
        return productSqlMapper.selectCategoryList();
    }

    // 관심상품 등록
    public void insertProductLike(WishListDto wishListDto) {
        productSqlMapper.insertProductLike(wishListDto);
    }

    public void deleteProductByUserLike(WishListDto wishListDto) {
        productSqlMapper.deleteProductByUserLike(wishListDto);
    }

    public int selectProductTotalLikeCount(int product_id) {
        return productSqlMapper.selectProductTotalLikeCount(product_id);
    }

    public boolean selectProductByUserLike(WishListDto wishListDto) {
        return productSqlMapper.selectProductByUserLike(wishListDto) > 0;
    }

    public void deletePost(int product_id) {
        productSqlMapper.deletePost(product_id);
    }


    // 채팅방
    public SecondhandProductDto selectChatRoomProductInformation(int product_id) {
        return productSqlMapper.selectChatRoomProductInformation(product_id);
    }
}
