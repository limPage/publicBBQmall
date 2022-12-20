package com.hiponya.bbqmall.controllers;

import com.hiponya.bbqmall.entities.product.CategoryEntity;
import org.springframework.stereotype.Controller;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping(value = "/")
public class HomeController {

    @GetMapping(value = "/" ,produces = MediaType.TEXT_HTML_VALUE)
    public ModelAndView getHome(CategoryEntity category){
        ModelAndView modelAndView = new ModelAndView("home/index");
        modelAndView.addObject("category", category);
        return modelAndView;
    }

    @GetMapping(value = "register", produces = MediaType.TEXT_HTML_VALUE)
    public ModelAndView getRegister() {
        ModelAndView modelAndView = new ModelAndView("member/register");

        return modelAndView;
    }


    @GetMapping(value = "view", produces = MediaType.TEXT_HTML_VALUE)
    public ModelAndView getDetailMenu() {
        ModelAndView modelAndView = new ModelAndView("home/view");

        return modelAndView;
    }



}