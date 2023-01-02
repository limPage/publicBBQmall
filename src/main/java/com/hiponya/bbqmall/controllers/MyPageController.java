package com.hiponya.bbqmall.controllers;


import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping(value = "myPage")
public class MyPageController {


    @GetMapping(value = "/" , produces = MediaType.TEXT_HTML_VALUE)
    public ModelAndView getMyPage(){

        return new ModelAndView("myPage/myPage");
    }
}
