package com.bulmeong.basecamp.camp.service;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.bulmeong.basecamp.camp.dto.CampsiteAreaDto;
import com.bulmeong.basecamp.camp.dto.CampsiteAreaImageDto;
import com.bulmeong.basecamp.camp.dto.CampsiteAreaPointDto;
import com.bulmeong.basecamp.camp.dto.CampsiteAreaSelectCategoryDto;
import com.bulmeong.basecamp.camp.dto.CampsiteBankDto;
import com.bulmeong.basecamp.camp.dto.CampsiteCarNumberDto;
import com.bulmeong.basecamp.camp.dto.CampsiteCategoryDto;
import com.bulmeong.basecamp.camp.dto.CampsiteDto;
import com.bulmeong.basecamp.camp.dto.CampsiteImageDto;
import com.bulmeong.basecamp.camp.dto.CampsiteOrderDto;
import com.bulmeong.basecamp.camp.dto.CampsiteOrderUserInfoDto;
import com.bulmeong.basecamp.camp.dto.CampsiteSelectCategoryDto;
import com.bulmeong.basecamp.camp.mapper.CampsiteSqlMapper;
import com.bulmeong.basecamp.common.dto.ImageDto;
import com.bulmeong.basecamp.common.util.ImageUtil;
import com.bulmeong.basecamp.common.util.Utils;
import com.bulmeong.basecamp.user.dto.UserDto;

@Service
public class CampsiteService {
    @Autowired
    private CampsiteSqlMapper campsiteSqlMapper;
    @Autowired
    private Utils utils;

    private CampsiteDto sessionCampsiteDto() {
        Map<String,Object> info = utils.getSession("campsite");
        return (CampsiteDto)info.get("dto");
    }

    //===================================================================================================================
    // 로그인 / 회원가입 구역
    //===================================================================================================================
    //판매자 로그인 확인
    public CampsiteDto getCampsiteDtoByAccountInfo(String account, String password) {
        return campsiteSqlMapper.getCampsiteDtoByAccountInfo(account,password);
    }
    
    //새 회원가입 번호
    public String newCampsiteId() { 
        return String.format("%02d", campsiteSqlMapper.newCampsiteId()) ; 
    }
    
    //판매자 회원 등록
    public void registerSeller(CampsiteDto campsiteDto, CampsiteBankDto campsiteBankDto, MultipartFile profileImage) {
        // 프로필 사진 저장
        if(profileImage != null) {
            String profileImageToString = ImageUtil.saveImageAndReturnLocation(profileImage);   
            campsiteDto.setProfile_image(profileImageToString);
        }

        // 새 판매자 계정 생성
        campsiteSqlMapper.registerUser(campsiteDto);

        // 새 계좌 생성
        campsiteBankDto.setCampsite_id(campsiteDto.getId());
        campsiteSqlMapper.registerBank(campsiteBankDto);
    }
    
    // 캠핑장 등록
    public void registerCamp(CampsiteDto campsiteDto, MultipartFile mapImage, MultipartFile[] mainImages, String[] categories) {
        //배치도 이미지 저장
        String mapImageToString = ImageUtil.saveImageAndReturnLocation(mapImage);
        campsiteDto.setMap_image(mapImageToString);

        //메인 이미지 저장
        List<ImageDto> mainImageList = ImageUtil.saveImageAndReturnDtoList(mainImages);
        for(ImageDto img : mainImageList) {
            CampsiteImageDto imageDto = new CampsiteImageDto();
            imageDto.setCampsite_id(campsiteDto.getId());
            imageDto.setLocation(img.getLocation());
            imageDto.setOrigin_filename(img.getOrigin_filename());
            campsiteSqlMapper.addCampMainImage(imageDto);
        }

        // 카테고리 저장
        for(String category : categories) {
            int category_id = Integer.parseInt(category);
            CampsiteSelectCategoryDto dto = new CampsiteSelectCategoryDto();
            dto.setCampsite_id(campsiteDto.getId());
            dto.setCategory_id(category_id);
            campsiteSqlMapper.addSelectCampCategory(dto);
        }

        // 판매자 정보 업데이트
        campsiteSqlMapper.registerCamp(campsiteDto);
        
        // 세션 데이터 갱신
        utils.setSession("campsite", campsiteInfo(campsiteDto.getId()));
    }
    //-------------------------------------------------------------------------------------------------------------------



