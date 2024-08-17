package com.bulmeong.basecamp.secondHandProduct.controller;
//
import org.json.JSONException;
import org.json.JSONObject;
import com.bulmeong.basecamp.campingcar.dto.RestResponseDto;
import org.json.JSONArray;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api")
public class RestRegionController {

//    @GetMapping("regions")
//    public RestResponseDto getRegions() throws Exception {
//
//        String serviceKey = "YOUR_SERVICE_KEY"; // 여기에 실제 서비스 키를 넣으세요.
//        String pageNo = "1";
//        String numOfRows = "100";
//        String type = "json";
//        String regionName = URLEncoder.encode("경기도", "UTF-8");
//
//        String urlString = String.format(
//                "https://apis.data.go.kr/1741000/StanReginCd/getStanReginCdList?" +
//                        "serviceKey=%s" +
//                        "&pageNo=%s" +
//                        "&numOfRows=%s" +
//                        "&type=%s" +
//                        "&locatadd_nm=%s",
//                serviceKey, pageNo, numOfRows, type, regionName);
//
//        URL url = new URL(urlString);
//        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
//        conn.setRequestMethod("GET");
//        conn.setRequestProperty("Content-type", "application/json");
//
//        BufferedReader rd;
//        if (conn.getResponseCode() >= 200 && conn.getResponseCode() <= 300) {
//            rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
//        } else {
//            rd = new BufferedReader(new InputStreamReader(conn.getErrorStream()));
//        }
//
//        StringBuilder sb = new StringBuilder();
//        String line;
//        while ((line = rd.readLine()) != null) {
//            sb.append(line);
//        }
//        rd.close();
//        conn.disconnect();
//
//        // 응답 데이터 출력
//        String response = sb.toString();
//        System.out.println("API 응답: " + response);
//
//        // JSON 파싱 시도
//        try {
//            JSONObject json = new JSONObject(response);
//            // "response" 키가 존재하는지 확인
//            if (!json.has("response")) {
//                throw new RuntimeException("API 응답에 'response' 키가 존재하지 않습니다: " + response);
//            }
//
//            JSONObject responseBody = json.getJSONObject("response").getJSONObject("body");
//            JSONArray items = responseBody.getJSONArray("items");
//
//            RestResponseDto responseDto = new RestResponseDto();
//            responseDto.setResult("success");
//
//            List<String> cities = new ArrayList<>();
//            for (int i = 0; i < items.length(); i++) {
//                JSONObject item = items.getJSONObject(i);
//                cities.add(item.getString("regionName")); // "regionName"는 실제 응답 데이터의 키에 맞게 수정 필요
//            }
//
//            responseDto.add("cities", cities);
//            return responseDto;
//
//        } catch (JSONException e) {
//            e.printStackTrace();
//            throw new RuntimeException("API 응답을 JSON으로 파싱할 수 없습니다: " + response);
//        }
//    }
}