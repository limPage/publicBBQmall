package com.hiponya.bbqmall.controllers;

import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping(value = "/admin")
public class AdminController {

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


}