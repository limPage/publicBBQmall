package com.hiponya.bbqmall.services;

import com.hiponya.bbqmall.entities.bbs.*;
import com.hiponya.bbqmall.entities.member.UserEntity;
import com.hiponya.bbqmall.enums.CommonResult;
import com.hiponya.bbqmall.enums.bbs.DeleteResult;
import com.hiponya.bbqmall.enums.bbs.ModifyArticleResult;
import com.hiponya.bbqmall.enums.bbs.WriteCommentResult;
import com.hiponya.bbqmall.enums.bbs.WriteResult;
import com.hiponya.bbqmall.interfaces.IResult;
import com.hiponya.bbqmall.mappers.IBbsMapper;
import com.hiponya.bbqmall.models.PagingModel;
import com.hiponya.bbqmall.vos.bbs.BpReadVo;
import com.hiponya.bbqmall.vos.bbs.NoticeReadVo;
import org.apache.commons.lang3.time.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;

import java.util.Date;

@Service(value = "com.hiponya.services.BbsService")

public class BbsService {


    private final IBbsMapper bbsMapper;


    @Autowired
    public BbsService(TemplateEngine templateEngine, IBbsMapper bbsMapper) {

        this.bbsMapper = bbsMapper;
    }

    public NoticeBoardEntity getNoticeBoard(String id) {
        return this.bbsMapper.selectNoticeBoardId(id);
    }


    public Enum<? extends IResult> writeNotice(NoticeEntity notice) {
//        article.setWrittenOn(new Date());
//        article.setModifiedOn(new Date());

        NoticeBoardEntity board = this.bbsMapper.selectNoticeBoardId(notice.getBoardId());
        if (board == null) {
            return WriteResult.NO_SUCH_BOARD;
        }

            //글쓸때 글의 새 개시물 알림뜰 기간을 내일까지로 정한다.
        notice.setExpirationDate(DateUtils.addDays(new Date(), 1));
//        if(this.bbsMapper.insertArticle(article)==0){
//            return CommonResult.FAILURE;

//        }
//        return CommonResult.SUCCESS;

        return this.bbsMapper.insertNotice(notice) > 0 ? CommonResult.SUCCESS : CommonResult.FAILURE;
    }


    public Enum<? extends IResult> addImage(ImageEntity image) {

        return this.bbsMapper.insertImage(image) > 0 ? CommonResult.SUCCESS : CommonResult.FAILURE;

    }

    public ImageEntity getImage(int index) {

        return this.bbsMapper.selectImageByIndex(index);

    }


    //페이징을 불러온다
    public int getNoticeCount(NoticeBoardEntity board, String keyword,String qid) { //검색조건이 크리테리온 검색어가 키워드 보드가 어느 게시판
//        if (criterion == null || keyword == null) { //검색어가 없을시  게시판 전체
//            return this.bbsMapper.selectArticleCountByBoardId(board.getId());
//        } else if (criterion.equals("title")) {
//            return this.bbsMapper.selectArticleCountByBoardIdTitle(board.getId(), keyword);
//
//        } else if (criterion.equals("nickname")) {
//            return this.bbsMapper.selectArticleCountByBoardIdNickname(board.getId(), keyword);
//        } else {
//            return this.bbsMapper.selectArticleCountByBoardIdTitleContent(board.getId(), keyword);
//        }
        System.out.println("공지종류는="+board.getId());
        System.out.println("keyword="+keyword);
        return this.bbsMapper.selectNoticeCountByNoticeBoardId(board.getId() ,keyword ,qid);

    }
    public int getBpArticleCount(String bbid, String keyword) { //검색조건이 크리테리온 검색어가 키워드 보드가 어느 게시판
//
        System.out.println("bp보드keyword="+keyword);
        return this.bbsMapper.selectBpArticleCountByBpBoardId(bbid ,keyword );

    }

public NoticeReadVo[] getAnnounceNotice(){
        return this.bbsMapper.selectAnnounceNotice();
}

            //공지게시판을 불러온다
    public NoticeReadVo[] getNotice(NoticeBoardEntity board, PagingModel paging,  String keyword, String qid) {

        //만들어진지 1일이 지난 게시물의 새게시물 여부를 새로고침 한다.
        System.out.println("업데이트된 수 "+this.bbsMapper.updateNoticeIsNew());

        return this.bbsMapper.selectNoticeByBoardId(board.getId(),
                keyword,
                paging.countPerPage,
                (paging.requestPage - 1) * (paging.countPerPage),
                qid);

    }
    //공지게시판을 불러온다 항목을 선택하지 않았을때 전체

//    public NoticeReadVo[] getNoticeAll(PagingModel paging,  String keyword) {
//
//        return this.bbsMapper.selectNoticeAll( keyword, paging.countPerPage,(paging.requestPage - 1) * (paging.countPerPage) );
//
//    }


