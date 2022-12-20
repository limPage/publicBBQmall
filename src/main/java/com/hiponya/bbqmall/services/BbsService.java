package com.hiponya.bbqmall.services;

import com.hiponya.bbqmall.entities.board.NoticeBoardEntity;
import com.hiponya.bbqmall.entities.board.NoticeEntity;
import com.hiponya.bbqmall.enums.CommonResult;
import com.hiponya.bbqmall.enums.bbs.WriteResult;
import com.hiponya.bbqmall.interfaces.IResult;
import com.hiponya.bbqmall.mappers.IBbsMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;

@Service(value = "com.hiponya.services.BbsService")

public class BbsService {


    private final IBbsMapper bbsMapper;


    @Autowired
    public BbsService(TemplateEngine templateEngine, IBbsMapper bbsMapper) {

        this.bbsMapper = bbsMapper;
    }



    public Enum<? extends IResult> writeNotice(NoticeEntity notice) {
//        article.setWrittenOn(new Date());
//        article.setModifiedOn(new Date());

        NoticeBoardEntity board = this.bbsMapper.selectNoticeBoardId(notice.getBoardId());
        if (board == null) {
            return WriteResult.NO_SUCH_BOARD;
        }

//        if(this.bbsMapper.insertArticle(article)==0){
//            return CommonResult.FAILURE;

//        }
//        return CommonResult.SUCCESS;

        return this.bbsMapper.insertNotice(notice) > 0 ? CommonResult.SUCCESS : CommonResult.FAILURE;
    }


}
