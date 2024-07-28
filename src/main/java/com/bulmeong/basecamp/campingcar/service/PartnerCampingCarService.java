package com.bulmeong.basecamp.campingcar.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.bulmeong.basecamp.campingcar.dto.BasicFacilitiesDto;
import com.bulmeong.basecamp.campingcar.dto.CampingcarDto;
import com.bulmeong.basecamp.campingcar.dto.CarTypeDto;
import com.bulmeong.basecamp.campingcar.dto.DriverAgeCondDto;
import com.bulmeong.basecamp.campingcar.dto.DriverExperienceCondDto;
import com.bulmeong.basecamp.campingcar.dto.DriverLicenseDto;
import com.bulmeong.basecamp.campingcar.dto.LocationDto;
import com.bulmeong.basecamp.campingcar.dto.ProductDetailImgDto;
import com.bulmeong.basecamp.campingcar.dto.RentalCompanyDto;
import com.bulmeong.basecamp.campingcar.mapper.PartnerCampingCarSqlMapper;
import com.bulmeong.basecamp.common.dto.ImageDto;
import com.bulmeong.basecamp.common.util.ImageUtil;

@Service
public class PartnerCampingCarService {
    @Autowired
    private PartnerCampingCarSqlMapper partnerCampingCarSqlMapper;
    //판매자 회원가입 등록 
    public void registerSeller(RentalCompanyDto rentalCompanyDto) {
        partnerCampingCarSqlMapper.createSeller(rentalCompanyDto);
    }
    // 판매자 id,pw 검사 
    public RentalCompanyDto getSellerByIdAndPw (String account_id, String account_pw) {
        return partnerCampingCarSqlMapper.selectCompanyByAccountIdandPw(account_id, account_pw);
    } 
    // 판매자회원가입_지역 카테고리 List
    public List<LocationDto> getLocationAll() {
        return partnerCampingCarSqlMapper.findLocationAll();
    }
    // 판매자 id 가지고 오기 
    public Map<String,Object> getcampingCarandSellerInfo (int id){
        Map<String,Object> map = new HashMap<>();
        RentalCompanyDto rentalCompanyDto = partnerCampingCarSqlMapper.getRentalCompanyInfoByid(id);
        map.put("rentalCompanyDto", rentalCompanyDto);
        CampingcarDto compingCarDto = partnerCampingCarSqlMapper.getCompingCarByid(id);
        map.put("compingCarDto", compingCarDto);

        return map;
    }

    // 차량등록 
    public void registerCamping(CampingcarDto campingCar, List<Integer> basicFacilites_id, MultipartFile[] detailedImg) {
        // 기본 차량등록 insert
        partnerCampingCarSqlMapper.createCamping(campingCar);
        // 차량등록X기본보유옵션 테이블 insert
        int product_id = campingCar.getId();
        for(int basic_facilities_id :basicFacilites_id) {
            partnerCampingCarSqlMapper.createCarBasic(basic_facilities_id,product_id);
        }

        // 최종 DetailImageList 담을 인스턴스 생성, 차량등록의 세부이미지 테이블 insert
        List<ProductDetailImgDto> productImageList = new ArrayList<>();
        List<ImageDto> ImgeDtoList = ImageUtil.saveImageAndReturnDtoList(detailedImg);
        for(ImageDto imgeDto : ImgeDtoList){
            ProductDetailImgDto productDetailImgDto = new ProductDetailImgDto();
            productDetailImgDto.setLocation(imgeDto.getLocation());
            productDetailImgDto.setOriginal_filename(imgeDto.getOrigin_filename());
            productDetailImgDto.setProduct_id(campingCar.getId());

            productImageList.add(productDetailImgDto);
            
            partnerCampingCarSqlMapper.createDetailImg(productDetailImgDto.getProduct_id(), 
                                           productDetailImgDto.getLocation(),
                                           productDetailImgDto.getOriginal_filename());
                                           
            System.out.println("상세이미지_test_service" + productDetailImgDto);
        }

    }

    // 차량등록_캠핑카 유형 Category List
    public List<CarTypeDto> getCarTypeAll() {
        return partnerCampingCarSqlMapper.findCarTypeAll(); 
    }
    // 차량등록_운전 면허증 Category List
    public List<DriverLicenseDto> getDriverLicenseAll() {
        return partnerCampingCarSqlMapper.findDriverLicenseAll();
    }
    // 차량등록_운전자 나이 Category List
    public List<DriverAgeCondDto> getDriverAgeAll() {
        return partnerCampingCarSqlMapper.findDriverAgeAll(); 
    }
    // 차량등록_운전자 경력 Category List
    public List<DriverExperienceCondDto> getDriverExperienceAll() {
        return partnerCampingCarSqlMapper.findDriverExperienceAll();
    }

    // 캠핑카 기본 보유 시설 Category 
    public List<BasicFacilitiesDto> getBasicFacilitiesAll() {
        return partnerCampingCarSqlMapper.findBasicFacilitiesAll();
    }

    // 사용자 : 차량 등록 list(외래키의 받은 테이블을 엮음)
    public List<Map<String,Object>> getCampingCarAll() {
        return partnerCampingCarSqlMapper.findCampingCarAll();
    }

    // 판매자 : 차량 현황및수정 List(외래키의 받은 테이블 엮음)
    public List<Map<String,Object>> getCampingcarBySellerId(int rental_company_id) {
        return partnerCampingCarSqlMapper.findCampingCarBySellerId(rental_company_id);

    }


}
