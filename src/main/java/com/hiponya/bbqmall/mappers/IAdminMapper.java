package com.hiponya.bbqmall.mappers;


import com.hiponya.bbqmall.entities.product.DetailImageEntity;
import com.hiponya.bbqmall.entities.product.ProductEntity;
import com.hiponya.bbqmall.entities.product.ProductImageEntity;
import com.hiponya.bbqmall.entities.product.StatusLookupEntity;
import com.hiponya.bbqmall.vos.product.ProductReadVo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.web.bind.annotation.RequestParam;

@Mapper
public interface IAdminMapper {



   int insertProduct(ProductEntity product);

   int insertProductImage(ProductImageEntity productImage);
   int insertDetailImage(DetailImageEntity detailImage);
   int insertStatusLookup(StatusLookupEntity statusLookup);
   ProductReadVo[] selectProductsByDetailIndex(@RequestParam (value = "detailIndex") int detailIndex);

   ProductImageEntity[] selectProductImagesByProductIndexExceptData(@Param(value = "productIndex") int productIndex);


   ProductImageEntity selectProductImageByIndex(@Param(value = "index") int index);

}
