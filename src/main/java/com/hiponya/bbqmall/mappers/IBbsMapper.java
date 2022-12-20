package com.hiponya.bbqmall.mappers;

import com.hiponya.bbqmall.entities.board.NoticeBoardEntity;
import com.hiponya.bbqmall.entities.board.NoticeEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface IBbsMapper {


    NoticeBoardEntity selectNoticeBoardId(@Param(value = "id") String id);

    int insertNotice(NoticeEntity notice);


}
