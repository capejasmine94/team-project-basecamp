package com.bulmeong.basecamp.secondHandProduct.controller;

import com.bulmeong.basecamp.common.dto.RestResponseDto;
import com.bulmeong.basecamp.common.util.Utils;
import com.bulmeong.basecamp.secondHandProduct.dto.WishListDto;
import com.bulmeong.basecamp.secondHandProduct.service.ProductService;
import com.bulmeong.basecamp.user.controller.UserRestController;
import com.bulmeong.basecamp.user.dto.UserDto;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/secondhandProduct")
public class RestProductController {

    @Autowired
    private ProductService productService;
    @Autowired
    private Utils utils;
    @Autowired
    private UserRestController userRestController;

    @RequestMapping("like")
    public RestResponseDto like(HttpSession session,
                                @ModelAttribute WishListDto wishListDto) {

        RestResponseDto restResponseDto = new RestResponseDto();
        restResponseDto.setResult("success");

        UserDto userDto = (UserDto) session.getAttribute("sessionUserInfo");
//        if (userDto == null) {
//            utils.loginUser(1);
//            userDto = (UserDto) session.getAttribute("sessionUserInfo");
//        }

        wishListDto.setUser_id(userDto.getId());
        productService.insertProductLike(wishListDto);


        return restResponseDto;
    }

    @RequestMapping("unLike")
    public RestResponseDto unLike(HttpSession session,
                                  @ModelAttribute WishListDto wishListDto) {
        RestResponseDto restResponseDto = new RestResponseDto();
        restResponseDto.setResult("success");

        UserDto userDto = (UserDto) session.getAttribute("sessionUserInfo");
//        if (userDto == null) {
//            utils.loginUser(1);
//            userDto = (UserDto) session.getAttribute("sessionUserInfo");
//        }

        wishListDto.setUser_id(userDto.getId());
        productService.deleteProductByUserLike(wishListDto);

        return restResponseDto;
    }
    @RequestMapping("totalLikeCount")
    public RestResponseDto totalLikeCount (@RequestParam(name = "product_id") int product_id) {

        RestResponseDto restResponseDto = new RestResponseDto();
        restResponseDto.setResult("success");

        int count = productService.selectProductTotalLikeCount(product_id);
        restResponseDto.add("count", count);

        return restResponseDto;
    }

    @RequestMapping("productByUserLike")
    public RestResponseDto productByUserLike (HttpSession session,
                                              @ModelAttribute WishListDto wishListDto) {
        RestResponseDto restResponseDto = new RestResponseDto();
        restResponseDto.setResult("success");

        UserDto userDto = (UserDto) session.getAttribute("sessionUserInfo");
//        if (userDto == null) {
//            utils.loginUser(1);
//            userDto = (UserDto) session.getAttribute("sessionUserInfo");
//        }
        wishListDto.setUser_id(userDto.getId());

        boolean byUserLike = productService.selectProductByUserLike(wishListDto);
        restResponseDto.add("byUserLike", byUserLike);

        return restResponseDto;

    }

}
