package com.bulmeong.basecamp.secondHandProduct.controller;


import com.bulmeong.basecamp.secondHandProduct.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("apl/chatPolling")
public class RestChatPollingController {

    @Autowired
    private ProductService productService;

}