    public BpReadVo[] getBpArticles(PagingModel paging, String keyword, String bbid) {

        //만들어진지 1일이 지난 게시물의 새게시물 여부를 새로고침 한다.

        return this.bbsMapper.selectBpArticleByBoardId(bbid, keyword, paging.countPerPage, (paging.requestPage - 1) * (paging.countPerPage));

    }

    //게시물에 들어가는 동시에 조회수를 올려줌

    public NoticeReadVo readNotice(int index) {
        NoticeReadVo notice = this.bbsMapper.selectNoticeByIndex(index);
        if (notice != null) {
            notice.setView(notice.getView() + 1);
            this.bbsMapper.updateNotice(notice);
        }

        return notice;

    }
    public BpReadVo readBpArticle(int bbid) {
        return this.bbsMapper.selectBpArticleByBoardIdJustOne(bbid);

    }

    //작성 공지 수정하기
    public Enum<? extends IResult> modifyNotice(NoticeEntity notice, UserEntity user) {
        System.out.println("인덱스는"+notice.getIndex());
        NoticeEntity existingArticle = this.bbsMapper.selectNoticeByIndex(notice.getIndex());

        //로그인 안했다면 실패
        if (user == null) {
            return ModifyArticleResult.NOT_SIGNED;
        }
    //    공지로 등록하려고 왔을 경우
        if(notice.isImportant()!=null){
            //관리지가 아니면 권한이 없음을 알린다.
            existingArticle.setImportant(notice.isImportant());


            if( !user.isAdmin()) {
                return  ModifyArticleResult.NOT_ALLOWED;
            }

        }


        if (existingArticle == null) { //게시글이 없으면 실패

            return ModifyArticleResult.NO_SUCH_ARTICLE;

                //수정 시작
        } else {
            //게시물 수정인경우->(단순 공지 등록이아님) 타이틀과 컨텐츠가 있음
            if(notice.getTitle()!=null){

                existingArticle.setContent(notice.getContent());
                existingArticle.setTitle(notice.getTitle());


            }


            existingArticle.setModifiedOn(new Date());
            existingArticle.setImportant(notice.isImportant());


            return this.bbsMapper.updateNotice(existingArticle) > 0 ? CommonResult.SUCCESS : CommonResult.FAILURE;
        }
    }

    public Enum<? extends IResult> modifyBpArticle(BpArticleEntity bpArticle, UserEntity user) {
        System.out.println("인덱스는"+bpArticle.getIndex());
        BpArticleEntity existingBpArticle = this.bbsMapper.selectBpArticleByBoardIdJustOne(bpArticle.getIndex());

        //로그인 안했다면 실패
        if (user == null) {
            return ModifyArticleResult.NOT_SIGNED;
        }

        if (existingBpArticle == null) { //게시글이 없으면 실패

            return ModifyArticleResult.NO_SUCH_ARTICLE;

            //수정 시작
        }   if( !user.getId().equals(existingBpArticle.getId())) {
            return  ModifyArticleResult.NOT_ALLOWED;
        }

        else {
            //게시물 수정인경우->(단순 공지 등록이아님) 타이틀과 컨텐츠가 있음



            bpArticle.setModifiedOn(new Date());


            return this.bbsMapper.updateBpArticle(bpArticle) > 0 ? CommonResult.SUCCESS : CommonResult.FAILURE;
        }
    }


    public Enum<? extends IResult> prepareModifyNotice(UserEntity user, int nid) {

        NoticeReadVo existingNotice = this.bbsMapper.selectNoticeByIndex(nid);

        if (user == null) {
            return ModifyArticleResult.NOT_SIGNED;  //로그인이 안되어있거나, 관리자가 아닐경우
        }

        if (existingNotice == null) { //게시글이 없으면

            return ModifyArticleResult.NO_SUCH_ARTICLE;
        }
        if (!user.isAdmin()) {   //권한이 없으면

            return ModifyArticleResult.NOT_ALLOWED;       //리턴 값을 줍니다
        }


        return CommonResult.SUCCESS;
    }
    public Enum<? extends IResult> prepareModifyBpArticle(UserEntity user, int bbid) {

        BpReadVo existingBpArticle = this.bbsMapper.selectBpArticleByBoardIdJustOne(bbid);

        if (user == null) {
            return ModifyArticleResult.NOT_SIGNED;  //로그인이 안되어있거나, 관리자가 아닐경우
        }

        if (existingBpArticle == null) { //게시글이 없으면

            return ModifyArticleResult.NO_SUCH_ARTICLE;
        }
        if (!user.getId().equals(existingBpArticle.getId())) {   //권한이 없으면

            return ModifyArticleResult.NOT_ALLOWED;       //리턴 값을 줍니다
        }


        return CommonResult.SUCCESS;
    }


    public Enum<? extends IResult> deleteNotice(NoticeEntity notice, UserEntity user) {
        NoticeEntity existingArticle = this.bbsMapper.selectNoticeByIndex(notice.getIndex());

        if (existingArticle == null) {
            return WriteResult.NO_SUCH_BOARD; //게시물이 없다
        }

        if (user == null || !user.isAdmin()) {
            return WriteResult.NOT_ALLOWED; //권한이 없다
        }

//        notice.setUserEmail(user.getEmail());
//        notice.setBoardId(existingArticle.getBoardId());

        return this.bbsMapper.deleteNoticeByIndex(notice.getIndex()) > 0 ? CommonResult.SUCCESS : CommonResult.FAILURE;
    }

