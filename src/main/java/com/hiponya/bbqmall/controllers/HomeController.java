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

    @GetMapping(value = "/register", produces = MediaType.TEXT_HTML_VALUE)
    public ModelAndView getRegister() {
        ModelAndView modelAndView = new ModelAndView("member/register");

        return modelAndView;
    }

    @GetMapping(value = "/login", produces = MediaType.TEXT_HTML_VALUE)
    public ModelAndView getLogin() {
        ModelAndView modelAndView = new ModelAndView("member/login");

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


    @RequestMapping(value = "logout" , method = RequestMethod.GET, produces = MediaType.TEXT_HTML_VALUE)
    public ModelAndView getLogout(HttpSession session){

//        session.removeAttribute("user");
        session.setAttribute("user", null);
        ModelAndView modelAndView = new ModelAndView( "redirect:./"); //리다이렉션

        System.out.println("로그아웃");
        return modelAndView;
    }


    @GetMapping(value = "recover", produces = MediaType.TEXT_HTML_VALUE)
    public ModelAndView getRecover() {
        ModelAndView modelAndView = new ModelAndView("member/recover");

        return modelAndView;
    }


}