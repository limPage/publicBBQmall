package com.hiponya.bbqmall.controllers;

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

    @GetMapping(value = "/" ,produces = MediaType.TEXT_HTML_VALUE)
    public ModelAndView getHome(){
        ModelAndView modelAndView = new ModelAndView("home/index");

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