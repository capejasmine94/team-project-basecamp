package com.bulmeong.basecamp.secondHandProduct.service;

import com.bulmeong.basecamp.secondHandProduct.dto.LocationDto;
import com.bulmeong.basecamp.secondHandProduct.mapper.LocationSqlMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LocationService {

    @Autowired
    private LocationSqlMapper locationSqlMapper;

    public void insertMyLocation(LocationDto locationDto) {
        if(locationSqlMapper.isAreaName(locationDto.getUser_id()) == 0) {

            locationSqlMapper.insertMyLocation(locationDto);
            System.out.println(locationDto.getUser_id() + "번 고객의 위치가 등록 됐습니다.");

        } else if(locationSqlMapper.isAreaName(locationDto.getUser_id()) == 1) {

            locationSqlMapper.updateMyLocation(locationDto);
            System.out.println(locationDto.getUser_id() + "번 고객의 위치가 업데이트 됐습니다.");

        } else {
            System.out.println(locationDto.getUser_id() + "번 고객은 위치가 등록된 상태입니다.");
        }
    }

    public String selectMyLocation(int user_id) {
        return locationSqlMapper.selectMyLocation(user_id);
    }



}
