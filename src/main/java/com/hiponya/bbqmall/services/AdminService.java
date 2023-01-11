package com.hiponya.bbqmall.services;


import com.hiponya.bbqmall.entities.bbs.BpArticleEntity;
import com.hiponya.bbqmall.entities.member.UserEntity;
import com.hiponya.bbqmall.entities.product.DetailImageEntity;
import com.hiponya.bbqmall.entities.product.ProductEntity;
import com.hiponya.bbqmall.entities.product.ProductImageEntity;
import com.hiponya.bbqmall.entities.product.StatusLookupEntity;
import com.hiponya.bbqmall.enums.CommonResult;
import com.hiponya.bbqmall.enums.admin.AdminResult;
import com.hiponya.bbqmall.enums.bbs.ModifyResult;
import com.hiponya.bbqmall.exception.RollbackException;
import com.hiponya.bbqmall.interfaces.IResult;
import com.hiponya.bbqmall.mappers.IAdminMapper;
import com.hiponya.bbqmall.vos.product.ProductReadVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Arrays;
import java.util.Date;

import static java.util.Arrays.stream;

@Service(value = "com.hiponya.bbqmall.services.AdminService")
public class AdminService {


    private final IAdminMapper adminMapper;


    @Autowired
    public AdminService(IAdminMapper adminMapper) {

        this.adminMapper = adminMapper;
    }

//    public PlaceEntity getPlace(int index){
//        return this.dataMapper.selectPlacesByIndex(index);
//    }
//
//
//    public PlaceVo[] getPlaces(double minLat, double minLng, double maxLat, double maxLng){
//           return this.dataMapper.selectPlacesExceptImage(minLat, minLng, maxLat, maxLng);
//
//    }

    @Transactional
    public Enum<? extends IResult> CreatProduct(UserEntity user, ProductEntity product, MultipartFile[] images, MultipartFile[] detailImages) throws IOException, RollbackException {
        if (user ==null){
            return  AdminResult.NOT_SIGNED;
        }
//        review.setUserId(user.getId());
        if(this.adminMapper.insertProduct(product)==0){
            return CommonResult.FAILURE;
        }
        if(!user.isAdmin()){
            return AdminResult.NOT_ALLOWED;
        }

        if (images !=null && images.length>0){
            for(MultipartFile image : images){
                ProductImageEntity productImage = new ProductImageEntity();
                productImage.setReviewIndex(product.getProductIndex());
                productImage.setData(image.getBytes());
                productImage.setType(image.getContentType());
                if(this.adminMapper.insertProductImage(productImage)==0){
                    throw new RollbackException();
                }

            }
        }

        if (detailImages !=null && detailImages.length>0){
            for(MultipartFile image : detailImages){
                DetailImageEntity detailImage = new DetailImageEntity();
                detailImage.setReviewIndex(product.getProductIndex());
                detailImage.setData(image.getBytes());
                detailImage.setType(image.getContentType());
                if(this.adminMapper.insertDetailImage(detailImage)==0){
                    throw new RollbackException();
                }

            }
        }
        StatusLookupEntity statusLookup = new StatusLookupEntity();
        statusLookup.setStatus("CREAT");
        statusLookup.setText(product.getProductName());
        if (this.adminMapper.insertStatusLookup(statusLookup)==0){
            throw new RollbackException();
        }

        return CommonResult.SUCCESS;
    }



    public ProductReadVo[] getProducts(String detailIndex){

        ProductReadVo[] products =this.adminMapper.selectProductsByDetailIndex(detailIndex);
        for (ProductReadVo product :products){
            ProductImageEntity[] productImages = this.adminMapper.selectProductImagesByProductIndexExceptData(product.getProductIndex());
            int[] productImageIndexes = stream(productImages).mapToInt(ProductImageEntity::getIndex).toArray();
            product.setImageIndexes(productImageIndexes);


        }
        return products;
    }

    public ProductImageEntity getProductImage (int index){

        return this.adminMapper.selectProductImageByIndex(index);
    }

    public ProductReadVo getProduct(int pid){
        ProductReadVo product =this.adminMapper.selectProductByProductIndex(pid);



            ProductImageEntity[] productImages = this.adminMapper.selectProductImagesByProductIndexExceptData(product.getProductIndex());
            int[] productImageIndexes = stream(productImages).mapToInt(ProductImageEntity::getIndex).toArray();
            product.setImageIndexes(productImageIndexes);



        return product;
    }

    public Enum<? extends IResult> modifyProduct(ProductEntity product, UserEntity user, MultipartFile[] images, MultipartFile[] detailImages ,int imageChange )throws IOException, RollbackException  {
        ProductEntity existingProduct = this.adminMapper.selectProductByProductIndex(product.getProductIndex());

//        review.setUserId(user.getId());

        //로그인 안했다면 실패
        if (user == null) {
            return AdminResult.NOT_SIGNED;
        }

        if (existingProduct == null) { //게시글이 없으면 실패
            return AdminResult.NO_SUCH_ARTICLE;

            //수정 시작
        }
        if( !user.isAdmin()) {
            return  ModifyResult.NOT_ALLOWED;
        }
        product.setModifiedOn(new Date());
        product.setCreatedOn(existingProduct.getCreatedOn());
        product.setView(existingProduct.getView());

        if(this.adminMapper.updateProduct(product)==0){
            return CommonResult.FAILURE;
        }


      //이미지가 새로 삽입되었을 경우
        if(imageChange==1){//이미지 선택버튼을 눌러 삭제에 동의하였다.
            System.out.println("수정시 이미지 삭제 수:"+this.adminMapper.deleteProductImageByProductIndex(product.getProductIndex()));
            if (images !=null && images.length>0){
                for(MultipartFile image : images){
                    ProductImageEntity productImage = new ProductImageEntity();
                    productImage.setReviewIndex(product.getProductIndex());
                    productImage.setData(image.getBytes());
                    productImage.setType(image.getContentType());
                    if(this.adminMapper.insertProductImage(productImage)==0){
                        throw new RollbackException();
                    }
                }
            }

            if (detailImages !=null && detailImages.length>0){
                for(MultipartFile image : detailImages){
                    DetailImageEntity detailImage = new DetailImageEntity();
                    detailImage.setReviewIndex(product.getProductIndex());
                    detailImage.setData(image.getBytes());
                    detailImage.setType(image.getContentType());
                    if(this.adminMapper.insertDetailImage(detailImage)==0){
                        throw new RollbackException();
                    }

                }
            }

        }

        StatusLookupEntity statusLookup = new StatusLookupEntity();
        statusLookup.setStatus("MODIFY");
        statusLookup.setText(product.getProductName());
        if (this.adminMapper.insertStatusLookup(statusLookup)==0){
            throw new RollbackException();
        }

        return CommonResult.SUCCESS;

    }


}
