package com.bulmeong.basecamp.store.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

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
import com.bulmeong.basecamp.user.dto.MileageLogDto;
import com.bulmeong.basecamp.user.dto.UserDto;

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
    public int selectOptionValueIdByData(@Param("product_id")int product_id, @Param("option_name") String option_name, @Param("value_name") String value_name);

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

    public int selectProductWishCountByProductWishDto(ProductWishDto productWishDto);
    public void insertProductWish(ProductWishDto productWishDto);
    public void deleteProductWish(ProductWishDto productWishDto);

    public List<ProductOptionNameDto> selectOptionNameListByProductId(int product_id);
    public List<ProductOptionValueDto> selectOptionValueListByOptionId(int option_id);

    public int selectNextOptionId(@Param("product_id")int product_id, @Param("offset") int offset);

    public int selectAdditionalInfoIdByValueIds(@Param("valueIds") int[] valueIds);
    public int countOrderProductByOptionValueIds(@Param("orderProductOptionValueIds") int[] orderProductOptionValueIds, @Param("optionValueCount") int optionValueCount);
    public int selectPurchaseCountByOptionValueIds(@Param("orderProductOptionValueIds") int[] orderProductOptionValueIds, @Param("optionValueCount") int optionValueCount);

    public int selectPurchaseCountByProductId(int product_id);

    public AdditionalInfoDto selectAdditionalInfoById(int additional_info_id);

    public void insertCartProduct(CartProductDto cartProductDto);
    public void insertCartProductOptionValue(CartProductOptionValueDto cartProductOptionValueDto);

    //장바구니
    public List<String> selectStoreNamesOfCartProdcutByUserId(int user_id);
    public List<CartProductDto> selectCartProductDtoList(int user_id);
    public StoreDto selectStoreDtoByCartProductId(int cart_product_id);

    public Map<String, Object> selectCartProductDataById(int cart_product_id);
    public int[] selectCartProductOptionValueIds(int cart_product_id);

    public String selectOptionValueNameById(int value_id);

    public void insertPendingOrder(StoreOrderDto storeOrderDto);
    public CartProductDto selectCartProductById(int cart_product_id);
    public void insertOrderProduct(OrderProductDto orderProductDto);
    public void insertProductOptionValue(OrderProductOptionValueDto orderProductOptionValueDto);

    //주문서
    public List<String> selectStoreNamesOfPendingOrderByUserId(int user_id);
    public List<OrderProductDto> selectPendingOrderProductDtoList(int user_id);
    public StoreDto selectStoreDtoByOrderProductId(int order_product_id);

    public Map<String, Object> selectOrderProductDataById(int order_product_id);
    public int[] selectOrderProductOptionValueIds(int order_product_id);
    public int[] selectOrderProductOptionValuePrimaryKeys(int order_product_id);

    public List<StoreOrderDto> selectPendingOrderDtoListByUserId(int user_id);
    public List<OrderProductDto> selectPendingOrderProductListByOrderId(int order_id);
    public void deletePendingOrderById(int store_order_id);
    public void deletePendingOrderProductById(int order_product_id);
    public void deleteOrderProductOptionValueById(int id);

    public void updateStoreOrder(StoreOrderDto storeOrderDto);
    public void updateOrderProduct(OrderProductDto orderProductDto);

    public void insertPointUsageLog(MileageLogDto mileageLogDto);
    public void updateUserMileage(@Param("id") int id, @Param("used_point") int used_point);
    public void updateUserMileagePlus(int id);

    public int[] selectCartProductOptionValuePrimaryKeys(int cart_product_id);

    public void deleteCartProduct(int id);
    public void deleteCartProductOptionValue(int id);
    public void deleteCartProductOptionValueByCartProductId(int cart_product_id);

    public StoreOrderDto selectStoreOrderDtoById(int id);

    public List<OrderProductDto> selectOrderProductListByOrderId(@Param("order_id") int order_id, @Param("filterOption") String filterOption);
    public List<OrderProductDto> selectOrderProductListForMyOrderListPage(@Param("order_id") int order_id, @Param("filterOption") String filterOption);

    public List<OrderProductDto> selectOrderProductListByStoreId(int store_id);

    public OrderProductDto selectOrderProductDtoById(int id);

    public UserDto selectUserDtoById(int id);

    public void updateOrderProductStatusToPreparing(int id);
    public void insertOrderDeliveryInfo(OrderDeliveryInfoDto orderDeliveryInfoDto);
    public void updateOrderProductStatusToDelivered(int id);
    public OrderDeliveryInfoDto selectOrderDeliveryInfoByOrderProductId(int order_product_id);

    public void insertUserDeliveryInfo(UserDeliveryInfoDto userDeliveryInfoDto);
    public List<UserDeliveryInfoDto> selectUserDeliveryInfoByUserId(int user_id);
    public void updateDefaultDeliveryInfoByUserId(int user_id);
    public void deleteUserDeliveryInfoById(int id);

    public UserDeliveryInfoDto selectDefaultAddressByUserId(int user_id);
    public int selectDefaultAddressCountByUserId(int user_id);

    public List<StoreOrderDto> selectStoreOrderDtoListByUserId(int user_id);

    public int orderCompleteCount(int user_id);
    public int deliveryCompleteCount(int user_id);
    public int purchaseConfirmationCount(int user_id);
    public int allOrderCount(int user_id);
    public int reviewCompleteCount(int user_id);

    public List<StoreProductDto> selectStoreProductByStoreId(int store_id);

    public List<Map<String, Object>> selectStoreProductDataListByStoreId(int store_id);

    public void updateOrderProductStatusToConfirm(int order_product_id);

    public Map<String, Object> selectOrderProductDataForReview(int order_product_id);

    public void insertProductReview(ProductReviewDto productReviewDto);

    public void updateOrderProductStatusToReviewComplete(int order_product_id);

    public List<Map<String, Object>> selectPurchaseConfirmationList(int user_id);
    public List<Map<String, Object>> selectReviewList(int user_id);
    public List<Map<String, Object>> selectStoreReviewList(int store_id);

    public Map<String, Object> selectReviewData(int review_id);
    public int selectCurrentReviewNumberById(int review_id);
    // public int selecReviewIdByOffset(@Param("store_id") int store_id, @Param("offset") int offset);

    public StoreSellerReplyDto selectStoreSellerReplyByReviewId(int review_id);
    public void insertSellerReply(StoreSellerReplyDto storeSellerReplyDto);

    public Map<String, Object> selectOrderProductDataForRefund(int order_product_id);

    public List<ProductRefundReasonDto> selectRefundReasonList();

    public void updateOrderProductStatusToRefundComplete(int id);

    public void insertProductRefund(ProductRefundDto productRefundDto);

    public Map<String, Object> selectRefundProductDataForCompletePage(int id);

    public void insertProductImage(ProductImageDto productImageDto);
    public List<ProductImageDto> selectProductImageDtoListByProductId(int product_id);

    public int selectProductPurchaseQuantity(int product_id);

    public List<Map<String, Object>> selectProductReviewDataListByProductId(int product_id);

    public void insertReviewPoint(int user_id);

    public int selectCartProductCountByProductId(CartProductDto cartProductDto);
    public void deleteCartProductByProductId(CartProductDto cartProductDto);

    public Integer selectCartProductIdByOptionValueIds(
        @Param("valueIds") int[] valueIds,
        @Param("optionValueCount") int optionValueCount,
        @Param("product_id") int product_id,
        @Param("user_id") int user_id
    );

    public void updateCartProductQuantity(@Param("quantity")int quantity, @Param("cart_product_id") int cart_product_id);
}