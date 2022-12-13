package com.hiponya.bbqmall.mappers;


import com.hiponya.bbqmall.entities.member.EmailAuthEntity;
import com.hiponya.bbqmall.entities.member.UserEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface IMemberMapper {


    EmailAuthEntity selectEmailAuthByEmailCodeSalt(@Param(value = "email") String email,
                                                   @Param(value = "code") String code,
                                                   @Param(value = "salt") String salt); //@파람 타입이 있기때문에 xml파일에서 파라매터 타입을 적지 않는다.

    UserEntity selectUserByEmail(@Param(value = "email") String email);

    //  UserEntity selectUserByEmail(UserEntity user);
    int insertEmailAuth(EmailAuthEntity emailAuthEntity);


}