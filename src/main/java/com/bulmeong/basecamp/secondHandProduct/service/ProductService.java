package com.bulmeong.basecamp.secondHandProduct.service;

import com.bulmeong.basecamp.secondHandProduct.dto.UserDto;
import com.bulmeong.basecamp.secondHandProduct.mapper.ProductSqlMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProductService {

    @Autowired
    ProductSqlMapper productSqlMapper;

    public UserDto userTest(UserDto userDto) {
        return productSqlMapper.userTest(userDto);
    }

}
