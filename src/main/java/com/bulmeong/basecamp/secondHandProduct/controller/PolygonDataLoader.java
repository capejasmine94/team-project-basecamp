package com.bulmeong.basecamp.secondHandProduct.controller;

import com.bulmeong.basecamp.secondHandProduct.service.PolygonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class PolygonDataLoader implements CommandLineRunner {

    @Autowired
    private PolygonService polygonService;
    // json 파일 어플리케이션 시작할때 인서트하기
    @Override
    public void run(String... args) throws Exception {
        if (!polygonService.countPolygons()) {
            String resourcePath = "classpath:/static/json/secondhandProduct/sido.json";
            polygonService.insertPolygon(resourcePath);
        }else {
            System.out.println("폴리곤 데이터가 존재합니다.");
        }
    }
}
