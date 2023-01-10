package com.hiponya.bbqmall.mappers;


import com.hiponya.bbqmall.entities.product.DetailImageEntity;
import com.hiponya.bbqmall.entities.product.ProductEntity;
import com.hiponya.bbqmall.entities.product.ProductImageEntity;
import com.hiponya.bbqmall.entities.product.StatusLookupEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.web.bind.annotation.RequestParam;

@Mapper
public interface IAdminMapper {



   int insertProduct(ProductEntity product);

   int insertProductImage(ProductImageEntity productImage);
   int insertDetailImage(DetailImageEntity detailImage);
   int insertStatusLookup(StatusLookupEntity statusLookup);
   ProductEntity[] selectProductsByDetailIndex(@RequestParam (value = "detailIndex") int detailIndex);

   ProductImageEntity[] selectReviewImagesByReviewIndexExceptData(@Param(value = "productIndex") int productIndex);

}
