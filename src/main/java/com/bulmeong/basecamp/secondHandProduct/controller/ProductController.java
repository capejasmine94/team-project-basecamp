package com.bulmeong.basecamp.secondHandProduct.controller;

import com.bulmeong.basecamp.common.util.Utils;
import com.bulmeong.basecamp.secondHandProduct.dto.CategoryDto;
import com.bulmeong.basecamp.secondHandProduct.dto.ImageDto;
import com.bulmeong.basecamp.secondHandProduct.dto.AllContentsProductDto;
import com.bulmeong.basecamp.secondHandProduct.dto.SecondhandProductDto;
import com.bulmeong.basecamp.secondHandProduct.service.ProductService;
import com.bulmeong.basecamp.user.dto.UserDto;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.*;

@Controller
@RequestMapping("secondhandProduct")
public class ProductController {

    @Autowired
    private ProductService productService;
    @Autowired
    private Utils utils;

    @GetMapping("mainPage")
    public String mainPage(Model model) {

        List<AllContentsProductDto> productDtoList = productService.selectSecondhandProductList();
        model.addAttribute("productDtoList", productDtoList);

        return "secondhandProduct/mainPage";
    }

    @GetMapping("productRegistrationPage")
    public String productRegistrationPage(HttpSession session,
                                          Model model) {

        UserDto sessionUserInfo = (UserDto) session.getAttribute("sessionUserInfo");
        model.addAttribute("sessionUserInfo", sessionUserInfo);

        List<CategoryDto> categoryDtoList = productService.selectCategoryList();
        model.addAttribute("categoryDtoList", categoryDtoList);

        return "secondhandProduct/productRegistrationPage";
    }

    @GetMapping("transactionListPage")
    public String transactionListPage() {
        return "secondhandProduct/transactionListPage";
    }

    @GetMapping("myPage")
    public String myPage(HttpSession session,
                         Model model) {

        UserDto sessionUserInfo = (UserDto) session.getAttribute("sessionUserInfo");
        model.addAttribute("sessionUserInfo", sessionUserInfo);

        return "secondhandProduct/myPage";
    }

    @PostMapping("productProcess")
    public String productProcess(@ModelAttribute SecondhandProductDto secondhandProductDto,
                                 @RequestParam(name = "images") MultipartFile[] images,
                                 @RequestParam(name = "main_image") String mainImage,
                                 @RequestParam(name = "user_id") int userId) {

        List<ImageDto> imageDtoList = new ArrayList<>();
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
                imageDtoList.add(imageDto);
            }

        }

        secondhandProductDto.setUser_id(userId);
        productService.insertProduct(secondhandProductDto, imageDtoList);

        return "redirect:/secondhandProduct/mainPage";
    }

    @GetMapping("postDetailsPage")
    public String postDetailsPage(Model model,
                                  @RequestParam(name = "product_id") int product_id) {

        Map<String, Object> productTotalDtoList = productService.selectSecondhandDetailProduct(product_id);
        model.addAttribute("productTotalDtoList", productTotalDtoList);

        productService.updateSecondhandDetailProductCount(product_id);

        return "secondhandProduct/postDetailsPage";
    }

    @GetMapping("productChatRoomPage")
    public String productChatRoomPage(HttpSession session,
                                      Model model,
                                      @RequestParam(name = "product_id") int product_id) {

        UserDto sessionUserInfo = (UserDto) session.getAttribute("sessionUserInfo");
        model.addAttribute("sessionUserInfo", sessionUserInfo);

        SecondhandProductDto secondhandProductDto = productService.selectChatRoomProductInformation(product_id);
        model.addAttribute("ProductDto", secondhandProductDto);



        return "secondhandProduct/productChatRoomPage";
    }

    @GetMapping("selectCategoryPage")
    public String selectCategoryPage(Model model) {

        List<CategoryDto> categoryDtoList = productService.selectCategoryList();
        model.addAttribute("categoryDtoList", categoryDtoList);

        return "secondhandProduct/selectCategoryPage";
    }

    @GetMapping("selectCategoryDetailListPage")
    public String selectCategoryDetailListPage(Model model,
                                               @RequestParam(name = "category_id") int category_id) {

        List<AllContentsProductDto> selectSecondhandProductByCategoryNameList = productService.selectSecondhandProductByCategoryNameList(category_id);
        model.addAttribute("categoryNameList", selectSecondhandProductByCategoryNameList);
        String categoryName = productService.selectCategoryName(category_id);
        model.addAttribute("categoryName", categoryName);


        return "secondhandProduct/selectCategoryDetailListPage";
    }

    @GetMapping("watchlistPage")
    public String watchlistPage(Model model,
                                @RequestParam(name = "user_id") int user_id) {

        List<AllContentsProductDto> userByWishList = productService.selectSecondhandProductByWishList(user_id);
        model.addAttribute("userByWishList", userByWishList);
        return "secondhandProduct/watchlistPage";
    }

    @GetMapping("purchaseHistoryPage")
    public String purchaseHistoryPage() {

        return "secondhandProduct/purchaseHistoryPage";
    }

    @GetMapping("myNeighborhoodSettingsPage")
    public String myNeighborhoodSettingsPage() {

        return "secondhandProduct/myNeighborhoodSettingsPage";
    }

    @GetMapping("neighborhoodCertificationPage")
    public String neighborhoodCertificationPage() {

        return "secondhandProduct/neighborhoodCertificationPage";
    }


}