    //===================================================================================================================
    // 카테고리
    //===================================================================================================================
    // 모든 카테고리
    public Map<String,Object> categories() {
        // 초기값
        Map<String, Object> result = new HashMap<>();

        // 캠핑장 카테고리
        result.put("camp", campsiteSqlMapper.campCategory());

        // 구역 카테고리
        result.put("area", campsiteSqlMapper.areaCategory());

        // 마무리
        return result;
    }

    // 모든 카테고리 캠핑장 번호로 찾기
    private List<CampsiteCategoryDto> showCategory(int campsite_id) {
        List<CampsiteCategoryDto> result = new ArrayList<>();
        List<Map<String, Object>> list = campsiteSqlMapper.selectCampCategory(campsite_id);
        
        for (Map<String, Object> category : list) {
            CampsiteCategoryDto dto = new CampsiteCategoryDto();
            dto.setId((int)category.get("id"));
            dto.setName((String)category.get("name"));
            dto.setImage((String)category.get("image"));
            result.add(dto);
        }

        List<CampsiteAreaDto> areaList = campsiteSqlMapper.getAreaListByCampsiteId(campsite_id);
        
        for (CampsiteAreaDto area : areaList) {
            if(area.getProcess() == null || 
               area.getProcess().equals("설정 필요") ||
               campsiteSqlMapper.getPointList(area.getId()).isEmpty() ||
               campsiteSqlMapper.getPointList(area.getId()) == null)
               continue;
            int area_id = area.getId();
            list = campsiteSqlMapper.selectAreaCategory(area_id);
            
            for (Map<String, Object> category : list) {
                CampsiteCategoryDto dto = new CampsiteCategoryDto();
                dto.setId((int)category.get("id"));
                dto.setName((String)category.get("name"));
                dto.setImage((String)category.get("image"));
                result.add(dto);
            }
        }

        // 중복된 이름을 가진 dto들을 제거
        Set<String> seenNames = new HashSet<>();
        result = result.stream()
                .filter(dto -> seenNames.add(dto.getName()))
                .collect(Collectors.toList());

        return result;
    }

    

    //-------------------------------------------------------------------------------------------------------------------



    //===================================================================================================================
    // 판매자 구역
    //===================================================================================================================
    //고유 번호로 캠핑장 모든 정보
    public Map<String,Object> campsiteInfo(int campsite_id) {
        // 초기값
        Map<String, Object> result = new HashMap<>();

        // Dto 확인
        CampsiteDto campsiteDto = campsiteSqlMapper.getCampsiteDtoById(campsite_id);
        if(campsiteDto == null) return null;
        
        // Dto
        result.put("dto", campsiteDto);

        // 구역
        List<CampsiteAreaDto> areaList = campsiteSqlMapper.getAreaListByCampsiteId(campsite_id);
        List<Map<String,Object>> areaInfoList = new ArrayList<>();
        for(CampsiteAreaDto area : areaList) {
            if(area == null) continue;
            int area_id = area.getId();
            Map<String,Object> areaInfo = areaInfo(area_id);
            areaInfoList.add(areaInfo);
        }
        result.put("area", areaInfoList);

        //기타 정보
        int min_Prise = 0;
        Integer value = campsiteSqlMapper.minPriseByCampsiteId(campsite_id);
        if(value != null)
            min_Prise = value;
        result.put("minPrise", min_Prise);

        //카테고리 
        result.put("campCategory", campsiteSqlMapper.selectCampCategory(campsite_id));
        result.put("showCategory", showCategory(campsite_id));

        // 메인 이미지
        result.put("mainImages", campsiteSqlMapper.campMainImage(campsite_id));

        //예약
        result.put("reservation", getOrderByCampsiteId(campsite_id));
        //리뷰

        // 마무리
        return result;
    }

    //고유 번호로 구역 모든 정보
    public Map<String,Object> areaInfo(int area_id) {
        // 초기값
        Map<String, Object> result = new HashMap<>();

        // Dto 확인
        CampsiteAreaDto areaDto = campsiteSqlMapper.getAreaById(area_id);
        if(areaDto == null) return null;
        
        // Dto
        result.put("dto", areaDto);

        // 포인트
        Map<String,Object> pointMap = new HashMap<>();
        pointMap.put("point", campsiteSqlMapper.getPointList(area_id));
        pointMap.put("dto", campsiteSqlMapper.pointByAreaId(area_id));
        result.put("pointInfo", pointMap);

        // 카테고리
        result.put("category", campsiteSqlMapper.selectAreaCategory(area_id));

        // 메인 이미지
        result.put("mainImages", campsiteSqlMapper.areaMainImage(area_id));

        // 마무리
        return result;
    }

