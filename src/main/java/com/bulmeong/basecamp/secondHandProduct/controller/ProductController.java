package com.bulmeong.basecamp.secondHandProduct.controller;

import com.bulmeong.basecamp.common.dto.RestResponseDto;
import com.bulmeong.basecamp.common.util.Utils;
import com.bulmeong.basecamp.secondHandProduct.dto.*;
import com.bulmeong.basecamp.secondHandProduct.service.ChatService;
import com.bulmeong.basecamp.secondHandProduct.service.LocationService;
import com.bulmeong.basecamp.secondHandProduct.service.PolygonService;
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
    private ChatService chatService;
    @Autowired
    private PolygonService polygonService;
    @Autowired
    private LocationService locationService;
    @Autowired
    private Utils utils;

    @GetMapping("mainPage")
    public String mainPage(Model model,
                           HttpSession session,
                           @RequestParam(name = "selected_area_name", required = false) String selected_area_name) {

        UserDto sessionUserInfo = (UserDto) session.getAttribute("sessionUserInfo");

        String areaName = locationService.selectMyLocation(sessionUserInfo.getId());
        model.addAttribute("areaName", areaName);

        if(areaName == null || areaName.equals("전체")) {
            List<AllContentsProductDto> productDtoList = productService.selectSecondhandProductList();
            model.addAttribute("productDtoList", productDtoList);
        } else {
            List<AllContentsProductDto> productDtoList = productService.selectSecondhandProductIsAreaList(areaName);
            model.addAttribute("productDtoList", productDtoList);
        }

        return "secondhandProduct/mainPage";
    }

    @GetMapping("productRegistrationPage")
    public String productRegistrationPage(HttpSession session,
                                          Model model) {

        UserDto sessionUserInfo = (UserDto) session.getAttribute("sessionUserInfo");
        model.addAttribute("sessionUserInfo", sessionUserInfo);

        List<CategoryDto> categoryDtoList = productService.selectCategoryList();
        model.addAttribute("categoryDtoList", categoryDtoList);

        List<PolygonDto> polygonDtoList = polygonService.selectPolygon();
        model.addAttribute("polygonDtoList", polygonDtoList);

        return "secondhandProduct/productRegistrationPage";
    }

    @GetMapping("productModificationPage")
    public String productModificationPage(HttpSession session, Model model,
                                          @RequestParam("product_id") int product_id) {

        UserDto sessionUserInfo = (UserDto) session.getAttribute("sessionUserInfo");
        model.addAttribute("sessionUserInfo", sessionUserInfo);

        List<CategoryDto> categoryDtoList = productService.selectCategoryList();
        model.addAttribute("categoryDtoList", categoryDtoList);

        List<PolygonDto> polygonDtoList = polygonService.selectPolygon();
        model.addAttribute("polygonDtoList", polygonDtoList);

        Map<String, Object> productTotalDtoMap = productService.selectSecondhandDetailProduct(product_id);
        model.addAttribute("productTotalDtoMap", productTotalDtoMap);

        return "secondhandProduct/productModificationPage";
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

    @PostMapping("productUpdate")
    public String productUpdate(@ModelAttribute SecondhandProductDto secondhandProductDto,
                                @RequestParam(name = "images", required = false) MultipartFile[] images,
                                @RequestParam(name = "main_image", required = false) String mainImage,
                                @RequestParam(name = "existing_images", required = false) String existingImages,
                                @RequestParam(name = "user_id") int userId,
                                @RequestParam(name = "product_id") int product_id) {

        // 기존 이미지 삭제
        List<ImageDto> existingImageList = productService.selectSecondhandProductImgList(product_id);
        for (ImageDto imageDto : existingImageList) {
            File imageFile = new File("/Users/simgyujin/basecampImage/" + imageDto.getImage_url());
            if (imageFile.exists()) {
                imageFile.delete();
            }
            productService.deleteImageByUrl(imageDto.getImage_url());
        }


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

                // 파일 충돌 방지
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
        secondhandProductDto.setUpdated_at(new Date());
        productService.updateProduct(secondhandProductDto, imageDtoList);

        return "redirect:/secondhandProduct/mainPage";
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

                // 파일 충돌 방지
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
        System.out.println(productTotalDtoList);
        model.addAttribute("productTotalDtoList", productTotalDtoList);


        productService.updateSecondhandDetailProductCount(product_id);

        return "secondhandProduct/postDetailsPage";
    }

    @GetMapping("chatRoomPage")
    public String chatRoomPage() {

        return "secondhandProduct/chatRoomPage";
    }
    // 채팅방 생성 - 전체
    @RequestMapping("allChatRoomPage")
    public String allChatRoomPage(HttpSession session, Model model) {

        UserDto sessionUserInfo = (UserDto) session.getAttribute("sessionUserInfo");
        List<SaleChatRoomDto> allChatRoomDto = chatService.selectAllChatRoomList(sessionUserInfo.getId());

        model.addAttribute("allChatRoomDto", allChatRoomDto);
        model.addAttribute("currentUserId", sessionUserInfo.getId());

        return "partials/secondhandProduct/allChatRoomPage :: content";
    }
    // 채팅방 생성 - 구매
    @RequestMapping("buyChatRoomPage")
    public String buyChatRoomPage(HttpSession session, Model model) {

        UserDto sessionUserInfo = (UserDto) session.getAttribute("sessionUserInfo");
        List<SaleChatRoomDto> buyChatRoomDto = chatService.selectBuyChatRoomList(sessionUserInfo.getId());

        model.addAttribute("buyChatRoomDto", buyChatRoomDto);

        return "partials/secondhandProduct/buyChatRoomPage :: content";
    }
    // 채팅방 생성 - 판매
    @RequestMapping("saleChatRoomPage")
    public String saleChatRoomPage(HttpSession session, Model model) {

        UserDto sessionUserInfo = (UserDto) session.getAttribute("sessionUserInfo");
        List<SaleChatRoomDto> saleChatRoomDto = chatService.selectSaleChatRoomList(sessionUserInfo.getId());

        model.addAttribute("saleChatRoomDto", saleChatRoomDto);

        return "partials/secondhandProduct/saleChatRoomPage :: content";
    }

    @GetMapping("productChatMessagePage")
    public String productChatMessagePage(Model model,
                                        @RequestParam(name = "product_id") int product_id,
                                        @RequestParam(name = "chat_room_id") int chat_room_id,
                                         @RequestParam(name = "nickname", required = false) String nickname) {


        Map<String, Object> productTotalDtoList = productService.selectSecondhandDetailProduct(product_id);
        productTotalDtoList.put("nickname", nickname);
        model.addAttribute("productTotalDtoList", productTotalDtoList);

        List<ChatMessageDto> chatMessageDtoList = chatService.selectChatRoomMessage(chat_room_id);
        model.addAttribute("chatMessageDtoList", chatMessageDtoList);



        return "secondhandProduct/productChatMessagePage";
    }

    // 대화중인 채팅방
    @GetMapping("conversationChatPage")
    public String conversationChatPage(Model model,
                                       @RequestParam("product_id") int product_id) {

        List<SaleChatRoomDto> saleChatRoomDto = chatService.selectByProductChatRoomList(product_id);

        model.addAttribute("saleChatRoomDto", saleChatRoomDto);

        return "secondhandProduct/conversationChatPage";
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
    public String neighborhoodCertificationPage(HttpSession session,
                                                Model model,
                                                @ModelAttribute LocationDto locationDto,
                                                @RequestParam(name = "selected_area_name") String selected_area_name) {

        model.addAttribute("selected_area_name", selected_area_name);

        UserDto sessionUserInfo = (UserDto) session.getAttribute("sessionUserInfo");
        locationDto.setUser_id(sessionUserInfo.getId());
        locationService.insertMyLocation(locationDto);


        return "secondhandProduct/neighborhoodCertificationPage";
    }


    @GetMapping("administrativeRegionListPage")
    public String administrativeRegionListPage(Model model) {

        List<PolygonDto> polygonDtoList = polygonService.selectPolygon();
        model.addAttribute("polygonDtoList", polygonDtoList);

        return "secondhandProduct/administrativeRegionListPage";
    }

    // 판매내역 페이지
    @GetMapping("mySalesHistoryPage")
    public String mySalesHistoryPage (HttpSession session,
                                      Model model) {

        String salesStatus = "판매중";
        String transactionCompleteStatus = "거래완료";
        UserDto sessionUserInfo = (UserDto) session.getAttribute("sessionUserInfo");

        int totalSalesPost = productService.getTotalSales(sessionUserInfo.getId(), salesStatus);
        model.addAttribute("totalSalesPost", totalSalesPost);

        int totalTransactionCompletePost = productService.getTotalTransactionComplete(sessionUserInfo.getId(), transactionCompleteStatus);
        model.addAttribute("totalTransactionCompletePost", totalTransactionCompletePost);

        return "secondhandProduct/mySalesHistoryPage";
    }
    // 판매내역 - 판매중
    @GetMapping("salesPage")
    public String salesPage (HttpSession session,
                             Model model) {

        String status = "판매중";
        UserDto sessionUserInfo = (UserDto) session.getAttribute("sessionUserInfo");

        List<AllContentsProductDto> mySalesDtoList = productService.selectSalesProduct(sessionUserInfo.getId(), status);

        model.addAttribute("mySalesDtoList", mySalesDtoList);

        return "partials/secondhandProduct/salesPage :: content";
    }

    // 판매내역 - 거래완료페이지
    @GetMapping("transactionCompletePage")
    public String transactionCompletePage (HttpSession session,
                                            Model model) {

        String status = "거래완료";
        UserDto sessionUserInfo = (UserDto) session.getAttribute("sessionUserInfo");

        List<AllContentsProductDto> myTransactionCompleteDtoList = productService.selectTransactionCompleteProduct(sessionUserInfo.getId(), status);
        model.addAttribute("myTransactionCompleteDtoList", myTransactionCompleteDtoList);

        return "partials/secondhandProduct/transactionCompletePage :: content";
    }

    // 거래완료 -> 판매자 선택
    @GetMapping("buyerSelectionPage")
    public String buyerSelectionPage(Model model, HttpSession session,
                                     @RequestParam("product_id") int product_id) {

        Map<String, Object> productTotalDtoList = productService.selectSecondhandDetailProduct(product_id);
        model.addAttribute("productTotalDtoList", productTotalDtoList);

        UserDto sessionUserInfo = (UserDto) session.getAttribute("sessionUserInfo");
        int seller_user_id = sessionUserInfo.getId();
        List<ProductBuyerDto> productBuyerDtoList = productService.getProductBuyerList(seller_user_id);
        model.addAttribute("productBuyerDtoList", productBuyerDtoList);

        return "secondhandProduct/buyerSelectionPage";
    }


}
