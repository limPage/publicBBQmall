package com.hiponya.bbqmall.mappers;

import com.hiponya.bbqmall.entities.member.UserEntity;
import com.hiponya.bbqmall.entities.product.*;
import com.hiponya.bbqmall.vos.product.ProductReadVo;
import com.hiponya.bbqmall.vos.product.WishlistVo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.web.bind.annotation.RequestParam;

@Mapper
public interface ICategoryMapper {
    CategoryEntity selectCategoryIndexByDetailIndex(@Param(value = "detail_index") int detailIndex);

    CategoryEntity selectCategoryByIndex(@Param(value = "index") int index);

    CategoryEntity[] selectCategories();

    ProductEntity selectProductByIndex(@Param(value = "pid") int pid);

    CategoryEntity selectCategories2(@Param(value = "index") int index);

    SortEntity selectSort(@Param(value = "sid") int sid);

    ProductEntity selectProductSortingByRanking(@Param(value = "sid") int sid);

    ProductReadVo[] selectProducts(@Param(value = "cid") int cid);



    ProductEntity[] selectEightProducts();
    ProductEntity[] selectSecondEightProducts();
    ProductEntity[] selectThirdEightProducts();

    SortEntity[] selectSorts();

    WishlistEntity selectWishlist(@Param(value = "index") int index);

    WishlistVo[] selectWishlists(@Param(value = "id") String id);

    CartEntity selectCartByIndex(@Param(value = "cid") int cid);

    UserEntity selectUserById(@Param(value = "id") String id);

    ProductEntity selectSaleQuantityByIndex(@Param(value = "index") int index);

    Integer selectWishlistSumPriceByUserId(@Param(value = "id") String id);

    Integer selectWishlistSumSalePriceByUserId(@Param(value = "id") String id);

    Integer selectWishlistSumQuantityByUserId(@Param(value = "id") String id);


    int insertWishlistByIndex(WishlistEntity wishlist);

    int insertWishlistOrder(OrderEntity order);

    int insertOrder(OrderEntity order);

    int deleteWishlistByIndex(@Param(value = "index") int index);

    ProductReadVo[] selectProductsByDetailIndex(@RequestParam(value = "detailIndex") int detailIndex);

    ProductImageEntity[] selectProductImagesByProductIndexExceptData(@Param(value = "productIndex") int productIndex);

    DetailImageEntity[] selectDetailImagesByProductIndexExceptData(@Param(value = "productIndex") int productIndex);

    ProductImageEntity selectProductImageByIndex(@Param(value = "index") int index);
    DetailImageEntity selectDetailImageByIndex(@Param(value = "index") int index);

}
