package com.bulmeong.basecamp.store.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

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
import com.bulmeong.basecamp.store.mapper.StoreSqlMapper;

@Service
public class StoreService {

    @Autowired
    private StoreSqlMapper storeSqlMapper;

    public boolean isExistAccountId(String account_id){
        return storeSqlMapper.selectCountStoreByAccountId(account_id)>0;
    } 

    public boolean isExistStoreName(String name){
        return storeSqlMapper.selectCountStoreByName(name)>0;
    } 

    public StoreDto getStoreDtoByAccountInfo(String account_id, String account_pw){
        return storeSqlMapper.selectStoreByAccountInfo(account_id, account_pw);
    }

    public void registerStore(StoreDto storeDto, StoreDeliveryInfoDto storeDeliveryInfoDto, StoreBankAccountDto storeBankAccountDto){
        storeSqlMapper.insertStoreDto(storeDto);

        int store_id = storeDto.getId();

        storeDeliveryInfoDto.setStore_id(store_id);
        storeBankAccountDto.setStore_id(store_id);

        storeSqlMapper.insertStoreDeliveryInfoDto(storeDeliveryInfoDto);
        storeSqlMapper.insertStoreBankAccountDto(storeBankAccountDto);
    }

    public StoreBankAccountDto getStoreBankAccountDtoByStoreId(int store_id){
        return storeSqlMapper.selectStoreBankAccountDtoByStoreId(store_id);
    }

    public List<StoreProductCategoryDto> getProductCategoryAll(){
        return storeSqlMapper.selectProductCategoryAll();
    }

    public List<ProductSubcategoryDto> getProductSubcategoryByCategoryId(int category_id){
        return storeSqlMapper.selectProductSubcategoryByCategoryId(category_id);
    }

    public int registerProductAndReturnId(StoreProductDto storeProductDto, double percentage){
        if(percentage!=0){
            StoreProductDiscountDto storeProductDiscountDto = new StoreProductDiscountDto();
            storeProductDiscountDto.setPercentage(percentage/100);
            storeSqlMapper.insertDiscountInfo(storeProductDiscountDto);

            storeProductDto.setDiscount_id(storeProductDiscountDto.getId());
        }
        storeSqlMapper.insertProduct(storeProductDto);

        return storeProductDto.getId();
    }

    public void registerProductOptionNameAndValue(ProductOptionNameDto productOptionNameDto, String[] optionValues){

        storeSqlMapper.insertOptionName(productOptionNameDto);
        int option_id = productOptionNameDto.getId();

        for(String optionValue : optionValues){
            ProductOptionValueDto productOptionValueDto = new ProductOptionValueDto();
            productOptionValueDto.setName(optionValue);
            productOptionValueDto.setOption_id(option_id);

            storeSqlMapper.insertOptionValue(productOptionValueDto);
        }
    }

    public void registerProductAddtionalInfo(String[] names, AdditionalInfoDto additionalInfoDto){

        storeSqlMapper.insertAdditionalInfo(additionalInfoDto);
        int additional_info_id = additionalInfoDto.getId();
        
        for(String name : names){
            OptionValueAdditionalInfoDto optionValueAdditionalInfoDto = new OptionValueAdditionalInfoDto();
            int option_value_id = storeSqlMapper.selectOptionValueIdByName(name);

            optionValueAdditionalInfoDto.setAdditional_info_id(additional_info_id);
            optionValueAdditionalInfoDto.setOption_value_id(option_value_id);

            storeSqlMapper.insertOptionValueAdditionalInfo(optionValueAdditionalInfoDto);
        }
    }

