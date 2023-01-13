package com.hiponya.bbqmall.services;

import com.hiponya.bbqmall.entities.member.EmailAuthEntity;
import com.hiponya.bbqmall.entities.member.UserEntity;
import com.hiponya.bbqmall.entities.product.*;
import com.hiponya.bbqmall.enums.CommonResult;
import com.hiponya.bbqmall.enums.category.WishlistDeleteResult;
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

    public Enum<? extends IResult> insertOrder(UserEntity user, OrderEntity order) {
        UserEntity existingUser = this.categoryMapper.selectUserById(user.getId());
        if(existingUser == null) {
            return CommonResult.FAILURE;
        }
        return this.categoryMapper.insertOrder(order) > 0 ? CommonResult.SUCCESS : CommonResult.FAILURE;
    }

    public Enum<? extends IResult> insertWishlistOrder(UserEntity user, OrderEntity order) {
        UserEntity existingUser = this.categoryMapper.selectUserById(user.getId());
        if(existingUser == null) {
            return CommonResult.FAILURE;
        }
        return this.categoryMapper.insertWishlistOrder(order) > 0 ? CommonResult.SUCCESS : CommonResult.FAILURE;
    }

    public Enum<? extends IResult> insertWishlist(WishlistEntity wishlist) {

        ProductEntity product = this.categoryMapper.selectProductByIndex(wishlist.getProductIndex());

        if(product == null) {
            return CommonResult.FAILURE; // 게시글이 없다면 FAILURE
        }
        return this.categoryMapper.insertWishlistByIndex(wishlist) > 0 ? CommonResult.SUCCESS : CommonResult.FAILURE;
    }

    public Integer getWishlistSumPrice(String id) {
        return this.categoryMapper.selectWishlistSumPriceByUserId(id);
    }

    public Integer getWishlistSumSalePrice(String id) {
        return this.categoryMapper.selectWishlistSumSalePriceByUserId(id);
    }

    public Integer getWishlistSumQuantity(String id) {
        return this.categoryMapper.selectWishlistSumQuantityByUserId(id);
    }

    public ProductEntity getProductByIndex(int pid) {

        return this.categoryMapper.selectProductByIndex(pid);
    }

    public Enum<? extends IResult> deleteWishlist(WishlistEntity wishlist, UserEntity user) {
        // 성공, 실패, 로그인이 안되었고 삭제하려는 댓글이 니 댓글이 아닌경우(너 = 세션으로 확인), 댓글이 존재하지 않음(null이 반환될 경우)
        WishlistEntity existingWishlist = this.categoryMapper.selectWishlist(wishlist.getIndex()); // wishlist.getIndex() = 자바스크립트에서 넘어온 index 값
        if(existingWishlist == null) {
            return WishlistDeleteResult.NO_SUCH_WISHLIST;
        }

        return this.categoryMapper.deleteWishlistByIndex(wishlist.getIndex()) > 0 ? CommonResult.SUCCESS : CommonResult.FAILURE;
    }

    public CartEntity getCartByIndex(int cid) {
        return this.categoryMapper.selectCartByIndex(cid);
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

    public WishlistEntity[] getWishlists(String id) {
        return this.categoryMapper.selectWishlists(id);
    }

    public ProductEntity[] getEightProducts() {
        return this.categoryMapper.selectEightProducts();
    }
    public ProductEntity[] getSecondEightProducts() {
        return this.categoryMapper.selectSecondEightProducts();
    }
    public ProductEntity[] getThirdEightProducts() {
        return this.categoryMapper.selectThirdEightProducts();
    }


}
