package com.hiponya.bbqmall.controllers;

import com.hiponya.bbqmall.entities.member.UserEntity;
import com.hiponya.bbqmall.entities.product.ProductEntity;
import com.hiponya.bbqmall.enums.admin.AdminResult;
import com.hiponya.bbqmall.exception.RollbackException;
import com.hiponya.bbqmall.services.AdminService;
import com.hiponya.bbqmall.vos.product.ProductReadVo;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
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
    public ModelAndView getReadProduct(){
        ModelAndView modelAndView = new ModelAndView("admin/readProduct");

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


    @GetMapping(value = "review")
    public ProductReadVo[] getProduct(@RequestParam(value = "detailIndex") int detailIndex){

//        JSONArray  jsonArray = new JSONArray();
        return this.adminService.getProducts(detailIndex);
    }

}