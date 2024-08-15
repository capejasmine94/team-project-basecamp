package com.bulmeong.basecamp.store.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

import com.bulmeong.basecamp.common.dto.ImageDto;
import com.bulmeong.basecamp.store.dto.AdditionalInfoDto;
import com.bulmeong.basecamp.store.dto.CartProductDto;
import com.bulmeong.basecamp.store.dto.CartProductOptionValueDto;
import com.bulmeong.basecamp.store.dto.OptionValueAdditionalInfoDto;
import com.bulmeong.basecamp.store.dto.OrderDeliveryInfoDto;
import com.bulmeong.basecamp.store.dto.OrderProductDto;
import com.bulmeong.basecamp.store.dto.OrderProductOptionValueDto;
import com.bulmeong.basecamp.store.dto.ProductImageDto;
import com.bulmeong.basecamp.store.dto.ProductOptionNameDto;
import com.bulmeong.basecamp.store.dto.ProductOptionValueDto;
import com.bulmeong.basecamp.store.dto.ProductRefundDto;
import com.bulmeong.basecamp.store.dto.ProductRefundReasonDto;
import com.bulmeong.basecamp.store.dto.ProductReviewDto;
import com.bulmeong.basecamp.store.dto.ProductSubcategoryDto;
import com.bulmeong.basecamp.store.dto.ProductWishDto;
import com.bulmeong.basecamp.store.dto.StoreBankAccountDto;
import com.bulmeong.basecamp.store.dto.StoreDeliveryInfoDto;
import com.bulmeong.basecamp.store.dto.StoreDto;
import com.bulmeong.basecamp.store.dto.StoreOrderDto;
import com.bulmeong.basecamp.store.dto.StoreProductCategoryDto;
import com.bulmeong.basecamp.store.dto.StoreProductDiscountDto;
import com.bulmeong.basecamp.store.dto.StoreProductDto;
import com.bulmeong.basecamp.store.dto.StoreSellerReplyDto;
import com.bulmeong.basecamp.store.dto.UserDeliveryInfoDto;
import com.bulmeong.basecamp.store.mapper.StoreSqlMapper;
import com.bulmeong.basecamp.user.dto.MileageLogDto;
import com.bulmeong.basecamp.user.dto.UserDto;

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

    public int registerProductAndReturnId(StoreProductDto storeProductDto, double percentage, List<ImageDto> imageDtoList){
        if(percentage!=0){
            StoreProductDiscountDto storeProductDiscountDto = new StoreProductDiscountDto();
            storeProductDiscountDto.setPercentage(percentage/100);
            storeSqlMapper.insertDiscountInfo(storeProductDiscountDto);

            storeProductDto.setDiscount_id(storeProductDiscountDto.getId());
        }
        storeSqlMapper.insertProduct(storeProductDto);

        for(ImageDto imageDto : imageDtoList){
            ProductImageDto productImageDto = new ProductImageDto();
            productImageDto.setLocation(imageDto.getLocation());
            productImageDto.setProduct_id(storeProductDto.getId());
            storeSqlMapper.insertProductImage(productImageDto);
        }

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
            
            if(storeProductDiscountDto!=null){
                double percentage = storeProductDiscountDto.getPercentage();
                int price = storeProductDto.getPrice();
                int salePrice = (int)Math.round(price-price*percentage);
                map.put("percentage", Math.round(percentage*100));
                map.put("salePrice", salePrice);
            }else{
                map.put("percentage", 0);
            }

            map.put("storeProductDto", storeProductDto);
            map.put("storeDto", storeDto);


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
            
            if(storeProductDiscountDto!=null){
                double percentage = storeProductDiscountDto.getPercentage();
                int price = storeProductDto.getPrice();
                int salePrice = (int)Math.round(price-price*percentage);
                map.put("percentage", Math.round(percentage*100));
                map.put("salePrice", salePrice);
            }else{
                map.put("percentage", 0);
            }


            map.put("storeProductDto", storeProductDto);
            map.put("storeDto", storeDto);

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
        int productWishCount = storeSqlMapper.selectProductWishCount(id);

        int store_id = storeProductDto.getStore_id();
        StoreDto storeDto = storeSqlMapper.selectStoreDtoById(store_id);
        StoreDeliveryInfoDto storeDeliveryInfoDto = storeSqlMapper.selectDeliveryInfoDtoByStoreId(store_id);

        int discount_id = storeProductDto.getDiscount_id();
        StoreProductDiscountDto storeProductDiscountDto = storeSqlMapper.selectDiscountById(discount_id);

        if(storeProductDiscountDto != null){
            double percentage = storeProductDiscountDto.getPercentage();
            int price = storeProductDto.getPrice();
            int salePrice = (int)Math.round(price-price*percentage);
            map.put("percentage", Math.round(percentage*100));
            map.put("salePrice", salePrice);
        }else{
            map.put("percentage", 0);
            map.put("salePrice", storeProductDto.getPrice());
        }

        List<ProductImageDto> productImageDtoList = storeSqlMapper.selectProductImageDtoListByProductId(id);

        map.put("storeProductDto", storeProductDto);
        map.put("storeDto", storeDto);
        map.put("storeDeliveryInfoDto", storeDeliveryInfoDto);
        map.put("productWishCount", productWishCount);
        map.put("productImageDtoList", productImageDtoList);

        return map;

    }

    public List<Map<String, Object>> getOptionDataList(int product_id){
        List<Map<String, Object>> result = new ArrayList<>();

        List<ProductOptionNameDto> productOptionNameDtoList = storeSqlMapper.selectOptionNameListByProductId(product_id);

        for(ProductOptionNameDto productOptionNameDto : productOptionNameDtoList){

            if(productOptionNameDto.getName().equals("")){
                return result;
            }

            Map<String, Object> map = new HashMap<>();

            int option_id = productOptionNameDto.getId();
            List<ProductOptionValueDto> productOptionValueDtoList = storeSqlMapper.selectOptionValueListByOptionId(option_id);

            map.put("productOptionNameDto", productOptionNameDto);
            map.put("productOptionValueDtoList", productOptionValueDtoList);

            result.add(map);
        }

        return result;
    }

    public int getStockQuantityByProudctId(int product_id){

        StoreProductDto storeProductDto = storeSqlMapper.selectProductDtoByID(product_id);
        int quantity = storeProductDto.getQuantity();
        int purchaseCount = storeSqlMapper.selectPurchaseCountByProductId(product_id);

        int stock_quantity = quantity - purchaseCount;

        return stock_quantity;
    }

    public List<Map<String, Object>> getAdditionalInfoDataList(int product_id, int current_index, int[] value_ids){
        List<Map<String, Object>> result = new ArrayList<>();

        int offset = current_index + 1;
        if (value_ids.length == 0) {
            offset = current_index;
        }

        int option_id = storeSqlMapper.selectNextOptionId(product_id, offset);

        List<ProductOptionValueDto> productOptionValueDtoList = storeSqlMapper.selectOptionValueListByOptionId(option_id);

        for(ProductOptionValueDto productOptionValueDto : productOptionValueDtoList){
            Map<String, Object> map = new HashMap<>();

            int value_id = productOptionValueDto.getId();
            int[] valueIds = new int[value_ids.length+1];
            
            for(int i = 0; i<value_ids.length; i++){

                valueIds[i] = value_ids[i];
            }

            valueIds[value_ids.length] = value_id;

            int additional_info_id = storeSqlMapper.selectAdditionalInfoIdByValueIds(valueIds);

            AdditionalInfoDto additionalInfoDto = storeSqlMapper.selectAdditionalInfoById(additional_info_id);

            int quantity = additionalInfoDto.getQuantity();
            int purchaseQuantity = storeSqlMapper.countOrderProductByOptionValueIds(valueIds, valueIds.length);
            int stock_quantity = quantity-purchaseQuantity;

            map.put("productOptionValueDto", productOptionValueDto);
            map.put("additionalInfoDto", additionalInfoDto);
            map.put("stock_quantity", stock_quantity);

            result.add(map);
        }

        return result;

    }
    
    public Map<String, Object> getAdditionalInfoData(int[] value_ids){
        Map<String, Object> map = new HashMap<>();

        int additional_info_id = storeSqlMapper.selectAdditionalInfoIdByValueIds(value_ids);

        AdditionalInfoDto additionalInfoDto = storeSqlMapper.selectAdditionalInfoById(additional_info_id);
        int quantity = additionalInfoDto.getQuantity();
        int purchaseQuantity = storeSqlMapper.countOrderProductByOptionValueIds(value_ids, value_ids.length);

        int stock_quantity = quantity-purchaseQuantity;
        
        map.put("additionalInfoDto", additionalInfoDto);
        map.put("stock_quantity", stock_quantity);

        return map;
    }

    public void insertCartProduct(CartProductDto cartProductDto, int[] value_ids){
        storeSqlMapper.insertCartProduct(cartProductDto);

        if(value_ids[0]==0){
            return;
        }

        int cart_product_id = cartProductDto.getId();

        for(int value_id : value_ids){
            CartProductOptionValueDto cartProductOptionValueDto = new CartProductOptionValueDto();
            cartProductOptionValueDto.setCart_product_id(cart_product_id);
            cartProductOptionValueDto.setOption_value_id(value_id);

            storeSqlMapper.insertCartProductOptionValue(cartProductOptionValueDto);
        }

    }

    public List<Map<String, Object>> getCartProductDataList(int user_id){
        List<Map<String, Object>> result = new ArrayList<>();

        List<String> storeNames = storeSqlMapper.selectStoreNamesOfCartProdcutByUserId(user_id);
        List<CartProductDto> cartProductDtoList = storeSqlMapper.selectCartProductDtoList(user_id);
        
        for(String store_name : storeNames){
            Map<String, Object> storeMap = new HashMap<>();
            List<Map<String, Object>> list = new ArrayList<>();

            for(CartProductDto cartProductDto: cartProductDtoList){

                int cart_product_id = cartProductDto.getId();
                StoreDto storeDto = storeSqlMapper.selectStoreDtoByCartProductId(cart_product_id);

                if(store_name.equals(storeDto.getName())){

                    Map<String, Object> cartProductData = storeSqlMapper.selectCartProductDataById(cart_product_id);
                    //name,main_image,price,quantity,discount_id,cart_product_id

                    Integer dicount_id = (Integer)cartProductData.get("discount_id");

                    if(dicount_id != 0){
                        StoreProductDiscountDto storeProductDiscountDto = storeSqlMapper.selectDiscountById(dicount_id);
                        double percentage = storeProductDiscountDto.getPercentage();
                        cartProductData.put("percentage", percentage);
                    }

                    //옵션
                    int[] option_value_ids = storeSqlMapper.selectCartProductOptionValueIds(cart_product_id);

                    if(option_value_ids.length==0){
                        list.add(cartProductData);
                        continue;//here
                    }

                    int additional_info_id = storeSqlMapper.selectAdditionalInfoIdByValueIds(option_value_ids);
                    AdditionalInfoDto additionalInfoDto = storeSqlMapper.selectAdditionalInfoById(additional_info_id);

                    List<String> valueNameList = new ArrayList<>();
                    for(int value_id : option_value_ids){
                        String value_name = storeSqlMapper.selectOptionValueNameById(value_id);
                        valueNameList.add(value_name);
                    }
                    cartProductData.put("valueNameList", valueNameList);
                    
                    additionalInfoDto.getQuantity();//옵션의 총 수량(이거 나중에 처리...)
                    int additional_price = additionalInfoDto.getAdditional_price();

                    cartProductData.put("additional_price", additional_price);

                    list.add(cartProductData);
                }

            }
            storeMap.put("cartProductDataList", list);
            storeMap.put("store_name", store_name);
            result.add(storeMap);
        }

        return result;
    }

    public void deletePendingOrder(int user_id){
        List<StoreOrderDto> storeOrderDtoList = storeSqlMapper.selectPendingOrderDtoListByUserId(user_id);

        // 어차피 하나겠지만... 반복문을 돌려본다
        for(StoreOrderDto storeOrderDto : storeOrderDtoList){

            int store_order_id = storeOrderDto.getId();
            storeSqlMapper.deletePendingOrderById(store_order_id);

            List<OrderProductDto> pendingOrderProductList = storeSqlMapper.selectPendingOrderProductListByOrderId(store_order_id);

            for(OrderProductDto orderProductDto : pendingOrderProductList){
                int order_product_id = orderProductDto.getId();
                int[] orderProductOptionValueIds = storeSqlMapper.selectOrderProductOptionValuePrimaryKeys(order_product_id);

                storeSqlMapper.deletePendingOrderProductById(order_product_id);

                for(int value_id : orderProductOptionValueIds){
                    storeSqlMapper.deleteOrderProductOptionValueById(value_id);
                }
            }
        }
    }

    public List<Map<String, Object>> getPendingOrderDataList(int user_id){
        List<Map<String, Object>> result = new ArrayList<>();

        List<String> storeNames = storeSqlMapper.selectStoreNamesOfPendingOrderByUserId(user_id);
        List<OrderProductDto> pendingOrderProductDtoList = storeSqlMapper.selectPendingOrderProductDtoList(user_id);
        
        for(String store_name : storeNames){
            Map<String, Object> storeMap = new HashMap<>();
            List<Map<String, Object>> list = new ArrayList<>();

            for(OrderProductDto orderProductDto: pendingOrderProductDtoList){

                int order_product_id = orderProductDto.getId();
                StoreDto storeDto = storeSqlMapper.selectStoreDtoByOrderProductId(order_product_id);

                if(store_name.equals(storeDto.getName())){

                    Map<String, Object> pendingOrderProductData = storeSqlMapper.selectOrderProductDataById(order_product_id);
                    //name,main_image,price,quantity,discount_id,order_product_id

                    Integer dicount_id = (Integer)pendingOrderProductData.get("discount_id");

                    int price = (int)pendingOrderProductData.get("price");
                    if(dicount_id != 0){
                        StoreProductDiscountDto storeProductDiscountDto = storeSqlMapper.selectDiscountById(dicount_id);
                        double percentage = storeProductDiscountDto.getPercentage();
                        pendingOrderProductData.put("percentage", percentage);

                        int salePrice = (int)Math.round(price-price*percentage);

                        pendingOrderProductData.put("salePrice", salePrice);
                    }else{
                        pendingOrderProductData.put("percentage", 0);
                        pendingOrderProductData.put("salePrice", price);
                    }

                    //옵션
                    int[] option_value_ids = storeSqlMapper.selectOrderProductOptionValueIds(order_product_id);

                    if(option_value_ids.length!=0){
                        int additional_info_id = storeSqlMapper.selectAdditionalInfoIdByValueIds(option_value_ids);
                        AdditionalInfoDto additionalInfoDto = storeSqlMapper.selectAdditionalInfoById(additional_info_id);
    
                        List<String> valueNameList = new ArrayList<>();
                        for(int value_id : option_value_ids){
                            String value_name = storeSqlMapper.selectOptionValueNameById(value_id);
                            valueNameList.add(value_name);
                        }
                        pendingOrderProductData.put("valueNameList", valueNameList);
                        
                        additionalInfoDto.getQuantity();//옵션의 총 수량(이거 나중에 처리...)
                        int additional_price = additionalInfoDto.getAdditional_price();
    
                        pendingOrderProductData.put("additional_price", additional_price);
                    }else{
                        pendingOrderProductData.put("valueNameList", null);
                        pendingOrderProductData.put("additional_price", 0);
                    }

                    list.add(pendingOrderProductData);
                }

            }
            storeMap.put("productOrderProductDataList", list);
            storeMap.put("store_name", store_name);
            result.add(storeMap);
        }

        return result;
    }

    public void insertPendingOrder(int user_id, int[] cart_product_ids){

        StoreOrderDto storeOrderDto = new StoreOrderDto();
        storeOrderDto.setUser_id(user_id);
        storeSqlMapper.insertPendingOrder(storeOrderDto);

        int order_id = storeOrderDto.getId();

        for(int cart_product_id : cart_product_ids){

            OrderProductDto orderProductDto = new OrderProductDto();
            orderProductDto.setOrder_id(order_id);
            
            CartProductDto cartProductDto = storeSqlMapper.selectCartProductById(cart_product_id);
            
            orderProductDto.setProduct_id(cartProductDto.getProduct_id());
            orderProductDto.setQuantity(cartProductDto.getQuantity());

            storeSqlMapper.insertOrderProduct(orderProductDto);

            int order_product_id = orderProductDto.getId();

            int[] option_value_ids = storeSqlMapper.selectCartProductOptionValueIds(cart_product_id);

            if(option_value_ids.length!=0){
                for(int option_value_id : option_value_ids){
                    OrderProductOptionValueDto orderProductOptionValueDto = new OrderProductOptionValueDto();
                    orderProductOptionValueDto.setOrder_product_id(order_product_id);
                    orderProductOptionValueDto.setOption_value_id(option_value_id);
    
                    storeSqlMapper.insertProductOptionValue(orderProductOptionValueDto);
                }
            }
        }
    }

    public int getPendingOrderId(int user_id){
        List<StoreOrderDto> storeOrderDtoList = storeSqlMapper.selectPendingOrderDtoListByUserId(user_id);

        int order_id = 0;
        for(StoreOrderDto storeOrderDto : storeOrderDtoList){
            order_id = storeOrderDto.getId();
        }

        return order_id;
    }

    public int orderProcess(int user_id, StoreOrderDto storeOrder){
        String delivery_address = storeOrder.getDelivery_address();
        String phone = storeOrder.getPhone();
        String receiver_name = storeOrder.getReceiver_name();
        int used_point = storeOrder.getUsed_point();
        int payment_amount = storeOrder.getPayment_amount();

        List<StoreOrderDto> storeOrderDtoList = storeSqlMapper.selectPendingOrderDtoListByUserId(user_id);

        int order_id = 0;

        //어차피 하나겠지만 괜히 반복문을 돌려본다
        for(StoreOrderDto storeOrderDto : storeOrderDtoList){

            order_id = storeOrderDto.getId();
            storeOrderDto.setDelivery_address(delivery_address);
            storeOrderDto.setReceiver_name(receiver_name);
            storeOrderDto.setPhone(phone);
            storeOrderDto.setUsed_point(used_point);
            storeOrderDto.setPayment_amount(payment_amount);
            
            storeSqlMapper.updateStoreOrder(storeOrderDto);
            
            List<OrderProductDto> orderProductDtoList = storeSqlMapper.selectPendingOrderProductListByOrderId(order_id);

            System.out.println("여기봐라");
            System.out.println(orderProductDtoList);

            for(OrderProductDto orderProductDto : orderProductDtoList){

                int product_id = orderProductDto.getProduct_id();
                StoreProductDto storeProductDto = storeSqlMapper.selectProductDtoByID(product_id);
                int price = storeProductDto.getPrice();

                orderProductDto.setProduct_price(price);
                orderProductDto.setProduct_main_image(storeProductDto.getMain_image());
                orderProductDto.setProduct_name(storeProductDto.getName());

                int discount_id = storeProductDto.getDiscount_id();
                int order_product_id = orderProductDto.getId();
                int[] value_ids = storeSqlMapper.selectOrderProductOptionValueIds(order_product_id);

                int quantity = orderProductDto.getQuantity();

                if(discount_id!=0){
                    //할인 있는 경우
                    StoreProductDiscountDto storeProductDiscountDto = storeSqlMapper.selectDiscountById(discount_id);
                    double percentage = storeProductDiscountDto.getPercentage();
                    orderProductDto.setDiscount_percentage(percentage);
                    
                    if(value_ids != null){
                        //옵션 있는 경우
                        int additional_info_id = storeSqlMapper.selectAdditionalInfoIdByValueIds(value_ids);
                        AdditionalInfoDto additionalInfoDto = storeSqlMapper.selectAdditionalInfoById(additional_info_id);
                        int additional_price = additionalInfoDto.getAdditional_price();

                        int salePrice = (int)Math.round(price-price*percentage);
                        salePrice = (salePrice+additional_price)*quantity;

                        int point_discoint = (int)(used_point*((double)salePrice/payment_amount));
                        orderProductDto.setUsed_point(point_discoint);

                        orderProductDto.setOrder_price(salePrice-point_discoint);

                        System.out.println("여기봐라!!!");
                        System.out.println(orderProductDto);

                        storeSqlMapper.updateOrderProduct(orderProductDto);
                    }else{
                        //옵션 없는 경우
                        int salePrice = (int)Math.round(price-price*percentage);
                        salePrice = (salePrice)*quantity;

                        int point_discoint = (int)(used_point*((double)salePrice/payment_amount));
                        orderProductDto.setUsed_point(point_discoint);
                        orderProductDto.setOrder_price(salePrice-point_discoint);

                        storeSqlMapper.updateOrderProduct(orderProductDto);
                    }
                }else{
                    // 할인 없는 경우
                    orderProductDto.setDiscount_percentage(0);
                    System.out.println("여기 봐라");
                    System.out.println(orderProductDto);

                    if(value_ids.length != 0){
                        //옵션 있는 경우
                        int additional_info_id = storeSqlMapper.selectAdditionalInfoIdByValueIds(value_ids);
                        AdditionalInfoDto additionalInfoDto = storeSqlMapper.selectAdditionalInfoById(additional_info_id);
                        int additional_price = additionalInfoDto.getAdditional_price();

                        int salePrice = (price+additional_price)*quantity;

                        int point_discoint = (int)(used_point*((double)salePrice/payment_amount));
                        orderProductDto.setUsed_point(point_discoint);

                        orderProductDto.setOrder_price(salePrice-point_discoint);

                        storeSqlMapper.updateOrderProduct(orderProductDto);
                    }else{
                        System.out.println("여기도 봐라");
                        System.out.println(orderProductDto);
                        //옵션 없는 경우
                        int salePrice = price*quantity;

                        int point_discoint = (int)(used_point*((double)salePrice/payment_amount));
                        orderProductDto.setUsed_point(point_discoint);
                        orderProductDto.setOrder_price(salePrice-point_discoint);

                        storeSqlMapper.updateOrderProduct(orderProductDto);
                    }
                }
                
            }   
        }

        if(used_point!=0){
            MileageLogDto mileageLogDto = new MileageLogDto();
            mileageLogDto.setUser_id(user_id);
            mileageLogDto.setChange_amount(-used_point);
            
            storeSqlMapper.insertPointUsageLog(mileageLogDto);

            storeSqlMapper.updateUserMileage(user_id, used_point);
        }

        return order_id;
    }

    public void deleteCartProductDataList(int[] cartProductIds){

        for(int cartProductId : cartProductIds){

            int[] cartProductOptionValueIds = storeSqlMapper.selectCartProductOptionValuePrimaryKeys(cartProductId);

            storeSqlMapper.deleteCartProduct(cartProductId);            

            for(int cartProductOptionValueId : cartProductOptionValueIds){
                storeSqlMapper.deleteCartProductOptionValue(cartProductOptionValueId);
            }
        }
    }

    public StoreOrderDto getStoreOrderDtoById(int id){

        return storeSqlMapper.selectStoreOrderDtoById(id);
    }

    public Map<String, Object> getOrderDataByOrderId(int id){
        Map<String, Object> result = new HashMap<>();
        
        StoreOrderDto storeOrderDto = storeSqlMapper.selectStoreOrderDtoById(id);

        List<Map<String, Object>> orderProductDataList = new ArrayList<>();
        List<OrderProductDto> orderProductDtoList = storeSqlMapper.selectOrderProductListByOrderId(id, "전체");

        for(OrderProductDto orderProductDto : orderProductDtoList){
            Map<String, Object> map = new HashMap<>();

            StoreDto storeDto = storeSqlMapper.selectStoreDtoByOrderProductId(orderProductDto.getId());
            
            int order_product_id = orderProductDto.getId();
            int[] value_ids = storeSqlMapper.selectOrderProductOptionValueIds(order_product_id);
            List<String> valueNameList = new ArrayList<>();

            if(value_ids.length!=0){
                for(int value_id : value_ids){
                    String value_name = storeSqlMapper.selectOptionValueNameById(value_id);
                    valueNameList.add(value_name);
                }

                int additional_info_id = storeSqlMapper.selectAdditionalInfoIdByValueIds(value_ids);
                AdditionalInfoDto additionalInfoDto = storeSqlMapper.selectAdditionalInfoById(additional_info_id);
                map.put("additionalInfoDto", additionalInfoDto);
            }

            map.put("storeName", storeDto.getName());
            map.put("orderProductDto", orderProductDto);
            map.put("valueNameList", valueNameList);

            orderProductDataList.add(map);
        }

        result.put("storeOrderDto", storeOrderDto);
        result.put("orderProductDataList", orderProductDataList);

        return result;
    }

    public List<Map<String, Object>> getStoreOrderDataList(int store_id){
        List<Map<String, Object>> result = new ArrayList<>();

        List<OrderProductDto> orderProductDtoList = storeSqlMapper.selectOrderProductListByStoreId(store_id);//여기에 나중에 status랑 머더라 아무튼 필터 걸 것임

        for(OrderProductDto orderProductDto : orderProductDtoList){
            Map<String, Object> map = new HashMap<>();
            int order_product_id = orderProductDto.getId();

            int[] value_ids = storeSqlMapper.selectOrderProductOptionValueIds(order_product_id);
            if(value_ids.length!=0){
                int additional_info_id = storeSqlMapper.selectAdditionalInfoIdByValueIds(value_ids);
                AdditionalInfoDto additionalInfoDto = storeSqlMapper.selectAdditionalInfoById(additional_info_id);
    
                map.put("additionalInfoDto", additionalInfoDto);
            }

            int order_id = orderProductDto.getOrder_id();
            StoreOrderDto storeOrderDto = storeSqlMapper.selectStoreOrderDtoById(order_id);

            map.put("orderProductDto", orderProductDto);
            map.put("storeOrderDto", storeOrderDto);
            
            OrderDeliveryInfoDto orderDeliveryInfoDto = storeSqlMapper.selectOrderDeliveryInfoByOrderProductId(order_product_id);
            map.put("orderDeliveryInfoDto", orderDeliveryInfoDto);

            result.add(map);
        }


        return result;
    }

    public Map<String, Object> getOrderProductData(int order_product_id){
        Map<String, Object> map = new HashMap<>();

        OrderProductDto orderProductDto = storeSqlMapper.selectOrderProductDtoById(order_product_id);

        int order_id = orderProductDto.getOrder_id();
        StoreOrderDto storeOrderDto = storeSqlMapper.selectStoreOrderDtoById(order_id);
        int payment_amount = storeOrderDto.getPayment_amount();

        int user_id = storeOrderDto.getUser_id();
        UserDto userDto = storeSqlMapper.selectUserDtoById(user_id);

        int price = orderProductDto.getProduct_price();
        double percentage = orderProductDto.getDiscount_percentage();
        int salePrice = (int)Math.round(price-price*percentage);

        Calendar calendar = Calendar.getInstance();
        calendar.setTime(storeOrderDto.getCreated_at());
        calendar.add(Calendar.DAY_OF_MONTH, 3);

        map.put("orderProductDto", orderProductDto);
        map.put("payment_amount", payment_amount);
        map.put("userDto", userDto);
        map.put("salePrice", salePrice);
        map.put("deliveryDeadline", calendar);
        map.put("storeOrderDto", storeOrderDto);

        return map;
    }
    
    public int getStoreIdByStoreOrderId(int order_product_id){
        return storeSqlMapper.selectStoreDtoByOrderProductId(order_product_id).getId();
    }

    public void confirmOrder(int[] order_product_ids){

        for(int order_product_id : order_product_ids){
            storeSqlMapper.updateOrderProductStatusToPreparing(order_product_id);
        }

    }

    public void insertDeliveryInfo(OrderDeliveryInfoDto orderDeliveryInfoDto){

        storeSqlMapper.insertOrderDeliveryInfo(orderDeliveryInfoDto);
        storeSqlMapper.updateOrderProductStatusToDelivered(orderDeliveryInfoDto.getOrder_product_id());

    }

    public void insertUserDeliveryInfo(UserDeliveryInfoDto userDeliveryInfoDto){
        if(userDeliveryInfoDto.getIs_default_address()==1){
            storeSqlMapper.updateDefaultDeliveryInfoByUserId(userDeliveryInfoDto.getUser_id());
        }
        storeSqlMapper.insertUserDeliveryInfo(userDeliveryInfoDto);
    }

    public List<UserDeliveryInfoDto> getUserDeliveryInfoByUserId(int user_id){
        return storeSqlMapper.selectUserDeliveryInfoByUserId(user_id);
    }

    public void deleteUserDeliveryInfoById(int id){
        storeSqlMapper.deleteUserDeliveryInfoById(id);
    }

    public boolean hasDefaultAddress(int user_id){
        return storeSqlMapper.selectDefaultAddressCountByUserId(user_id)>0;
    }

    public UserDeliveryInfoDto selectDefaultAddressByUserId(int user_id){
        return storeSqlMapper.selectDefaultAddressByUserId(user_id);
    }

    public List<Map<String, Object>> getStoreOrderDataListByUserId(int user_id, String filterOption){
        List<Map<String, Object>> result = new ArrayList<>();

        List<StoreOrderDto> storeOrderDtoList = storeSqlMapper.selectStoreOrderDtoListByUserId(user_id);

        for(StoreOrderDto storeOrderDto : storeOrderDtoList){
            Map<String, Object> orderMap = new HashMap<>();

            int order_id = storeOrderDto.getId();
            List<OrderProductDto> orderProductDtoList = storeSqlMapper.selectOrderProductListForMyOrderListPage(order_id, filterOption);

            List<Map<String, Object>> OrderProductDataList = new ArrayList<>();
            
            for(OrderProductDto orderProductDto : orderProductDtoList){
                Map<String, Object> map = new HashMap<>();
                
                int order_product_id = orderProductDto.getId();

                StoreDto storeDto = storeSqlMapper.selectStoreDtoByOrderProductId(order_product_id);

                map.put("store_name", storeDto.getName());

                int[] value_ids = storeSqlMapper.selectOrderProductOptionValueIds(order_product_id);
                List<String> valueNameList = new ArrayList<>();
                for(int value_id : value_ids){
                    String value_name = storeSqlMapper.selectOptionValueNameById(value_id);
                    valueNameList.add(value_name);
                }

                map.put("valueNameList", valueNameList);
                map.put("orderProductDto", orderProductDto);
    
                OrderProductDataList.add(map);
            }

            orderMap.put("storeOrderDto", storeOrderDto);
            orderMap.put("OrderProductDataList", OrderProductDataList);

            result.add(orderMap);
        }

        return result;
    }

    public Map<String, Object> getOrderStatusCountData(int user_id){
        Map<String, Object> map = new HashMap<>();

        map.put("orderCompleteCount", storeSqlMapper.orderCompleteCount(user_id));
        map.put("deliveryCompleteCount", storeSqlMapper.deliveryCompleteCount(user_id));
        map.put("purchaseConfirmationCount", storeSqlMapper.purchaseConfirmationCount(user_id));
        map.put("allOrderCount", storeSqlMapper.allOrderCount(user_id));
        map.put("reviewCompleteCount", storeSqlMapper.reviewCompleteCount(user_id));

        return map;
    }

    public Map<String, Object> getStoreOrderDataListByOrderId(int order_id){
        Map<String, Object> map = new HashMap<>();

        StoreOrderDto storeOrderDto = storeSqlMapper.selectStoreOrderDtoById(order_id);

        List<OrderProductDto> orderProductDtoList = storeSqlMapper.selectOrderProductListByOrderId(order_id, "전체");
        List<Map<String, Object>> orderProductDataList = new ArrayList<>();

        int product_price_sum = 0;
        
        for(OrderProductDto orderProductDto : orderProductDtoList){
            Map<String, Object> orderProductData = new HashMap<>();

            int order_product_id = orderProductDto.getId();

            StoreDto storeDto = storeSqlMapper.selectStoreDtoByOrderProductId(order_product_id);

            orderProductData.put("orderProductDto", orderProductDto);
            orderProductData.put("storeDto", storeDto);
            
            int[] value_ids = storeSqlMapper.selectOrderProductOptionValueIds(order_product_id);

            if(value_ids.length!=0){
                List<String> valueNameList = new ArrayList<>();
                for(int value_id : value_ids){
                    String value_name = storeSqlMapper.selectOptionValueNameById(value_id);
                    valueNameList.add(value_name);
                }
                orderProductData.put("valueNameList", valueNameList);

                int additional_info_id = storeSqlMapper.selectAdditionalInfoIdByValueIds(value_ids);
                AdditionalInfoDto additionalInfoDto = storeSqlMapper.selectAdditionalInfoById(additional_info_id);

                int additional_price = additionalInfoDto.getAdditional_price();
                orderProductData.put("additional_price", additional_price);

                int product_price = (orderProductDto.getProduct_price()+additional_price)*orderProductDto.getQuantity();
                product_price_sum += product_price;

            }else{
                orderProductData.put("valueNameList", null);
                int additional_price = 0;
                orderProductData.put("additional_price", additional_price);
                int product_price = (orderProductDto.getProduct_price()+additional_price)*orderProductDto.getQuantity();
                product_price_sum += product_price;
            }

            orderProductDataList.add(orderProductData);

        }

        int discount_price_sum = product_price_sum - (storeOrderDto.getPayment_amount()+storeOrderDto.getUsed_point());

        map.put("orderProductDataList", orderProductDataList);
        map.put("storeOrderDto", storeOrderDto);

        map.put("productPriceSum", product_price_sum);
        map.put("discountPriceSum", discount_price_sum);

        return map;
    }

    public List<Map<String, Object>> getStoreProductByStoreId(int store_id){
        return storeSqlMapper.selectStoreProductDataListByStoreId(store_id);
    }

    public void purchaseConfirmation(int order_product_id){
        storeSqlMapper.updateOrderProductStatusToConfirm(order_product_id);
    }

    public Map<String, Object> getOrderProductDataForReview(int order_product_id){
        Map<String, Object> map = storeSqlMapper.selectOrderProductDataForReview(order_product_id);
        
        int[] value_ids = storeSqlMapper.selectOrderProductOptionValueIds(order_product_id);
        if(value_ids.length!=0){
            List<String> valueNameList = new ArrayList<>();
            for(int value_id : value_ids){
                String value_name = storeSqlMapper.selectOptionValueNameById(value_id);
                valueNameList.add(value_name);
            }
            map.put("valueNameList", valueNameList);
        }else{
            map.put("valueNameList", null);
        }

        return map;
    }

    public void writeReview(ProductReviewDto productReviewDto){
        storeSqlMapper.insertProductReview(productReviewDto);
        int order_product_id = productReviewDto.getOrder_product_id();
        storeSqlMapper.updateOrderProductStatusToReviewComplete(order_product_id);
    }

    public List<Map<String, Object>> getPurchaseConfirmationList(int user_id){

        List<Map<String, Object>> result = new ArrayList<>();

        List<Map<String, Object>> orderProductDataList = storeSqlMapper.selectPurchaseConfirmationList(user_id);

        for(Map<String, Object> orderProudctData : orderProductDataList){

            int order_product_id = (int)orderProudctData.get("id");
            int[] valueIds = storeSqlMapper.selectOrderProductOptionValueIds(order_product_id);
            if(valueIds.length!=0){
                List<String> valueNameList = new ArrayList<>();
                for(int value_id : valueIds){
                    String value_name = storeSqlMapper.selectOptionValueNameById(value_id);
                    valueNameList.add(value_name);
                }
                orderProudctData.put("valueNameList", valueNameList);
            }else{
                orderProudctData.put("valueNameList", null);
            }
            
            result.add(orderProudctData);
        }

        return result;
    }

    public List<Map<String, Object>> getReviewCompleteList(int user_id){

        List<Map<String, Object>> result = new ArrayList<>();

        List<Map<String, Object>> reviewDataList = storeSqlMapper.selectReviewList(user_id);

        if(reviewDataList.size()!=0){
            for(Map<String, Object> orderProductData : reviewDataList){

                int order_product_id = (int)orderProductData.get("id");
                int[] valueIds = storeSqlMapper.selectOrderProductOptionValueIds(order_product_id);
                if(valueIds.length!=0){
                    List<String> valueNameList = new ArrayList<>();
                    for(int value_id : valueIds){
                        String value_name = storeSqlMapper.selectOptionValueNameById(value_id);
                        valueNameList.add(value_name);
                    }
                    orderProductData.put("valueNameList", valueNameList);
                }else{
                    orderProductData.put("valueNameList", null);
                }

                int review_id = (int)orderProductData.get("review_id");
                StoreSellerReplyDto storeSellerReplyDto = storeSqlMapper.selectStoreSellerReplyByReviewId(review_id);
                orderProductData.put("storeSellerReplyDto", storeSellerReplyDto);
                
                result.add(orderProductData);
            }
        }

        return result;
    }

    public List<Map<String, Object>> getStoreReviewList(int store_id){

        return storeSqlMapper.selectStoreReviewList(store_id);
    }

    public Map<String, Object> selectReviewData(int review_id){
        Map<String, Object> map = storeSqlMapper.selectReviewData(review_id);

        int order_product_id = (int)map.get("order_product_id");

        int[] value_ids = storeSqlMapper.selectOrderProductOptionValueIds(order_product_id);
        if(value_ids.length!=0){
            List<String> valueNameList = new ArrayList<>();
            for(int value_id : value_ids){
                String value_name = storeSqlMapper.selectOptionValueNameById(value_id);
                valueNameList.add(value_name);
            }
            map.put("valueNameList", valueNameList);
        }else{
            map.put("valueNameList", null);
        }
        map.put("currentReviewNumber", storeSqlMapper.selectCurrentReviewNumberById(review_id));
        map.put("storeSellerReplyDto", storeSqlMapper.selectStoreSellerReplyByReviewId(review_id));

        return map;
    }

    // public Map<String, Object>

    public void writeSellerReply(StoreSellerReplyDto storeSellerReplyDto){
        storeSqlMapper.insertSellerReply(storeSellerReplyDto);
    }

    // public StoreSellerReplyDto getStoreSellerReplyDto(int review_id){
    //     return storeSqlMapper.selectStoreSellerReplyByReviewId(review_id);
    // }
    
    public Map<String, Object> getOrderProductDataForRefund(int order_product_id){
        return storeSqlMapper.selectOrderProductDataForRefund(order_product_id);
    }

    public List<ProductRefundReasonDto> getRefundReasonList(){
        return storeSqlMapper.selectRefundReasonList();
    }

    public void deleteCartProductData(int cart_product_id){
        storeSqlMapper.deleteCartProduct(cart_product_id);
        storeSqlMapper.deleteCartProductOptionValueByCartProductId(cart_product_id);
    }

    public Map<String, Object> productWish(ProductWishDto productWishDto){
        Map<String, Object> map = new HashMap<>();

        int productWishCount = storeSqlMapper.selectProductWishCountByProductWishDto(productWishDto);

        if(productWishCount==0){
            storeSqlMapper.insertProductWish(productWishDto);
            map.put("isWishlisted", true);
            map.put("productWishCount", storeSqlMapper.selectProductWishCount(productWishDto.getProduct_id()));
            return map;
        }else{
            storeSqlMapper.deleteProductWish(productWishDto);
            map.put("isWishlisted", false);
            map.put("productWishCount", storeSqlMapper.selectProductWishCount(productWishDto.getProduct_id()));
            return map;
        }
        
    }

    public boolean isWishlisted(ProductWishDto productWishDto){
        return storeSqlMapper.selectProductWishCountByProductWishDto(productWishDto) > 0;
    }

    public int getProductWishCount(int product_id){
        return storeSqlMapper.selectProductWishCount(product_id);
    }

    public int orderProductRefund(ProductRefundDto productRefundDto){
        storeSqlMapper.updateOrderProductStatusToRefundComplete(productRefundDto.getOrder_product_id());
        storeSqlMapper.insertProductRefund(productRefundDto);

        return productRefundDto.getId();
    }

    public Map<String, Object> getRefundProductDataForCompletePage(int product_refund_id){
        Map<String, Object> map = storeSqlMapper.selectRefundProductDataForCompletePage(product_refund_id);
        int order_product_id = (int)map.get("order_product_id");
        int[] value_ids = storeSqlMapper.selectOrderProductOptionValueIds(order_product_id);
        if(value_ids.length!=0){
            List<String> valueNameList = new ArrayList<>();
            for(int value_id : value_ids){
                String value_name = storeSqlMapper.selectOptionValueNameById(value_id);
                valueNameList.add(value_name);
            }
            map.put("valueNameList", valueNameList);
        }else{
            map.put("valueNameList", null);
        }
        
        return map;
    }
}