package com.hiponya.bbqmall.controllers;

import com.hiponya.bbqmall.services.BbsService;
import com.hiponya.bbqmall.services.HomeService;
import org.springframework.beans.factory.annotation.Autowired;
import com.hiponya.bbqmall.entities.product.ProductEntity;
import com.hiponya.bbqmall.entities.product.ProductImageEntity;
import com.hiponya.bbqmall.services.CategoryService;
import org.springframework.boot.Banner;
import org.springframework.stereotype.Controller;

import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping(value = "/")
public class HomeController {

    private final HomeService homeService;
    private final CategoryService categoryService;

    @Autowired
    public HomeController(HomeService homeService, CategoryService categoryService) {
        this.homeService = homeService;
        this.categoryService = categoryService;
    }



    @GetMapping(value = "/" ,produces = MediaType.TEXT_HTML_VALUE)
    public ModelAndView getHome(){
        ModelAndView modelAndView = new ModelAndView("home/index");

        modelAndView.addObject("reviews", this.homeService.getProductReviews());
        modelAndView.addObject("products", this.homeService.getRecommendedProducts());


        return modelAndView;
    }




    @GetMapping(value = "catalog", produces = MediaType.TEXT_HTML_VALUE)
    public ModelAndView getCatalog() {
        ModelAndView modelAndView = new ModelAndView("member/catalog");

        return modelAndView;
    }


}