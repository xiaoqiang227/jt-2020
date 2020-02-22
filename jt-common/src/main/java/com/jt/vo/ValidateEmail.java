package com.jt.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
//验证邮箱状态
public class ValidateEmail {

    private String  success;	//验证结果0：可注册，1：已被使用，2：地址不正确

    public static ValidateEmail setSucceess(String success){

        return new ValidateEmail(success);
    }

}
