package com.bulmeong.basecamp.secondHandProduct.service;

import com.bulmeong.basecamp.secondHandProduct.dto.PolygonDto;
import com.bulmeong.basecamp.secondHandProduct.mapper.PolygonSqlMapper;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

@Service
public class PolygonService {

    @Autowired
    private PolygonSqlMapper polygonSqlMapper;
    @Autowired
    private ResourceLoader resourceLoader;

    public boolean countPolygons() {
        return polygonSqlMapper.countPolygon() > 0;
    }

    public void insertPolygon(String resourcePath) throws IOException {
        // 리소스 경로로부터 파일 읽기
        Resource resource = resourceLoader.getResource(resourcePath);
        InputStream inputStream = resource.getInputStream();

        // JSON 파일 파싱
        ObjectMapper mapper = new ObjectMapper();
        JsonNode root = mapper.readTree(inputStream);

        // 각 Feature를 PolygonDto로 변환하여 데이터베이스에 삽입
        for (JsonNode feature : root.path("features")) {
            PolygonDto polygon = new PolygonDto();
            polygon.setPolygon_name(feature.path("properties").path("SIG_KOR_NM").asText());

            polygonSqlMapper.insertPolygon(polygon);
        }
    }

    public List<PolygonDto> selectPolygon() {
        return polygonSqlMapper.selectPolygon();
    }

}
