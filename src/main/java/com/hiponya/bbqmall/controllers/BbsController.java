package com.hiponya.bbqmall.controllers;


import com.hiponya.bbqmall.entities.bbs.*;
import com.hiponya.bbqmall.entities.member.UserEntity;
import com.hiponya.bbqmall.enums.CommonResult;
import com.hiponya.bbqmall.enums.bbs.WriteResult;
import com.hiponya.bbqmall.interfaces.IResult;
import com.hiponya.bbqmall.models.PagingModel;
import com.hiponya.bbqmall.services.BbsService;
import com.hiponya.bbqmall.vos.bbs.BpReadVo;
import com.hiponya.bbqmall.vos.bbs.NoticeReadVo;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import java.io.IOException;

@Controller
@RequestMapping(value = "board")
public class BbsController {


    private final BbsService bbsService;


    @Autowired
    public BbsController(BbsService bbsService) {
        this.bbsService = bbsService;
    }


    @RequestMapping(value = "/", method = RequestMethod.GET, produces = MediaType.TEXT_HTML_VALUE)
    public ModelAndView getBoard(@SessionAttribute(value = "user", required = false) UserEntity user,
                                @RequestParam(value = "bid", required = false) String bid,
                                @RequestParam(value = "page", required = false, defaultValue = "1") Integer page,
                                @RequestParam(value = "keyword", required = false, defaultValue = "") String keyword,
                                 @RequestParam(value = "qid" ,required = false) String qid,
                                 @RequestParam(value = "bbid" ,required = false) String bbid) {
        //페이지 안보내줫을때는 펄즈, 펄즈도 인식하게 인티저, 펄즈일시 디폴트 1
        page=Math.max(1,page);


        ModelAndView modelAndView = new ModelAndView("board/board");


//        if(bid==null|| bid.equals("all")) {
//
//            int totalCount = this.bbsService.getNoticeCountAll( keyword);
//
//            PagingModel paging = new PagingModel(totalCount, page);
//            modelAndView.addObject("paging", paging); //게시글개수
//            NoticeReadVo[] notice = this.bbsService.getNoticeAll(paging,  keyword);
//            modelAndView.addObject("notice", notice);
//            modelAndView.addObject("user", user);
//        }
        NoticeBoardEntity noticeBoard = new NoticeBoardEntity(); //게시판 선택안하거나 전채선택일경우 빈껍데기



                     if (bid!=null && !bid.equals("all") ) {


                  noticeBoard = this.bbsService.getNoticeBoard(bid);
             }

                if (bid!=null &&bid.equals("bp") ){
                    System.out.println("bbid는"+bbid);
//                    int totalCount = this.bbsService.getBpArticleCount(bbid, keyword);
                    int totalCount = this.bbsService.getBpArticleCount(bbid, keyword);
                    PagingModel paging = new PagingModel(totalCount, page);
//
                    BpReadVo[] bpArticles = this.bbsService.getBpArticles( paging, keyword, bbid);
//
                    modelAndView.addObject("bid",bid);
                    modelAndView.addObject("bpArticles",bpArticles);

                    modelAndView.addObject("paging", paging); //게시글개수



                }else {


                    int totalCount = this.bbsService.getNoticeCount(noticeBoard, keyword, qid);
                    PagingModel paging = new PagingModel(totalCount, page);


                    if (bid != null && bid.equals("qna")) {
                        QnaAnswerEntity[] answer = this.bbsService.getAnswer();
                        modelAndView.addObject("answer", answer); //게시글개수

                        paging = new PagingModel(20, totalCount, page);
                        System.out.println("qid는" + qid);
                        System.out.println("qid 키워드는=" + keyword);
                        modelAndView.addObject("qid", qid);

                    }


                    modelAndView.addObject("paging", paging); //게시글개수

                    NoticeReadVo[] notice = this.bbsService.getNotice(noticeBoard, paging, keyword, qid);
                    NoticeReadVo[] announceNotice = this.bbsService.getAnnounceNotice();

                    modelAndView.addObject("announceNotice", announceNotice);
                    modelAndView.addObject("notice", notice);
                    System.out.println("bid는" + bid);
                    modelAndView.addObject("bid", bid);


                    modelAndView.addObject("user", user);


//        else{
//            NoticeBoardEntity noticeBoard = this.bbsService.getNoticeBoard(bid);
////            modelAndView.addObject("board", noticeBoard);//뺄지말지##########################
//
//            if (noticeBoard != null) {
//                int totalCount = this.bbsService.getNoticeCount(noticeBoard, keyword);
//
//                PagingModel paging = new PagingModel(totalCount, page);
//                modelAndView.addObject("paging", paging); //게시글개수
//
//                NoticeReadVo[] notice = this.bbsService.getNotice(noticeBoard, paging, keyword);
//
//
//                modelAndView.addObject("notice", notice);
//                modelAndView.addObject("user", user);
//
//            }
//        }
                }
        return modelAndView;
    }








