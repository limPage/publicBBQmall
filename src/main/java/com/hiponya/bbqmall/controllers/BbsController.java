package com.hiponya.bbqmall.controllers;


import com.hiponya.bbqmall.entities.board.NoticeEntity;
import com.hiponya.bbqmall.entities.member.UserEntity;
import com.hiponya.bbqmall.enums.CommonResult;
import com.hiponya.bbqmall.enums.bbs.WriteResult;
import com.hiponya.bbqmall.interfaces.IResult;
import com.hiponya.bbqmall.services.BbsService;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping(value = "board")
public class BbsController {


    private final BbsService bbsService;


    @Autowired
    public BbsController(BbsService bbsService) {
        this.bbsService = bbsService;
    }



    @GetMapping(value = "/" ,produces = MediaType.TEXT_HTML_VALUE)
    public  ModelAndView getBoard(){
        ModelAndView modelAndView = new ModelAndView("board/board");
        return modelAndView;
    }


    @GetMapping(value = "/notice" ,produces = MediaType.TEXT_HTML_VALUE)
    public  ModelAndView getNotice(){
        ModelAndView modelAndView = new ModelAndView("board/readNotice");
        return modelAndView;
    }


    @RequestMapping(value = "/writeNotice", method = RequestMethod.GET, produces = MediaType.TEXT_HTML_VALUE)
    public ModelAndView getWrite(@SessionAttribute(value = "user", required = false) UserEntity user) { //컨트로롤러에서 셋아트리뷰트 한 user값을 가져온다. 세션에서 가져온 값이다. required false가 있어야 null일때 userEntity값을 안요구하게된다. 400예방 false일때 null이 드감
        //비드를 문자열로 전달
        ModelAndView modelAndView;
        if (user == null) {//로그인확인
            modelAndView = new ModelAndView("redirect:/member/login");
        } else {
            modelAndView = new ModelAndView("board/writeNotice");


        }
        return modelAndView;
    }








    @RequestMapping(value = "writeNotice", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public String postWrite(@SessionAttribute(value = "user", required = false) UserEntity user,
                            @RequestParam(value = "bid", required = false) String bid,
                            NoticeEntity notice) {//컨트로롤러에서 셋아트리뷰트 한 user값을 가져온다. 세션에서 가져온 값이다. required false가 있어야 null일때 userEntity값을 안요구하게된다. 400예방
        Enum<? extends IResult> result;//bid값으로 받아와서 그것을 id로 지정해준다.
        JSONObject responseObject = new JSONObject();

        if (user == null) {
            result = WriteResult.NOT_ALLOWED;
        } else if (bid == null) {
            result = WriteResult.NO_SUCH_BOARD;
        } else {
            notice.setBoardId(bid);
            System.out.println(notice.getBoardId());
//            notice.setUserEmail(user.getEmail());
            result = this.bbsService.writeNotice(notice);

            if (result == CommonResult.SUCCESS) {

                responseObject.put("index", notice.getIndex());//인트의 기본값은 0이다.


            }


        }

        responseObject.put("result", result.name().toLowerCase());
        return responseObject.toString();
    }

}
