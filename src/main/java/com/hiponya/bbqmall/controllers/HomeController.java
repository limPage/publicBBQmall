package com.hiponya.bbqmall.controllers;

import com.hiponya.bbqmall.services.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping(value = "/")
public class HomeController {

    private final MemberService memberService;

    @Autowired
    public HomeController(MemberService memberService) {
        this.memberService = memberService;
    }
    @GetMapping(value = "/" ,produces = MediaType.TEXT_HTML_VALUE)
    public ModelAndView getHome(){
        ModelAndView modelAndView = new ModelAndView("member/login");

        return modelAndView;
    }

}