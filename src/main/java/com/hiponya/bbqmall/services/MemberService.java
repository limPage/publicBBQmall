package com.hiponya.bbqmall.services;

import com.hiponya.bbqmall.entities.member.EmailAuthEntity;
import com.hiponya.bbqmall.entities.member.UserEntity;
import com.hiponya.bbqmall.enums.CommonResult;
import com.hiponya.bbqmall.enums.member.SendEmailAuthResult;
import com.hiponya.bbqmall.enums.member.VerifyEmailAuthResult;
import com.hiponya.bbqmall.interfaces.IResult;
import com.hiponya.bbqmall.mappers.IMemberMapper;
import com.hiponya.bbqmall.utils.CryptoUtils;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.security.NoSuchAlgorithmException;
import java.util.Date;

@Service(value = "com.hiponya.bbqmall.services.MemberService")
public class MemberService {



    private final JavaMailSender mailSender;

    private final TemplateEngine templateEngine;
    private final IMemberMapper memberMapper;


    @Autowired
    public MemberService(JavaMailSender mailSender, TemplateEngine templateEngine, IMemberMapper MemberMapper) {
        this.mailSender = mailSender;
        this.templateEngine = templateEngine;
        this.memberMapper = MemberMapper;
    }

    @Transactional //모두성공해야 보내주겟다
    public Enum<? extends IResult> sendEmailAuth(UserEntity user, EmailAuthEntity emailAuth) throws NoSuchAlgorithmException, MessagingException {
        //뭔진모르겠는데 반드시 상속받거나 구현하고 있어야한다.
        UserEntity existingUser = this.memberMapper.selectUserByEmail(user.getEmail());
        if (existingUser != null) {
            return SendEmailAuthResult.EMAIL_DUPLICATED; //사용중인 이메일이다.
        }
        //아파치커먼즈랭 암호화
        String authCode = RandomStringUtils.randomNumeric(6);
        String authSalt = String.format("%s%s%f%f",
                user.getEmail(), authCode, Math.random(), Math.random());
        System.out.println(authSalt); //솔트할 문자열 제작

        CryptoUtils.hashSha512(authCode);//유틸즈에서 가져옴

        Date createdOn = new Date();
        Date expiresOn = DateUtils.addMinutes(createdOn, 5);

        emailAuth.setCode(authCode);
        emailAuth.setSalt(authSalt);
        emailAuth.setCreatedOn(createdOn);
        emailAuth.setExpiresOn(expiresOn);
        emailAuth.setExpired(false);

        if (this.memberMapper.insertEmailAuth(emailAuth) == 0) {
            return CommonResult.FAILURE;
        }
//        SimpleMailMessage mail = new SimpleMailMessage();
//        mail.setTo(user.getEmail());
//        mail.setFrom("jjirong2e@gmail.com");
//        mail.setSubject("[스터디] 회원가입 인증 번호");
//        mail.setText("emailAuth.getCode()");
//        this.mailSender.send(mail);

        Context context = new Context(); //서비스에서 html 파일갑이용하기 위해
        context.setVariable("code", emailAuth.getCode());


        //메일보내기
        String text = this.templateEngine.process("member/registerEmailAuth", context);//서비스에서 html 파일갑이용하기 위해2
        MimeMessage mail = this.mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(mail, "UTF-8");
        helper.setTo(user.getEmail());
        helper.setSubject("[스터디] 회원가입 인증번호");
        helper.setText(text, true);
        this.mailSender.send(mail);

        return CommonResult.SUCCESS;
    }

    @Transactional
    public Enum<? extends IResult> verifyEmailAuth(EmailAuthEntity emailAuth) {

        EmailAuthEntity existingEmailAuth = this.memberMapper.selectEmailAuthByEmailCodeSalt(
                emailAuth.getEmail(), emailAuth.getCode(), emailAuth.getSalt());

        if (existingEmailAuth == null) {
            return CommonResult.FAILURE;
        }

        if (existingEmailAuth.getExpiresOn().compareTo(new Date()) < 0) {
            return VerifyEmailAuthResult.EXPIRED;
        }

        existingEmailAuth.setExpired(true);
        if (this.memberMapper.updateEmailAuth(existingEmailAuth) == 0) {
            return CommonResult.FAILURE;
        }

        return CommonResult.SUCCESS;
    }


}
