package com.hiponya.bbqmall.controllers;


import com.hiponya.bbqmall.entities.member.EmailAuthEntity;
import com.hiponya.bbqmall.entities.member.UserEntity;
import com.hiponya.bbqmall.enums.CommonResult;
import com.hiponya.bbqmall.interfaces.IResult;
import com.hiponya.bbqmall.services.MemberService;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.mail.MessagingException;
import javax.servlet.http.HttpSession;
import java.security.NoSuchAlgorithmException;

@Controller
@RequestMapping("/member")
public class MemberController {

    private final MemberService memberService;

    @Autowired
    public MemberController(MemberService memberService) {
        this.memberService = memberService;
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




    @RequestMapping(value = "email", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public String postEmail(UserEntity user, EmailAuthEntity emailAuth) throws NoSuchAlgorithmException, MessagingException {
        System.out.println(user.getEmail());
        Enum<? extends IResult> result = this.memberService.sendEmailAuth(user, emailAuth);

        JSONObject responseObjects = new JSONObject();
        responseObjects.put("result", result.name().toLowerCase());
        if (result == CommonResult.SUCCESS) {
            responseObjects.put("salt", emailAuth.getSalt());
        }

        return responseObjects.toString();

    }


    @RequestMapping(value = "email", method = RequestMethod.PATCH, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public String patchEmail(EmailAuthEntity emailAuth) {
        Enum<?> result = this.memberService.verifyEmailAuth(emailAuth);
        JSONObject responseObject = new JSONObject();
        responseObject.put("result", result.name().toLowerCase());


        return responseObject.toString();
    }


    @RequestMapping(value = "register", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public String postRegister(UserEntity user, EmailAuthEntity emailAuth) throws NoSuchAlgorithmException {
        Enum<?> result =this.memberService.register(user, emailAuth);
        JSONObject responseObject = new JSONObject();
        responseObject.put("result",result.name().toLowerCase() );

//        if (result == CommonResult.SUCCESS) {
//            responseObject.put("salt", emailAuth.getSalt());
//        }
        return responseObject.toString();

        //1.memberservice가 가진 register메서드에 user및 emailauth전달하여 호출하기
        //2.1이 반환하는 결과 eunm<?>를 받아와 jsonobject 타입의 응답결과 만들기
        //3.2에서만든 jsonobject객체를 문자열화 하여 반환하기 tostring



    }



    @RequestMapping(value = "login" ,method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public String postLogin(HttpSession session, UserEntity user){
//        MediaType mediaType = new MediaType("member/login");


        Enum<? extends IResult> result = this.memberService.login(user);

        JSONObject responseObject = new JSONObject();
        responseObject.put("result", result.name().toLowerCase());

        if(result == CommonResult.SUCCESS){
            session.setAttribute("user",user);
            System.out.println("로그인 성공");
        }else System.out.println("로그인실패");

        return responseObject.toString();
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


    @ResponseBody
    @RequestMapping(value = "recoverId", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public String postRecoverEmail(UserEntity user) {
        Enum<? extends IResult> result =this.memberService.recoverId(user);

        JSONObject responseObject = new JSONObject();
        responseObject.put("result",result.name().toLowerCase() );


        if (result == CommonResult.SUCCESS) {

            responseObject.put("id", user.getId());
        }

        return responseObject.toString();// "{result: success}" 스크립트

    }


}



