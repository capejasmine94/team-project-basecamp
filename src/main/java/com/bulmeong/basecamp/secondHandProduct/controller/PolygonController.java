package com.bulmeong.basecamp.secondHandProduct.controller;

import com.bulmeong.basecamp.secondHandProduct.dto.PolygonDto;
import com.bulmeong.basecamp.secondHandProduct.service.PolygonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("api/polygon")
public class PolygonController {
    @Autowired
    private PolygonService polygonService;

    @GetMapping("getPolygonNames")
    public List<PolygonDto> getPolygons() {
        return polygonService.selectPolygon();
    }
}
