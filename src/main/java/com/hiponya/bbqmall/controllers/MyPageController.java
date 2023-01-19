package com.hiponya.bbqmall.controllers;


import com.hiponya.bbqmall.entities.member.UserEntity;
import com.hiponya.bbqmall.entities.member.WithdrawalEntity;
import com.hiponya.bbqmall.interfaces.IResult;
import com.hiponya.bbqmall.services.MemberService;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpSession;

@Controller
@RequestMapping(value = "myPage")
public class MyPageController {


    private final MemberService memberService;

    @Autowired
    public MyPageController(MemberService memberService) {
        this.memberService = memberService;
    }





}
