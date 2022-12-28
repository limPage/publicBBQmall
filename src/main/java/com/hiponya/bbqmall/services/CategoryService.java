package com.hiponya.bbqmall.services;

import com.hiponya.bbqmall.entities.member.EmailAuthEntity;
import com.hiponya.bbqmall.entities.product.CategoryEntity;
import com.hiponya.bbqmall.entities.product.ProductEntity;
import com.hiponya.bbqmall.entities.product.SortEntity;
import com.hiponya.bbqmall.enums.CommonResult;
import com.hiponya.bbqmall.enums.member.CategoryResult;
import com.hiponya.bbqmall.enums.member.VerifyEmailAuthResult;
import com.hiponya.bbqmall.interfaces.IResult;
import com.hiponya.bbqmall.mappers.ICategoryMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Date;

@Service(value = "com.hiponya.bbqmall.services.CategoryService")
public class CategoryService {

    private final ICategoryMapper categoryMapper;

    @Autowired
    public CategoryService(ICategoryMapper categoryMapper) {
        this.categoryMapper = categoryMapper;
    }

    public CategoryEntity getCategoryIndex(int index) {
        return this.categoryMapper.selectCategoryByIndex(index);
    }

    public Enum<? extends IResult> getProductQuantity (int index) {
        ProductEntity existingProduct = this.categoryMapper.selectSaleQuantityByIndex(index);
        if(existingProduct == null) {
            return CategoryResult.NO_PRODUCTS;
        }
        return CommonResult.SUCCESS;
    }

    @Transactional
    public Enum<? extends IResult> sendCategoryIndex(CategoryEntity category, ProductEntity product) {

        CategoryEntity existingCategory = this.categoryMapper.selectCategoryIndexByDetailIndex(product.getDetailIndex());
        if (existingCategory == null) {
            return CommonResult.FAILURE;
        }
        if(category.getIndex() != product.getDetailIndex()) {
            return CategoryResult.NO_PRODUCTS;
        }
        if(category.getTitle() == null) {
            return CategoryResult.NO_CATEGORY;
        }

        return CommonResult.SUCCESS;
    }

    public ProductEntity getProductByIndex(int pid) {

        return this.categoryMapper.selectProductByIndex(pid);
    }

    public CategoryEntity[] getCategories() {
        return this.categoryMapper.selectCategories();
    }

    public CategoryEntity getCategories2(int cid) {
        return this.categoryMapper.selectCategories2(cid);
    }

    public ProductEntity[] getProducts(int cid) {
        return this.categoryMapper.selectProducts(cid);
    }

    public SortEntity[] getSorts() {
        return this.categoryMapper.selectSorts();
    }


}
