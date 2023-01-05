package com.hiponya.bbqmall.controllers;


import com.hiponya.bbqmall.entities.member.EmailAuthEntity;
import com.hiponya.bbqmall.entities.member.UserEntity;
import com.hiponya.bbqmall.entities.member.WithdrawalEntity;
import com.hiponya.bbqmall.enums.CommonResult;
import com.hiponya.bbqmall.enums.bbs.ModifyResult;
import com.hiponya.bbqmall.interfaces.IResult;
import com.hiponya.bbqmall.services.MemberService;
import com.hiponya.bbqmall.vos.member.EmailAuthVo;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.method.annotation.SessionAttributesHandler;
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

            UserEntity isAdminMode= this.memberService.isAdminMode(user);
            System.out.println("어드민이에요?"+isAdminMode.isAdmin());
            user.setAdmin(isAdminMode.isAdmin());
            user.setEmail(isAdminMode.getEmail());
            user.setName(isAdminMode.getName());
            user.setBirth(isAdminMode.getBirth());
            user.setContact(isAdminMode.getContact());
            user.setAddressPostal(isAdminMode.getAddressPostal());
            user.setAddressPrimary(isAdminMode.getAddressPrimary());
            user.setAddressSecondary(isAdminMode.getAddressSecondary());

            session.setAttribute("user",user);
            System.out.println("로그인 성공");
        }else System.out.println("로그인실패");

        return responseObject.toString();
    }


    @RequestMapping(value = "logout" , method = RequestMethod.GET, produces = MediaType.TEXT_HTML_VALUE)
    public ModelAndView getLogout(HttpSession session){

//        session.removeAttribute("user");
        session.setAttribute("user", null);
        ModelAndView modelAndView = new ModelAndView( "redirect:/"); //리다이렉션

        System.out.println("로그아웃");
        return modelAndView;
    }


    @GetMapping(value = "recover", produces = MediaType.TEXT_HTML_VALUE)
    public ModelAndView getRecover(@RequestParam (value = "find") String find) {
        ModelAndView modelAndView = new ModelAndView("member/recover");
        modelAndView.addObject("find", find);
        return modelAndView;
    }


    @ResponseBody
    @RequestMapping(value = "recover", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public String postRecoverEmail(UserEntity user) {
        Enum<? extends IResult> result =this.memberService.recoverId(user);

        JSONObject responseObject = new JSONObject();
        responseObject.put("result",result.name().toLowerCase() );


        if (result == CommonResult.SUCCESS) {

            responseObject.put("id", user.getId());
        }

        return responseObject.toString();// "{result: success}" 스크립트

    }


    @ResponseBody
    @RequestMapping(value = "recoverPassword", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public String postRecoverPassword(EmailAuthVo emailAuthVo) throws MessagingException {
        Enum<?> result =this.memberService.recoverPasswordSend(emailAuthVo);

        JSONObject responseObject = new JSONObject();
        responseObject.put("result",result.name().toLowerCase() );

        if (result == CommonResult.SUCCESS) {
            responseObject.put("index", emailAuthVo.getIndex());
        }
        return responseObject.toString();// "{result: success}" 스크립트

    }


    @RequestMapping(value = "recoverPasswordEmail", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public String postRecoverPasswordEmail(EmailAuthEntity emailAuth){
        Enum<?> result = this.memberService.recoverPasswordCheck(emailAuth);
        JSONObject responseObject = new JSONObject();
        responseObject.put("result", result.name().toLowerCase());

        if(result == CommonResult.SUCCESS){
            responseObject.put("code",emailAuth.getCode());
            responseObject.put("salt", emailAuth.getSalt());
        }

        System.out.println(emailAuth.getIndex());
        return responseObject.toString();

    }

    @RequestMapping(value = "recoverPasswordEmail", method = RequestMethod.GET, produces = MediaType.TEXT_HTML_VALUE)
    public ModelAndView getRecoverPasswordEmail(EmailAuthEntity emailAuth){
        //  this.memberService.updateRecoverPasswordAuth(emailAuth);
        Enum<?> result= this.memberService.recoverPasswordAuth(emailAuth);
        ModelAndView modelAndView = new ModelAndView("member/recoverPasswordEmail");
        modelAndView.addObject("result",result.name());
        return modelAndView;
    }

    @RequestMapping(value = "recoverPassword", method = RequestMethod.PATCH, produces = MediaType.TEXT_HTML_VALUE)
    @ResponseBody
    public String patchRecoverPassword(EmailAuthEntity emailAuth, UserEntity user){
        Enum<?> result = this.memberService.recoverPassword(emailAuth ,user);
        JSONObject responseObject = new JSONObject();
        responseObject.put("result", result.name().toLowerCase());

        return responseObject.toString();
    }


    @GetMapping(value = "csCenter",produces = MediaType.TEXT_HTML_VALUE)
    public ModelAndView getCsCenter(){
        ModelAndView modelAndView = new ModelAndView("member/csCenter");
        return modelAndView;
    }


    @GetMapping(value = "/myPage" , produces = MediaType.TEXT_HTML_VALUE)
    public ModelAndView getMyPage(){

        return new ModelAndView("myPage/myPage");

    }


    @GetMapping(value = "/withdrawal" , produces = MediaType.TEXT_HTML_VALUE)
    public ModelAndView getMyPageWithdrawal(){

        return new ModelAndView("myPage/myPageWithdrawal");

    }

    @ResponseBody
    @RequestMapping(value = "/withdrawal", method = RequestMethod.DELETE, produces = MediaType.APPLICATION_JSON_VALUE)
    public String  deleteUser(@SessionAttribute (value = "user" ,required = false)UserEntity user, WithdrawalEntity withdrawal, HttpSession session){
//        System.out.println("id="+user.getId());

        JSONObject responseObject = new JSONObject();
        Enum<? extends IResult> result = this.memberService.deleteUser(user, withdrawal);

        responseObject.put("result", result.name().toLowerCase());

        if (result==CommonResult.SUCCESS){
            session.setAttribute("user",null);
        }
        return responseObject.toString();
    }

    @GetMapping(value = "/modifyMyInfo" , produces = MediaType.TEXT_HTML_VALUE)
    public ModelAndView getModifyMyInfo(){

        return new ModelAndView("myPage/modifyMyInfo");

    }

    @ResponseBody
    @RequestMapping(value = "modifyMyInfo", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public String postPasswordCheck(@SessionAttribute (value = "user",required = false)UserEntity user,
                                    UserEntity userInfo){
        JSONObject responseObject = new JSONObject();
        if (user==null){
            responseObject.put("result", ModifyResult.NOT_SIGNED.name().toLowerCase()) ;
        }else {
            Enum<?> result=this.memberService.login(userInfo);
            responseObject.put("result",result.name().toLowerCase()) ;
        }
        return responseObject.toString();

    }

    @ResponseBody
    @RequestMapping(value = "modifyMyInfo", method = RequestMethod.PATCH, produces = MediaType.APPLICATION_JSON_VALUE)
    public String patchMyInfo(@SessionAttribute (value = "user",required = false)UserEntity user,HttpSession session, UserEntity userInfo){
        JSONObject responseObject = new JSONObject();
        if (user==null){
            responseObject.put("result",ModifyResult.NOT_SIGNED.name().toLowerCase()) ;
        }else {
            Enum<?> result=this.memberService.updateUserInfo(user,userInfo);
            responseObject.put("result",result.name().toLowerCase()) ;
            if (result==CommonResult.SUCCESS){
                session.setAttribute("user",null);
            }
        }
        return responseObject.toString();
    }
}




