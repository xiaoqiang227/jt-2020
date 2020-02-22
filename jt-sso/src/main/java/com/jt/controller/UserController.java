package com.jt.controller;

import com.fasterxml.jackson.databind.util.JSONPObject;
import com.jt.serviceImpl.UserService;
import com.jt.util.IPUtil;
import com.jt.vo.SysResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    private UserService userService;

    //验证用户名、手机号、邮箱是否已经注册
    @RequestMapping("/check/{param}/{type}")
    public JSONPObject checkUser(@PathVariable String param, @PathVariable Integer type, String callback){
       boolean data = userService.findCheckUser(param,type);
        return new JSONPObject(callback,SysResult.success(data));
    }

    //用户信息回显  （用户名） query/7f86047a4bafc8b951041edcd6fde6e3/xiaoqiang123?
    @RequestMapping("/query/{ticket}/{userName}")
    public JSONPObject userQuery(@PathVariable String ticket, @PathVariable String userName, String callback, HttpServletRequest request){
        //获取用户的IP
        String IP = IPUtil.getIpAddr(request);
        System.out.println("回显IP = " + IP);
        //验证用户ticket、IP，并返回用户信息
        String userJSON = userService.findQueryUser(ticket,userName,IP);
        if (userJSON == null){
            //如果为null，说明不是同一用户
            return new JSONPObject(callback,SysResult.fail());
        }

        return new JSONPObject(callback,SysResult.success(userJSON));
    }


}
