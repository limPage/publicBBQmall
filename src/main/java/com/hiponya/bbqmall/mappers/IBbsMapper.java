package com.hiponya.bbqmall.mappers;

import com.hiponya.bbqmall.entities.bbs.ImageEntity;
import com.hiponya.bbqmall.entities.bbs.NoticeBoardEntity;
import com.hiponya.bbqmall.entities.bbs.NoticeEntity;
import com.hiponya.bbqmall.vos.bbs.NoticeReadVo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface IBbsMapper {


    NoticeBoardEntity selectNoticeBoardId(@Param(value = "id") String id);
    int selectNoticeCountAll(
                             @Param(value = "keyword") String keyword);


    int selectNoticeCountByNoticeBoardId(@Param(value = "boardId") String boardId,

                                          @Param(value = "keyword") String keyword);


    NoticeReadVo[] selectArticleNotice();
    NoticeReadVo[] selectNoticeByBoardId(@Param(value = "boardId") String boardId,

                                            @Param(value = "keyword") String keyword,
                                            @Param(value = "limit") int limit,
                                            @Param(value = "offset") int offset);
    NoticeReadVo[] selectNoticeAll(

                                         @Param(value = "keyword") String keyword,
                                         @Param(value = "limit") int limit,
                                         @Param(value = "offset") int offset);
    NoticeReadVo selectNoticeByIndex(@Param(value = "index") int index);

    int updateNotice(NoticeEntity article);


    int insertNotice(NoticeEntity notice);

    int insertImage(ImageEntity image);

    ImageEntity selectImageByIndex(@Param(value =  "index") int index);



}