    // 캠핑장 수정
    public void updateCamp(CampsiteDto campsiteDto, MultipartFile mapImage, MultipartFile[] mainImages, String[] categories) {
        //배치도 이미지 저장
        if(mapImage != null && !mapImage.isEmpty()) {
            String mapImageToString = ImageUtil.saveImageAndReturnLocation(mapImage);
            campsiteDto.setMap_image(mapImageToString);
        }
        else {
            campsiteDto.setMap_image(sessionCampsiteDto().getMap_image());
        }
        //메인 이미지 저장
        if(mainImages != null && !mainImages[0].isEmpty()) {
            campsiteSqlMapper.deleteCampMainImage(campsiteDto.getId());
            List<ImageDto> mainImageList = ImageUtil.saveImageAndReturnDtoList(mainImages);
            for(ImageDto img : mainImageList) {
                System.out.println(img);
                CampsiteImageDto imageDto = new CampsiteImageDto();
                imageDto.setCampsite_id(campsiteDto.getId());
                imageDto.setLocation(img.getLocation());
                imageDto.setOrigin_filename(img.getOrigin_filename());
                campsiteSqlMapper.addCampMainImage(imageDto);
            }
        }

        // 카테고리 수정
        campsiteSqlMapper.deleteSelectCampCategory(campsiteDto.getId());
        Set<String> set = new LinkedHashSet<>(Arrays.asList(categories));
        categories = set.toArray(new String[0]);
        for(String category : categories) {
            System.out.println(category);
            int category_id = Integer.parseInt(category);
            CampsiteSelectCategoryDto dto = new CampsiteSelectCategoryDto();
            dto.setCampsite_id(campsiteDto.getId());
            dto.setCategory_id(category_id);
            campsiteSqlMapper.addSelectCampCategory(dto);
        }

        // 판매자 정보 업데이트
        campsiteSqlMapper.updateCamp(campsiteDto);

        // 세션 데이터 갱신
        utils.setSession("campsite", campsiteInfo(campsiteDto.getId()));
    }
    
    // 구역 생성
    public void registerArea(CampsiteAreaDto campsiteAreaDto) {
        campsiteAreaDto.setCampsite_id(sessionCampsiteDto().getId());
        campsiteSqlMapper.registerArea(campsiteAreaDto);   
    }

    // 구역 수정
    public void updateArea(CampsiteAreaDto campsiteAreaDto, MultipartFile mapImage, MultipartFile[] mainImages, String[] categories) {
         //배치도 이미지 저장
        
         if(mapImage != null && !mapImage.isEmpty()) {
            String mapImageToString = ImageUtil.saveImageAndReturnLocation(mapImage);
            campsiteAreaDto.setMap_image(mapImageToString);
        }
        else {
            campsiteAreaDto.setMap_image(sessionCampsiteDto().getMap_image());
        }
        //메인 이미지 저장
        if(mainImages != null && !mainImages[0].isEmpty()) {
            campsiteSqlMapper.deleteAreaMainImage(campsiteAreaDto.getId());
            List<ImageDto> mainImageList = ImageUtil.saveImageAndReturnDtoList(mainImages);
            for(ImageDto img : mainImageList) {
                CampsiteAreaImageDto imageDto = new CampsiteAreaImageDto();
                imageDto.setArea_id(campsiteAreaDto.getId());
                imageDto.setLocation(img.getLocation());
                imageDto.setOrigin_filename(img.getOrigin_filename());
                campsiteSqlMapper.addAreaMainImage(imageDto);
            }
        }

        // 카테고리 수정
        campsiteSqlMapper.deleteSelectAreaCategory(campsiteAreaDto.getId());
        Set<String> set = new LinkedHashSet<>(Arrays.asList(categories));
        categories = set.toArray(new String[0]);
        for(String category : categories) {
            System.out.println(category);
            int category_id = Integer.parseInt(category);
            CampsiteAreaSelectCategoryDto dto = new CampsiteAreaSelectCategoryDto();
            dto.setArea_id(campsiteAreaDto.getId());
            dto.setCategory_id(category_id);
            campsiteSqlMapper.addSelectAreaCategory(dto);
        }

        // 판매자 정보 업데이트
        campsiteSqlMapper.updateArea(campsiteAreaDto);

        // 세션 데이터 갱신
        utils.setSession("campsite", campsiteInfo(sessionCampsiteDto().getId()));
    }

