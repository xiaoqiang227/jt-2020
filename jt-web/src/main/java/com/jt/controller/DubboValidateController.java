package com.jt.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.jt.dubbo.serviceImpl.service.DubboUserService;
import com.jt.util.EmailUtil;
import com.jt.util.PhoneCodeUtil;
import com.jt.vo.ValidateEmail;
import com.jt.vo.ValidatePhone;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
public class DubboValidateController {
    @Reference(check = false)
    private DubboUserService userService;
    @Autowired
    private EmailUtil emailUtil;
    @Autowired
    private PhoneCodeUtil phoneCodeUtil;

    //验证邮箱状态：0：可注册，1：已被使用，2：地址不正确
    @RequestMapping("/validate/isEmailEngaged")
    public ValidateEmail validateEmail(String email){
        //验证邮箱是否被使用
        boolean b = userService.selectUserByEmailByPhone(email,1);
        if (b){
            return new ValidateEmail("1");
        }
        //验证格式(地址)是否正确
        String regex = "^[a-z0-9A-Z]+[- | a-z0-9A-Z . _]+@([a-z0-9A-Z]+(-[a-z0-9A-Z]+)?\\.)+[a-z]{2,}$";
        boolean matches = email.matches(regex);

        return ValidateEmail.setSucceess(matches?"0":"2");
    }

    //邮箱验证成功，给用户发送一封验证邮件
    @RequestMapping("/validateuser/isEmailEngaged")
    public ValidateEmail validateUserEmail(String email){
        //发送邮件
        emailUtil.emailFS(email);
        log.info("=====邮件已发送！======");
        return ValidateEmail.setSucceess("0");
    }

    //验证手机号状态:0：可注册，1：已绑定，2：已重新注册并绑定,三天内不可重新注册
    @RequestMapping("/validateuser/isMobileEngaged")
    public ValidateEmail validateMobile(String mobil){
        boolean b = userService.selectUserByEmailByPhone(mobil, 2);
        return ValidateEmail.setSucceess(b?"1":"0");
    }

    //手机号发送验证码
    @RequestMapping("/notifyuser/mobileCode")
    public ValidatePhone mobileCode(String state, String mobile){
        //发送手机验证码
        //phoneCodeUtil.getPhonemsg(mobile);
        log.info("=====手机验证码已发送！=====");
        return ValidatePhone.setRs("1");
    }

    //提交验证
    @RequestMapping("/register/sendRegEmail")
    public ValidateEmail sub(){
        //未完成。。。。

        return ValidateEmail.setSucceess("1");
    }

}