    public Enum<? extends IResult> deleteBpArticle(int bbid, UserEntity user) {
        BpArticleEntity bpArticle = this.bbsMapper.selectBpArticleByBoardIdJustOne(bbid);

        if (bpArticle == null) {
            return DeleteResult.NO_SUCH_ARTICLE; //게시물이 없다
        }
        if (user == null || !user.getId().equals(bpArticle.getId())) {
            return DeleteResult.NOT_ALLOWED; //권한이 없다
        }
        return this.bbsMapper.deleteBpArticleByIndex(bpArticle.getIndex()) > 0 ? CommonResult.SUCCESS : CommonResult.FAILURE;
    }

    public Enum<? extends IResult> deleteAdminComment(UserEntity user,int bbid, int acIndex ) {
        BpArticleEntity existingBpArticle = this.bbsMapper.selectBpArticleByBoardIdJustOne(bbid);//글이 없다

        AdminCommentEntity adminComment = this.bbsMapper.selectAdminCommentByIndexJustOne(acIndex);//수정할 댓글이 없다

        if (adminComment == null || existingBpArticle==null) {
            return DeleteResult.NO_SUCH_ARTICLE; //게시물이 없다
        }

        if (user == null || !user.isAdmin()) {
            return DeleteResult.NOT_ALLOWED; //권한이 없다
        }
        if( this.bbsMapper.deleteAdminCommentByIndex(acIndex) > 0 ){


            existingBpArticle.setCommentCount(existingBpArticle.getCommentCount()-1);
            if(this.bbsMapper.updateBpArticle(existingBpArticle)>0){
                return  CommonResult.SUCCESS;
            }
        }
        return CommonResult.FAILURE;

    }





    public QnaAnswerEntity[] getAnswer(){

       return this.bbsMapper.selectAnswers();
    }






    public Enum<? extends IResult> writeBpArticle(BpArticleEntity bpArticle) {
//        article.setWrittenOn(new Date());
//        article.setModifiedOn(new Date());

        BpBoardEntity bpBoard = this.bbsMapper.selectBpBoardById(bpArticle.getBpBoardId());
        if (bpBoard == null) {
            return WriteResult.NO_SUCH_BOARD;
        }



        return this.bbsMapper.insertBpArticle(bpArticle) > 0 ? CommonResult.SUCCESS : CommonResult.FAILURE;
    }

    public Enum<? extends IResult> writeAdminComment(UserEntity user, int bbid, AdminCommentEntity adminComment ) {
//        article.setWrittenOn(new Date());
//        article.setModifiedOn(new Date());
            //유저가 null
                //어드민이 아닐경우
        //게시물이 없을경우

        if (user==null){
            return WriteCommentResult.NOT_SIGNED;
        }
        if(!user.isAdmin()){
            return WriteCommentResult.NOT_ALLOWED;
        }
        BpArticleEntity existingBpArticle= this.bbsMapper.selectBpArticleByBoardIdJustOne(bbid);
        if(existingBpArticle==null){
            return WriteCommentResult.NO_SUCH_ARTICLE;
        }



        adminComment.setArticleIndex(bbid);
        if(this.bbsMapper.insertAdminComment(adminComment)>0){
            existingBpArticle.setCommentCount(existingBpArticle.getCommentCount()+1);

           if(this.bbsMapper.updateBpArticle(existingBpArticle)>0){
               return  CommonResult.SUCCESS;
           }
        }

        return  CommonResult.FAILURE;
    }

    public AdminCommentEntity[] getAdminComments(BpReadVo article){

        return this.bbsMapper.selectAdminCommentByIndex(article.getIndex());

    }


    public Enum<? extends IResult> modifyAdminComment(UserEntity user, AdminCommentEntity adminComment) {

        BpArticleEntity existingBpArticle = this.bbsMapper.selectBpArticleByBoardIdJustOne(adminComment.getArticleIndex());
        AdminCommentEntity existingAdminComment = this.bbsMapper.selectAdminCommentByIndexJustOne(adminComment.getIndex());

        //로그인 안했다면 실패
        if (user == null && !user.isAdmin()) {
            return ModifyArticleResult.NOT_SIGNED;
        }

        if (existingBpArticle == null || existingAdminComment==null) { //게시글이 없으면 실패

            return ModifyArticleResult.NO_SUCH_ARTICLE;

            //수정 시작
        }

        else {
            existingAdminComment.setModifiedOn(new Date());
            existingAdminComment.setContent(adminComment.getContent());


            return this.bbsMapper.updateAdminComment(existingAdminComment) > 0 ? CommonResult.SUCCESS : CommonResult.FAILURE;
        }
    }

}

