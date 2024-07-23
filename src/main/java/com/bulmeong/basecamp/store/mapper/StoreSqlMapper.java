package com.bulmeong.basecamp.store.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

import com.bulmeong.basecamp.store.dto.AdditionalInfoDto;
import com.bulmeong.basecamp.store.dto.OptionValueAdditionalInfoDto;
import com.bulmeong.basecamp.store.dto.ProductOptionNameDto;
import com.bulmeong.basecamp.store.dto.ProductOptionValueDto;
import com.bulmeong.basecamp.store.dto.ProductSubcategoryDto;
import com.bulmeong.basecamp.store.dto.StoreBankAccountDto;
import com.bulmeong.basecamp.store.dto.StoreDeliveryInfoDto;
import com.bulmeong.basecamp.store.dto.StoreDto;
import com.bulmeong.basecamp.store.dto.StoreProductCategoryDto;
import com.bulmeong.basecamp.store.dto.StoreProductDiscountDto;
import com.bulmeong.basecamp.store.dto.StoreProductDto;

@Mapper
public interface StoreSqlMapper {

    public int selectCountStoreByAccountId(String account_id);

    public int selectCountStoreByName(String name);

    public StoreDto selectStoreByAccountInfo(@Param("account_id") String account_id, @Param("account_pw") String account_pw);

    //스토어 가입
    public void insertStoreDto(StoreDto storeDto);
    public void insertStoreDeliveryInfoDto(StoreDeliveryInfoDto storeDeliveryInfoDto);
    public void insertStoreBankAccountDto(StoreBankAccountDto storeBankAccountDto);

    public StoreBankAccountDto selectStoreBankAccountDtoByStoreId(int store_id);

    //카테고리 출력
    public List<StoreProductCategoryDto> selectProductCategoryAll();
    public List<ProductSubcategoryDto> selectProductSubcategoryByCategoryId(int category_id);

    public void insertDiscountInfo(StoreProductDiscountDto storeProductDiscountDto);
    public void insertProduct(StoreProductDto storeProductDto);

    public void insertOptionName(ProductOptionNameDto productOptionNameDto);
    public void insertOptionValue(ProductOptionValueDto productOptionValueDto);

    public void insertAdditionalInfo(AdditionalInfoDto additionalInfoDto);
    public int selectOptionValueIdByName(String name);
    public void insertOptionValueAdditionalInfo(OptionValueAdditionalInfoDto optionValueAdditionalInfoDto);

    public List<StoreProductDto> selectAllProducts();
    public StoreDto selectStoreDtoById(int id);
    public StoreProductDiscountDto selectDiscountById(int id);

    public StoreProductCategoryDto selectProductCategoryById(int id);
    public List<StoreProductDto> selectProductDtoListByCategoryId(@Param("id")int id, @Param("subcategory_id") Integer subcategory_id);
    public int countProductDtoByCategoryId(@Param("id")int id, @Param("subcategory_id") Integer subcategory_id);

    public StoreProductDto selectProductDtoByID(int id);
    public StoreDeliveryInfoDto selectDeliveryInfoDtoByStoreId(int store_id);
    public int selectProductWishCount(int product_id);

    public int selectProductWishCountByUserId(int user_id, int product_id);
}