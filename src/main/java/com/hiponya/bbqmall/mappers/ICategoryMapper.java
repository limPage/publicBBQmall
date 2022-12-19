package com.hiponya.bbqmall.mappers;

import com.hiponya.bbqmall.entities.product.CategoryEntity;
import com.hiponya.bbqmall.entities.product.ProductEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface ICategoryMapper {
    CategoryEntity selectCategoryIndexByDetailIndex(@Param(value = "detail_index") int detailIndex);

    CategoryEntity selectCategoryByIndex(@Param(value = "index") int index);

    CategoryEntity[] selectCategories();

    ProductEntity[] selectProducts();


}