    // 포인트 생성
    public void registerPoint(CampsiteAreaPointDto pointDto, int count, int area_id) {
        pointDto.setArea_id(area_id);
        campsiteSqlMapper.registerPoint(pointDto);
        campsiteSqlMapper.registerPointId(pointDto);
        pointDto.setPoint_id(pointDto.getId());
        for(int i = 0; i < count-1; i++) {
            campsiteSqlMapper.registerPoint(pointDto);
        }
        // 세션 데이터 갱신
        utils.setSession("campsite", campsiteInfo(sessionCampsiteDto().getId()));
    }

    // 포인트 수정
    public void updatePoint(int area_id, String[] names, int[] counts) {
        if(names == null || names.length == 0) return;
        campsiteSqlMapper.deletePoints(area_id);
        for(int i = 0; i < names.length; i++) {
            CampsiteAreaPointDto pointDto = new CampsiteAreaPointDto();
            pointDto.setName(names[i]);
            registerPoint(pointDto, counts[i], area_id);
        }
    }

    // 포인트 삭제
    public void deletePoint(int point_id){
        campsiteSqlMapper.deletePoint(point_id);
    }

    public void deleteArea(int area_id) {
        campsiteSqlMapper.deletePoints(area_id);
        campsiteSqlMapper.deleteArea(area_id);
    }
    //-------------------------------------------------------------------------------------------------------------------



    //===================================================================================================================
    // 구매자 구역
    //===================================================================================================================
    // 모든 캠핑장 리스트
    public List<Map<String,Object>> campsiteList() {
        List<Map<String,Object>> result = new ArrayList<>();
        List<CampsiteDto> campsiteList = campsiteSqlMapper.getAllCampsiteDto();
        for(CampsiteDto dto : campsiteList) {
            result.add(campsiteInfoForUser(dto.getId()));
        }
        return result;
    }

    //유저가 보는 캠핑장리스트
    public Map<String,Object> campsiteInfoForUser(int campsite_id) {
        // 초기값
        Map<String, Object> result = new HashMap<>();

        // Dto 확인
        CampsiteDto campsiteDto = campsiteSqlMapper.getCampsiteDtoById(campsite_id);
        if(campsiteDto == null) return null;
        
        // Dto
        result.put("dto", campsiteDto);

        // 구역
        List<CampsiteAreaDto> areaList = campsiteSqlMapper.getAreaListByCampsiteId(campsite_id);
        List<Map<String,Object>> areaInfoList = new ArrayList<>();
        for(CampsiteAreaDto area : areaList) {
            if(area == null) continue;
            if(area.getProcess().equals("설정 필요")) continue;
            if(campsiteSqlMapper.getPointList(area.getId()).isEmpty() ||
            campsiteSqlMapper.getPointList(area.getId()) == null) continue;
            int area_id = area.getId();
            Map<String,Object> areaInfo = areaInfo(area_id);
            areaInfoList.add(areaInfo);
        }
        result.put("area", areaInfoList);

        //기타 정보
        int min_Prise = campsiteSqlMapper.minPriseByCampsiteId(campsite_id);
        result.put("minPrise", min_Prise);

        //카테고리 
        result.put("campCategory", campsiteSqlMapper.selectCampCategory(campsite_id));
        result.put("showCategory", showCategory(campsite_id));

        // 메인 이미지
        result.put("mainImages", campsiteSqlMapper.campMainImage(campsite_id));

        //리뷰

        // 마무리
        return result;
    }

    // 예약번호로 예약 가져오기
    public Map<String, Object> getOrderByCode(String resvCode) {
        Map<String, Object> result = new HashMap<>();
        CampsiteOrderDto orderDto = campsiteSqlMapper.getOrderByResvCode(resvCode);
        result.put("dto", orderDto);
        result.put("camp", campsiteSqlMapper.getCampsiteByPointId(orderDto.getPoint_id()));
        result.put("area", campsiteSqlMapper.getAreaByPointId(orderDto.getPoint_id()));
        result.put("carNumbers", campsiteSqlMapper.getCarNumberList(orderDto.getId()));
        result.put("userInfo", campsiteSqlMapper.getUserInfoByOrderId(orderDto.getId()));
        result.put("point", campsiteSqlMapper.pointById(orderDto.getPoint_id()));
        return result;
    }
    
