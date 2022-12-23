package com.hiponya.bbqmall.services;

import com.hiponya.bbqmall.entities.bbs.ImageEntity;
import com.hiponya.bbqmall.entities.bbs.NoticeBoardEntity;
import com.hiponya.bbqmall.entities.bbs.NoticeEntity;
import com.hiponya.bbqmall.enums.CommonResult;
import com.hiponya.bbqmall.enums.bbs.WriteResult;
import com.hiponya.bbqmall.interfaces.IResult;
import com.hiponya.bbqmall.mappers.IBbsMapper;
import com.hiponya.bbqmall.models.PagingModel;
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
    public int getNoticeCount(NoticeBoardEntity board, String keyword) { //검색조건이 크리테리온 검색어가 키워드 보드가 어느 게시판
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
        System.out.println(board.getId());
        System.out.println("k="+keyword);
        return this.bbsMapper.selectNoticeCountByNoticeBoardId(board.getId() ,keyword);

    }
    //페이징을 불러온다 그냥 홈페이지로 갔을 경우

//    public int getNoticeCountAll(  String keyword) { //검색조건이 크리테리온 검색어가 키워드 보드가 어느 게시판
////
//        return this.bbsMapper.selectNoticeCountAll(keyword);
//
//    }
//

public NoticeReadVo[] getArticleNotice(){
        return this.bbsMapper.selectArticleNotice();
}

            //공지게시판을 불러온다
    public NoticeReadVo[] getNotice(NoticeBoardEntity board, PagingModel paging,  String keyword) {

        return this.bbsMapper.selectNoticeByBoardId(board.getId(), keyword, paging.countPerPage,(paging.requestPage - 1) * (paging.countPerPage));

    }
    //공지게시판을 불러온다 항목을 선택하지 않았을때 전체

//    public NoticeReadVo[] getNoticeAll(PagingModel paging,  String keyword) {
//
//        return this.bbsMapper.selectNoticeAll( keyword, paging.countPerPage,(paging.requestPage - 1) * (paging.countPerPage) );
//
//    }


    //게시물에 들어가는 동시에 조회수를 올려줌

    public NoticeReadVo readNotice(int index) {
        NoticeReadVo article = this.bbsMapper.selectNoticeByIndex(index);
        if (article != null) {
            article.setView(article.getView() + 1);
            this.bbsMapper.updateNotice(article);
        }

        return article;

    }


}
