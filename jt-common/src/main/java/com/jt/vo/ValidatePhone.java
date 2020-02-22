package com.jt.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
//验证手机状态
public class ValidatePhone {

    private String  rs;	//验证结果 1：验证码已发送，-1：网络繁忙，请稍后重新获取验证码

    public static ValidatePhone setRs(String rs){

        return new ValidatePhone(rs);
    }
}