    // 회원의 모든 예약 리스트
    public List<Map<String, Object>> getOrderByUserId(int user_id) {
        List<Map<String, Object>> result = new ArrayList<>();
        List<CampsiteOrderDto> list = campsiteSqlMapper.getOrderByUserId(user_id);
        for(CampsiteOrderDto orderDto : list){
        Map<String, Object> map = new HashMap<>();
            map.put("dto", orderDto);
            map.put("camp", campsiteSqlMapper.getCampsiteByPointId(orderDto.getPoint_id()));
            map.put("area", campsiteSqlMapper.getAreaByPointId(orderDto.getPoint_id()));
            map.put("carNumbers", campsiteSqlMapper.getCarNumberList(orderDto.getId()));
            map.put("userInfo", campsiteSqlMapper.getUserInfoByOrderId(orderDto.getId()));
            map.put("point", campsiteSqlMapper.pointById(orderDto.getPoint_id()));
            result.add(map);
        }
        return result;
    }

    public Map<String, Object> getOrderById(int order_id) {
        Map<String, Object> result = new HashMap<>();
        CampsiteOrderDto orderDto = campsiteSqlMapper.getOrderById(order_id);
        result.put("dto", orderDto);
        result.put("camp", campsiteSqlMapper.getCampsiteByPointId(orderDto.getPoint_id()));
        result.put("area", campsiteSqlMapper.getAreaByPointId(orderDto.getPoint_id()));
        result.put("carNumbers", campsiteSqlMapper.getCarNumberList(orderDto.getId()));
        result.put("userInfo", campsiteSqlMapper.getUserInfoByOrderId(orderDto.getId()));
        result.put("point", campsiteSqlMapper.pointById(orderDto.getPoint_id()));
        return result;
    }

    // 캠프장의 모든 예약 리스트
    public List<Map<String, Object>> getOrderByCampsiteId(int campsite_id) {
        List<Map<String, Object>> result = new ArrayList<>();
        List<CampsiteOrderDto> list = campsiteSqlMapper.getOrderByCampsiteId(campsite_id);
        for(CampsiteOrderDto orderDto : list){
        Map<String, Object> map = new HashMap<>();
            map.put("dto", orderDto);
            map.put("carNumbers", campsiteSqlMapper.getCarNumberList(orderDto.getId()));
            map.put("area", campsiteSqlMapper.getAreaByPointId(orderDto.getPoint_id()));
            map.put("userInfo", campsiteSqlMapper.getUserInfoByOrderId(orderDto.getId()));
            map.put("point", campsiteSqlMapper.pointById(orderDto.getPoint_id()));
            result.add(map);
        }
        return result;
    }
    
    // 포인트 번호로 포인트 리스트
    public List<CampsiteAreaPointDto> pointByPointId(int point_id) {
        return campsiteSqlMapper.pointByPointId(point_id);
    }

    // 예약 등록
    public void registerOrder(CampsiteOrderDto campsiteOrderDto, int useMileage, String[] carNumbers) {
        campsiteSqlMapper.registerOrder(campsiteOrderDto);

         // 회원인 경우 회원번호
        if(utils.getSession("sessionUserInfo") != null) {
            UserDto user = utils.getSession("sessionUserInfo");
            CampsiteOrderUserInfoDto userInfoDto = new CampsiteOrderUserInfoDto();
            userInfoDto.setOrder_id(campsiteOrderDto.getId());
            userInfoDto.setUser_id(user.getId());
            userInfoDto.setUse_mileage(useMileage);
            campsiteSqlMapper.registerOrderUserInfo(userInfoDto);
        }

        // 차량 번호
        if(carNumbers == null || carNumbers.length <= 0) 
            return;
        for(String carNumber : carNumbers) {
            CampsiteCarNumberDto carNumberDto = new CampsiteCarNumberDto();
            carNumberDto.setOrder_id(campsiteOrderDto.getId());
            carNumberDto.setCar_number(carNumber);
            campsiteSqlMapper.registerCarNumber(carNumberDto);
        }
    }
    //-------------------------------------------------------------------------------------------------------------------




}
