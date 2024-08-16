package com.bulmeong.basecamp.secondHandProduct.mapper;

import com.bulmeong.basecamp.secondHandProduct.dto.*;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface ProductSqlMapper {
    // 상품등록
    public void insertSecondhandProduct(SecondhandProductDto secondhandProductDto);
    // 상품업데이트
    public void updateSecondhandProduct(SecondhandProductDto secondhandProductDto);
    // 메인페이지 전체 게시글 리스트
    public List<AllContentsProductDto> selectSecondhandProductList();
    public List<AllContentsProductDto> selectSecondhandProductIsAreaList(String polygon_name);

    // 상품 거래상태 업데이트
    public void updateProductStatus(AllContentsProductDto allContentsProductDto);

    // 상품 정보 디테일
    public AllContentsProductDto selectSecondhandDetailProduct(int product_id);
    // 상품 조회수
    public void updateSecondhandDetailProductCount(int product_id);

    // 이미지 등록
    public void insertSecondhandProductImg(ImageDto imageDto);
    // 이미지 삭제
    public void deleteImageByUrl(String image_url);
    // 이미지 리스트
    public List<ImageDto> selectSecondhandProductImgList(int product_id);

    // 카테고리 리스트
    public List<CategoryDto> selectCategoryList();
    // 메인페이지 카테고리별 게시글 리스트
    public List<AllContentsProductDto> selectSecondhandProductByCategoryList(int category_id);
    // 카테고리별 리스트 목록 카테고리 이름
    public String selectCategoryName(int category_id);

    // 관심상품
    public void insertProductLike(WishListDto wishListDto);
    public void deleteProductByUserLike(WishListDto wishListDto);
    public int selectProductTotalLikeCount(int product_id);
    public int selectProductByUserLike(WishListDto wishListDto);
    public List<AllContentsProductDto> selectSecondhandProductByWishList(int user_id);

    // 게시글 수정,삭제
    public void deletePost(int product_id);

    // 채팅방
    public SecondhandProductDto selectChatRoomProductInformation(int product_id);

//마이페이지
    // 판매내역 - 판매중
    public List<AllContentsProductDto> selectSalesProduct(int user_id, String status);
    // 판매중 게시글 수
    public int getTotalSales(int user_id, String status);
    // 판매내역 - 거래완료
    public List<AllContentsProductDto> selectTransactionCompleteProduct(int user_id, String status);
    // 거래완료 게시글 수
    public int getTotalTransactionComplete(int user_id, String status);
    // 거래완료 -> 구매자 리스트
    public List<ProductBuyerDto> getProductBuyerList(ProductBuyerDto productBuyerDto);

//거래후기
    // 좋아요 후기 선택
    public List<LikeReviewDto> selectLikeReviewList();
    // 싫어요 후기 선택
    public List<UnlikeReviewDto> selectUnlikeReviewList();

}