    @GetMapping(value = "/readNotice" ,produces = MediaType.TEXT_HTML_VALUE)
    public  ModelAndView getNotice( @RequestParam(value = "bid", required = false) String bid,
                                     @RequestParam(value = "nid", required = false) Integer nid,
                                   @RequestParam(value = "bbid", required = false) Integer bbid){
        ModelAndView modelAndView = new ModelAndView("board/readNotice");


        if(bid.equals("bpArticle")){
            modelAndView.addObject("bid",bid);//이게 무슨 보드꺼를 읽느냐 알려줌
          BpReadVo bpArticle=  this.bbsService.readBpArticle(bbid);


            modelAndView.addObject("bpArticle",bpArticle);//이게 무슨 보드꺼를 읽느냐 알려줌

            return modelAndView;

        }else {
            modelAndView.addObject("bid",bid);//이게 무슨 보드꺼를 읽느냐 알려줌

            NoticeReadVo notice = this.bbsService.readNotice(nid);
            modelAndView.addObject("notice", notice);

        if (notice != null) { //공지가 있다면 어떤 보드 공지인지

            modelAndView.addObject("board", this.bbsService.getNoticeBoard(notice.getBoardId()));
        }
    }
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





    //이미지 읽어오기
    @RequestMapping(value = "image", method = RequestMethod.GET)
    public ResponseEntity<byte[]> getImage(@RequestParam(value = "idd") int id) { //이미지 자체로 보여주려면 리스판스 엔티티

        ImageEntity image = this.bbsService.getImage(id); //-1 -99를 넣으면 null
        if (image == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", image.getFileMime()); //파일형식을 결정하는 header의 마임타입을 받는게 중요하다


        return new ResponseEntity<>(image.getData(), headers, HttpStatus.OK);
    }


    //이미지 붙이기
    @RequestMapping(value = "image", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public String postImage(@RequestParam(value = "upload") MultipartFile file) throws IOException {
        ImageEntity image = new ImageEntity();
        image.setFileName(file.getOriginalFilename());
        image.setFileMime(file.getContentType());
        image.setData(file.getBytes());


        Enum<?> result = this.bbsService.addImage(image);
        JSONObject responseObject = new JSONObject();
        responseObject.put("result", result.name().toLowerCase());
        if (result == CommonResult.SUCCESS) {

            responseObject.put("url", "http://localhost:8080/board/image?idd=" + image.getIndex()); //idd값으로 get이 들어감

        }


        return responseObject.toString();
    }

    @GetMapping(value = "modifyNotice", produces = MediaType.TEXT_HTML_VALUE)
    public ModelAndView getModifyNotice(@SessionAttribute(value = "user", required = false)UserEntity user,
                                        @RequestParam(value = "nid", required = false) Integer nid,
                                        @RequestParam(value = "bbid",required = false) Integer bbid,
                                        @RequestParam(value = "bid") String bid){
        ModelAndView modelAndView =new ModelAndView("board/modifyNotice");

        if (bid.equals("notice")) {
            NoticeReadVo existingNotice = this.bbsService.readNotice(nid);
            Enum<?> result = this.bbsService.prepareModifyNotice(user, nid);
            System.out.println("리절트는" + result.name());

            modelAndView.addObject("result", result.name());

            if (result == CommonResult.SUCCESS) {
                modelAndView.addObject("notice", existingNotice);
            }
        } else if (bid.equals("bp")) {

            BpReadVo existingBpArticle = this.bbsService.readBpArticle(bbid);
            Enum<?> result = this.bbsService.prepareModifyBpArticle(user,bbid);
            modelAndView.addObject("result", result.name());
            if (result == CommonResult.SUCCESS) {
                modelAndView.addObject("BpArticle", existingBpArticle);
            }

        }

        modelAndView.addObject("bid", bid);
        return modelAndView;
    }


    @RequestMapping(value = "modifyNotice", method = RequestMethod.PATCH, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public String patchModify(@SessionAttribute(value = "user") UserEntity user , NoticeReadVo notice) {
        JSONObject responseObject = new JSONObject();



        Enum<?> result = this.bbsService.modifyNotice(notice, user);


        responseObject.put("result", result.name().toLowerCase());

        if (result == CommonResult.SUCCESS) {
            responseObject.put("nid", notice.getIndex());
        }
        return responseObject.toString();

    }
    @RequestMapping(value = "modifyBpArticle", method = RequestMethod.PATCH, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public String patchyModifyBpArticle(@SessionAttribute(value = "user") UserEntity user , BpArticleEntity bpArticle) {
        JSONObject responseObject = new JSONObject();

        Enum<?> result = this.bbsService.modifyBpArticle(bpArticle, user);


        responseObject.put("result", result.name().toLowerCase());

        if (result == CommonResult.SUCCESS) {
            responseObject.put("bbid", bpArticle.getIndex());
        }
        return responseObject.toString();

    }


    @ResponseBody
    @RequestMapping(value = "deleteNotice", method = RequestMethod.DELETE, produces = MediaType.APPLICATION_JSON_VALUE)
    public String deleteNotice(@SessionAttribute(value = "user", required = false) UserEntity user,
                             @RequestParam(value = "nid") int nid
//                             ArticleEntity article 주소로 받아왔기때문에 파람을 쓴다.
    ) {
        NoticeEntity notice = new NoticeEntity();
        notice.setIndex(nid);
        JSONObject responseObject = new JSONObject();

        Enum<?> result = this.bbsService.deleteNotice(notice, user);
        responseObject.put("result", result.name().toLowerCase());
//
//        if (result == CommonResult.SUCCESS) {
//
//            responseObject.put("bid", notice.getBoardId());
//
//        }

        return responseObject.toString();
    }



    @RequestMapping(value = "/writeBp", method = RequestMethod.GET, produces = MediaType.TEXT_HTML_VALUE)
    public ModelAndView getBulkPurchase(@SessionAttribute(value = "user", required = false) UserEntity user) { //컨트로롤러에서 셋아트리뷰트 한 user값을 가져온다. 세션에서 가져온 값이다. required false가 있어야 null일때 userEntity값을 안요구하게된다. 400예방 false일때 null이 드감
        //비드를 문자열로 전달
        ModelAndView modelAndView;
        if (user == null) {//로그인확인
            modelAndView = new ModelAndView("redirect:/member/login");
        } else {
            modelAndView = new ModelAndView("board/writeBulkPurchase");


        }
        return modelAndView;
    }

    @RequestMapping(value = "writeBp", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public String postWrite(@SessionAttribute(value = "user", required = false) UserEntity user,
                            BpArticleEntity bpArticle) {//컨트로롤러에서 셋아트리뷰트 한 user값을 가져온다. 세션에서 가져온 값이다. required false가 있어야 null일때 userEntity값을 안요구하게된다. 400예방
        Enum<? extends IResult> result;//bid값으로 받아와서 그것을 id로 지정해준다.
        JSONObject responseObject = new JSONObject();

        BpBoardEntity board= new BpBoardEntity();


        System.out.println("contact는"+bpArticle.getContact());
        if (user == null) {
            result = WriteResult.NOT_ALLOWED;
        } else {
//            bpArticle.setBpBoardId(bid);
            System.out.println(bpArticle.getBpBoardId());
//            notice.setUserEmail(user.getEmail());
            result = this.bbsService.writeBpArticle(bpArticle);

            if (result == CommonResult.SUCCESS) {

                responseObject.put("index", bpArticle.getIndex());//인트의 기본값은 0이다.

            }
        }
        responseObject.put("result", result.name().toLowerCase());
        return responseObject.toString();
    }


    @RequestMapping(value = "writeAdminComment", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public String postAdminComment(@SessionAttribute(value = "user", required = false) UserEntity user,
                              @RequestParam(value = "bbid" ,required = false) Integer bbid, AdminCommentEntity adminComment){
        Enum<? extends IResult> result;
        JSONObject responseObject = new JSONObject();

        System.out.println(bbid);
            result = this.bbsService.writeAdminComment(user, bbid, adminComment);

            if (result == CommonResult.SUCCESS) {

//                responseObject.put("index", adminComment.getIndex());//인트의 기본값은 0이다.

            }

        responseObject.put("result", result.name().toLowerCase());

        return responseObject.toString();

    }
}
