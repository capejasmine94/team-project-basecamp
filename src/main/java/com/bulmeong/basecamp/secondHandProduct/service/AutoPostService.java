package com.bulmeong.basecamp.secondHandProduct.service;

import com.bulmeong.basecamp.secondHandProduct.dto.SecondhandProductDto;
import com.bulmeong.basecamp.secondHandProduct.mapper.ProductSqlMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;
import java.util.UUID;
//@EnableScheduling

@Service
public class AutoPostService {

    @Autowired
    private ProductSqlMapper productSqlMapper;
    private int count = 0;

    @Scheduled(fixedRate = 300000) // 1시간마다 실행 (밀리초 단위로 설정)
    public void autoPost() {
        Random random = new Random();
        int randomId = random.nextInt(6) + 58;
        int randomCategoryId = random.nextInt(16) + 1;
        int randomPolygonId = random.nextInt(17) + 1;
        String[] title = {
                "너무너무너무~",
                "자동테스트~~",
                "잘하고있니?",
                "비싸요비싸~",
                "안뚱아라까앙"
        };
        String randomTitle = title[random.nextInt(title.length)];
        int randomPrice = (random.nextInt(50000 - 1000 + 1)) + 1000;
        String[] explanation = {
                "이 상품은 정말 멋져요.",
                "최고의 품질을 보장합니다.",
                "한정 판매 중입니다.",
                "서둘러 구매하세요.",
                "인기 상품입니다."
        };
        String randomExplanation = explanation[random.nextInt(explanation.length)];
        String[] images = {
                "src/main/resources/static/public/img/secondHandProductImage/auto1.png",
                "src/main/resources/static/public/img/secondHandProductImage/auto2.png",
                "src/main/resources/static/public/img/secondHandProductImage/auto3.png",
                "src/main/resources/static/public/img/secondHandProductImage/auto4.png",
                "src/main/resources/static/public/img/secondHandProductImage/auto5.png"
        };
        String randomImage = images[random.nextInt(images.length)];
        String rootPath = "/Users/simgyujin/basecampImage/";
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd/");
        String todayPath = sdf.format(new Date());
        File todayFolderForCreate = new File(rootPath + todayPath);

        if (!todayFolderForCreate.exists()) {
            todayFolderForCreate.mkdirs();
        }

        String uuid = UUID.randomUUID().toString();
        long currentTime = System.currentTimeMillis();
        String fileName = uuid + "_" + currentTime + ".png";

        try {
            // 이미지 파일 복사
            Files.copy(Paths.get(randomImage), Paths.get(rootPath + todayPath + fileName), StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException e) {
            e.printStackTrace();
        }


        // 자동생성 게시글
        SecondhandProductDto newProduct = new SecondhandProductDto();
        newProduct.setUser_id(randomId);
        newProduct.setCategory_id(randomCategoryId);
        newProduct.setPolygon_id(randomPolygonId);
        newProduct.setMain_image(todayPath + fileName);
        newProduct.setTitle(randomTitle);
        newProduct.setPrice(randomPrice);
        newProduct.setExplanation(randomExplanation);
        newProduct.setStatus("판매중");
        newProduct.setRead_count(0);
        newProduct.setChat_room_count(0); // 초기 조회수 0으로 설정


        productSqlMapper.insertSecondhandProduct(newProduct);
        count++;
        System.out.println(count + "번째 업로드완료");
    }
}
