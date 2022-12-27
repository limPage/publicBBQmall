package com.hiponya.bbqmall.mappers;

import com.hiponya.bbqmall.entities.bbs.ImageEntity;
import com.hiponya.bbqmall.entities.bbs.NoticeBoardEntity;
import com.hiponya.bbqmall.entities.bbs.NoticeEntity;
import com.hiponya.bbqmall.entities.bbs.QnaAnswerEntity;
import com.hiponya.bbqmall.vos.bbs.NoticeReadVo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface IBbsMapper {


    NoticeBoardEntity selectNoticeBoardId(@Param(value = "id") String id);
    int selectNoticeCountAll(
                             @Param(value = "keyword") String keyword);


    int selectNoticeCountByNoticeBoardId(@Param(value = "boardId") String boardId,

                                          @Param(value = "keyword") String keyword,
                                          @Param(value = "qid") String qid);

    int updateNoticeIsNew();

    int deleteNoticeByIndex(@Param(value = "index") int index);

    NoticeReadVo[] selectAnnounceNotice();
    NoticeReadVo[] selectNoticeByBoardId(@Param(value = "boardId") String boardId,

                                            @Param(value = "keyword") String keyword,
                                            @Param(value = "limit") int limit,
                                            @Param(value = "offset") int offset,
                                            @Param(value = "qid") String qid);

    NoticeReadVo selectNoticeByIndex(@Param(value = "index") int index);

    QnaAnswerEntity[] selectAnswer();
    int updateNotice(NoticeEntity notice);


    int insertNotice(NoticeEntity notice);

    int insertImage(ImageEntity image);

    ImageEntity selectImageByIndex(@Param(value =  "index") int index);



}
