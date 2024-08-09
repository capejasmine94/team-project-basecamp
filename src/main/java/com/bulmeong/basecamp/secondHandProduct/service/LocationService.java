package com.bulmeong.basecamp.secondHandProduct.service;

import com.bulmeong.basecamp.secondHandProduct.dto.LocationDto;
import com.bulmeong.basecamp.secondHandProduct.mapper.LocationSqlMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LocationService {

    @Autowired
    private LocationSqlMapper locationSqlMapper;

    public void myLocation(LocationDto locationDto) {

        if(locationSqlMapper.isLocationRegistered(locationDto.getUser_id()) == 0) {

            locationSqlMapper.insertMyLocation(locationDto);
        } else {

            locationSqlMapper.updateMyLocation(locationDto);
        }
    }

    public LocationDto selectMyLocation(int user_id) {
        return locationSqlMapper.selectMyLocation(user_id);
    }

}
