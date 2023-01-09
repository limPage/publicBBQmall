package com.hiponya.bbqmall.mappers;

import com.hiponya.bbqmall.entities.product.*;
import com.hiponya.bbqmall.vos.product.WishlistVo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface ICategoryMapper {
    CategoryEntity selectCategoryIndexByDetailIndex(@Param(value = "detail_index") int detailIndex);

    CategoryEntity selectCategoryByIndex(@Param(value = "index") int index);

    CategoryEntity[] selectCategories();

    ProductEntity selectProductByIndex(@Param(value = "pid") int pid);

    CategoryEntity selectCategories2(@Param(value = "index") int index);

    ProductEntity[] selectProducts(@Param(value = "cid") int cid);

    SortEntity[] selectSorts();

    WishlistEntity selectWishlist(@Param(value = "id") String id);

    WishlistVo[] selectWishlists(@Param(value = "id") String id);

    CartEntity selectCartByIndex(@Param(value = "cid") int cid);

    ProductEntity selectSaleQuantityByIndex(@Param(value = "index") int index);

    Integer selectWishlistSumPriceByUserId(@Param(value = "id") String id);

    Integer selectWishlistSumSalePriceByUserId(@Param(value = "id") String id);


    int insertWishlistByIndex(@Param(value="id") String id,
                              @Param(value="productIndex") int productIndex,
                              @Param(value = "quantity") int quantity);



}
