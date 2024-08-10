package com.bulmeong.basecamp.secondHandProduct.controller;

import com.bulmeong.basecamp.common.dto.RestResponseDto;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

@RestController
@RequestMapping("api")
public class RestRegionController {

        @GetMapping("regions")
        public String getRegions() throws Exception {

            String serviceKey = "Dy3lTEaDlQxz3SJhrtQ0BTzwXCZ14Ziuf99b6utPOw%2FiYyCJ7j5aGVXs4nFN7KY1BwPOkKqu9pNJs6dE5YPFkg%3D%3D";
            String pageNo = "1";
            String numOfRows = "100";
            String type = "json";
            String regionName = URLEncoder.encode("경기도", "UTF-8");

            String urlString = String.format(
                    "https://apis.data.go.kr/1741000/StanReginCd/getStanReginCdList?" +
                            "serviceKey=%s" +
                            "&pageNo=%s" +
                            "&numOfRows=%s" +
                            "&type=%s" +
                            "&locatadd_nm=%s",
                    serviceKey, pageNo, numOfRows, type, regionName);


            URL url = new URL(urlString);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.setRequestProperty("Content-type", "application/json");

            BufferedReader rd;
            if (conn.getResponseCode() >= 200 && conn.getResponseCode() <= 300) {
                rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            } else {
                rd = new BufferedReader(new InputStreamReader(conn.getErrorStream()));
            }

            StringBuilder sb = new StringBuilder();
            String line;
            while ((line = rd.readLine()) != null) {
                sb.append(line);
            }
            rd.close();
            conn.disconnect();

            return sb.toString();
        }

}