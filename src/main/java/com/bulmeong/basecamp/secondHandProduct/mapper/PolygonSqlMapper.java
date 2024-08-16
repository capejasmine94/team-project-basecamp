package com.bulmeong.basecamp.secondHandProduct.mapper;

import com.bulmeong.basecamp.secondHandProduct.dto.PolygonDto;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface PolygonSqlMapper {

    public int countPolygon();
    public void insertPolygon(PolygonDto polygon);
    public List<PolygonDto> selectPolygon();

}
