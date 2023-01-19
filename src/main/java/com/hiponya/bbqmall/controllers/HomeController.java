package com.hiponya.bbqmall.controllers;

import com.hiponya.bbqmall.services.BbsService;
import com.hiponya.bbqmall.services.HomeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpSession;

@Controller
@RequestMapping(value = "/")
public class HomeController {

    private final HomeService homeService;


    @Autowired
    public HomeController(HomeService homeService) {
        this.homeService = homeService;
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

    @GetMapping(value = "category", produces = MediaType.TEXT_HTML_VALUE)
    public ModelAndView getCategory() {
        ModelAndView modelAndView = new ModelAndView("home/category");

        return modelAndView;
    }




}