    public List<Map<String, Object>> getAllProductDataList(){

        List<Map<String, Object>> result = new ArrayList<>();

        List<StoreProductDto> storeProductDtoList = storeSqlMapper.selectAllProducts();

        for(StoreProductDto storeProductDto : storeProductDtoList){
            Map<String, Object> map = new HashMap<>();

            int store_id = storeProductDto.getStore_id();
            StoreDto storeDto = storeSqlMapper.selectStoreDtoById(store_id);

            int discount_id = storeProductDto.getDiscount_id();
            StoreProductDiscountDto storeProductDiscountDto = storeSqlMapper.selectDiscountById(discount_id);
            
            double percentage = storeProductDiscountDto.getPercentage();
            int price = storeProductDto.getPrice();
            int salePrice = (int)Math.round(price-price*percentage);

            map.put("storeProductDto", storeProductDto);
            map.put("storeDto", storeDto);
            map.put("percentage", Math.round(percentage*100));
            map.put("salePrice", salePrice);

            result.add(map);
        }

        return result;
    }

    public List<Map<String, Object>> getProductDataByCategoryId(int id, Integer subcategory_id){
        
        List<Map<String, Object>> result = new ArrayList<>();

        List<StoreProductDto> storeProductDtoList = storeSqlMapper.selectProductDtoListByCategoryId(id, subcategory_id);

        for(StoreProductDto storeProductDto : storeProductDtoList){
            Map<String, Object> map = new HashMap<>();

            int store_id = storeProductDto.getStore_id();
            StoreDto storeDto = storeSqlMapper.selectStoreDtoById(store_id);

            int discount_id = storeProductDto.getDiscount_id();
            StoreProductDiscountDto storeProductDiscountDto = storeSqlMapper.selectDiscountById(discount_id);
            
            double percentage = storeProductDiscountDto.getPercentage();
            int price = storeProductDto.getPrice();
            int salePrice = (int)Math.round(price-price*percentage);

            map.put("storeProductDto", storeProductDto);
            map.put("storeDto", storeDto);
            map.put("percentage", Math.round(percentage*100));
            map.put("salePrice", salePrice);

            result.add(map);
        }

        return result;
    }

    public int countProductDtoByCategoryId(int id, Integer subcategory_id){
        return storeSqlMapper.countProductDtoByCategoryId(id, subcategory_id);
    }

    public Map<String, Object> getProductCategoryDataByCategoryId(int id){
        Map<String, Object> map = new HashMap<>();

        StoreProductCategoryDto storeProductCategoryDto = storeSqlMapper.selectProductCategoryById(id);
        List<ProductSubcategoryDto> productSubcategoryDtoList = storeSqlMapper.selectProductSubcategoryByCategoryId(id);

        map.put("storeProductCategoryDto", storeProductCategoryDto);
        map.put("productSubcategoryDtoList", productSubcategoryDtoList);

        return map;
    }

    public Map<String, Object> getProductDataByProductId(int id){
        Map<String, Object> map = new HashMap<>();

        StoreProductDto storeProductDto = storeSqlMapper.selectProductDtoByID(id);

        int store_id = storeProductDto.getStore_id();
        StoreDto storeDto = storeSqlMapper.selectStoreDtoById(store_id);
        StoreDeliveryInfoDto storeDeliveryInfoDto = storeSqlMapper.selectDeliveryInfoDtoByStoreId(store_id);

        int discount_id = storeProductDto.getDiscount_id();
        StoreProductDiscountDto storeProductDiscountDto = storeSqlMapper.selectDiscountById(discount_id);

        double percentage = storeProductDiscountDto.getPercentage();
        int price = storeProductDto.getPrice();
        int salePrice = (int)Math.round(price-price*percentage);

        map.put("storeProductDto", storeProductDto);
        map.put("storeDto", storeDto);
        map.put("storeDeliveryInfoDto", storeDeliveryInfoDto);
        map.put("percentage", Math.round(percentage*100));
        map.put("salePrice", salePrice);

        return map;

    }

    public boolean isProductInWishlist(int user_id, int product_id){
        return storeSqlMapper.selectProductWishCountByUserId(user_id, product_id) > 0;
    }
}