package com.bulmeong.basecamp.secondHandProduct.controller;

import com.bulmeong.basecamp.common.dto.RestResponseDto;
import com.bulmeong.basecamp.secondHandProduct.dto.*;
import com.bulmeong.basecamp.secondHandProduct.service.ProductService;
import com.bulmeong.basecamp.user.dto.UserDto;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.*;

@RestController
@RequestMapping("api/secondhandProduct")
public class RestProductController {

    @Autowired
    private ProductService productService;

    @RequestMapping("like")
    public RestResponseDto like(HttpSession session,
                                @ModelAttribute WishListDto wishListDto) {

        RestResponseDto restResponseDto = new RestResponseDto();
        restResponseDto.setResult("success");

        UserDto userDto = (UserDto) session.getAttribute("sessionUserInfo");

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
        wishListDto.setUser_id(userDto.getId());

        boolean byUserLike = productService.selectProductByUserLike(wishListDto);
        restResponseDto.add("byUserLike", byUserLike);

        return restResponseDto;

    }

    @RequestMapping("deletePost")
    public RestResponseDto deletePost(@RequestParam("product_id") int product_id) {
        RestResponseDto restResponseDto = new RestResponseDto();
        restResponseDto.setResult("success");

        productService.deletePost(product_id);

        return restResponseDto;
    }

    @RequestMapping(value = "updateProcess", method = RequestMethod.POST)
    public RestResponseDto productProcess(@ModelAttribute SecondhandProductDto secondhandProductDto,
                                          @RequestParam(name = "images", required = false) MultipartFile[] images,
                                          @RequestParam(name = "main_image", required = false) String mainImage,
                                          @RequestParam(name = "existing_images", required = false) String[] existingImages,
                                          @RequestParam(name = "user_id", required = false) int userId,
                                          @RequestParam(name = "product_id") int product_id) {

        RestResponseDto restResponseDto = new RestResponseDto();
        restResponseDto.setResult("success");

        List<ImageDto> imageDtoList = new ArrayList<>();
        // 기존 이미지 URL 유지
        if (existingImages != null) {
            for (String imageUrl : existingImages) {
                ImageDto imageDto = new ImageDto();
                imageDto.setImage_url(imageUrl);
                imageDto.setProduct_id(product_id);
                imageDtoList.add(imageDto);
            }
        }

        if (images != null) {
            for (int i = 0; i < images.length; i++) {
                MultipartFile image = images[i];
                if (image.isEmpty()) {
                    continue;
                }
                String rootPath = "/Users/simgyujin/basecampImage/";

                // 날짜 폴더
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd/");
                String todayPath = sdf.format(new Date());

                File todayFolderForCreate = new File(rootPath + todayPath);

                if (!todayFolderForCreate.exists()) {
                    todayFolderForCreate.mkdirs();
                }

                // 파일 충돌
                String originalFileName = image.getOriginalFilename();
                String uuid = UUID.randomUUID().toString();
                long currentTime = System.currentTimeMillis();
                String fileName = uuid + "_" + currentTime;
                // 확장자 추출
                String ext = originalFileName.substring(originalFileName.lastIndexOf("."));
                fileName += ext;

                try {
                    image.transferTo(new File(rootPath + todayPath + fileName));
                } catch (Exception e) {
                    e.printStackTrace();
                }
                // 첫 번째 이미지를 메인 이미지로 설정
                if (i == 0) {
                    secondhandProductDto.setMain_image(todayPath + fileName);
                }
                // DB 저장 DTO
                ImageDto imageDto = new ImageDto();
                imageDto.setImage_url(todayPath + fileName);
                imageDto.setProduct_id(product_id);
                imageDtoList.add(imageDto);
            }

        }

        secondhandProductDto.setUser_id(userId);
        secondhandProductDto.setProduct_id(product_id);
        productService.updateProduct(secondhandProductDto, imageDtoList);

        return restResponseDto;
    }

    // 상품 거래상태 업데이트
    @RequestMapping("updateStatus")
    public RestResponseDto updateStatus(@RequestBody AllContentsProductDto allContentsProductDto) {

        RestResponseDto restResponseDto = new RestResponseDto();
        restResponseDto.setResult("success");

        productService.updateProductStatus(allContentsProductDto);
        System.out.println("거래 상태 변경 완료");

       return restResponseDto;
    }



}
