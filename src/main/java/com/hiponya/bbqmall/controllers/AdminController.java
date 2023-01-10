package com.hiponya.bbqmall.controllers;

import com.hiponya.bbqmall.entities.bbs.ImageEntity;
import com.hiponya.bbqmall.entities.member.UserEntity;
import com.hiponya.bbqmall.entities.product.ProductEntity;
import com.hiponya.bbqmall.entities.product.ProductImageEntity;
import com.hiponya.bbqmall.enums.admin.AdminResult;
import com.hiponya.bbqmall.exception.RollbackException;
import com.hiponya.bbqmall.services.AdminService;
import com.hiponya.bbqmall.vos.product.ProductReadVo;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import java.io.IOException;

@Controller
@RequestMapping(value = "/admin")
public class AdminController {


    private final AdminService adminService;

    @Autowired
    public AdminController(AdminService adminService){
        this.adminService= adminService;
    }


    @GetMapping(value = "/" ,produces = MediaType.TEXT_HTML_VALUE)
    public ModelAndView getAdmin(){
        ModelAndView modelAndView = new ModelAndView("admin/admin");

        return modelAndView;
    }

    @GetMapping(value = "/create" ,produces = MediaType.TEXT_HTML_VALUE)
    public ModelAndView getCreateProduct(){
        ModelAndView modelAndView = new ModelAndView("admin/createProduct");

        return modelAndView;
    }

    @GetMapping(value = "/read" ,produces = MediaType.TEXT_HTML_VALUE)
    public ModelAndView getReadProduct(@RequestParam (value = "keyword" ,required = false) String keyword,
                                       @RequestParam (value = "detailIndex" ,required = false) Integer detailIndex){
        ModelAndView modelAndView = new ModelAndView("admin/readProduct");


        if (detailIndex!=null){
            ProductReadVo[] products= this.adminService.getProducts(detailIndex);
         modelAndView.addObject("products", products);
        }

        return modelAndView;
    }


    @GetMapping(value = "/update" ,produces = MediaType.TEXT_HTML_VALUE)
    public ModelAndView getUpdateProduct(){
        ModelAndView modelAndView = new ModelAndView("admin/updateProduct");

        return modelAndView;
    }
    @GetMapping(value = "/delete" ,produces = MediaType.TEXT_HTML_VALUE)
    public ModelAndView getDeleteProduct(){
        ModelAndView modelAndView = new ModelAndView("admin/deleteProduct");

        return modelAndView;
    }



    @PostMapping(value = "create")
    @ResponseBody
    public String postReview(@SessionAttribute(value = "user" ,required = false) UserEntity user,
                             @RequestParam(value = "images", required = false) MultipartFile[] images,
                             @RequestParam(value = "detailImages", required = false) MultipartFile[] detailImages,
                             ProductEntity product )throws IOException {
        JSONObject responseObject = new JSONObject();
        Enum<?> result;

        try{

            result = this.adminService.CreatProduct(user,product,images,detailImages);
        }catch (RollbackException ignored){
            result = AdminResult.FAILURE;
        }
        responseObject.put("result", result.name().toLowerCase());
        return responseObject.toString();
    }

    @GetMapping(value = "productImage")
    public ResponseEntity<byte[]> getReviewImage(@RequestParam(value = "index") int index){

        ResponseEntity<byte[]> responseEntity ;


        ProductImageEntity reviewImage = this.adminService.getProductImage(index);

        if(reviewImage==null){
            responseEntity = new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }else {
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.valueOf(reviewImage.getType()));
            headers.setContentLength(reviewImage.getData().length);
            responseEntity = new ResponseEntity<>(reviewImage.getData(),headers, HttpStatus.OK);
        }
        return responseEntity;
    }
//    @RequestMapping(value = "productImage", method = RequestMethod.GET)
//    public ResponseEntity<byte[]> getImage(@RequestParam(value = "index") int index) { //이미지 자체로 보여주려면 리스판스 엔티티
//
//        System.out.println(index);
//        ProductImageEntity productImage = this.adminService.getProductImage(index); //-1 -99를 넣으면 null
//        if (productImage == null) {
//            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
//        }
//        HttpHeaders headers = new HttpHeaders();
//        headers.add("Content-Type", productImage.getType()); //파일형식을 결정하는 header의 마임타입을 받는게 중요하다
//
//
//        return new ResponseEntity<>(productImage.getData(), headers, HttpStatus.OK);
//    }


//    @GetMapping(value = "product")
//    public ProductReadVo[] getProduct(@RequestParam(value = "detailIndex") int detailIndex){
//
////        JSONArray  jsonArray = new JSONArray();
//        return this.adminService.getProducts(detailIndex);
//    }

}