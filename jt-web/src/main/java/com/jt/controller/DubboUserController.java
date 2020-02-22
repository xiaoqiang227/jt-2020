package com.jt.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.jt.dubbo.serviceImpl.service.DubboUserService;
import com.jt.pojo.User;
import com.jt.util.CookieUtil;
import com.jt.util.IPUtil;
import com.jt.vo.SysResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import redis.clients.jedis.JedisCluster;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Controller
@RequestMapping("/user")
public class DubboUserController {
    @Reference(check = false)
    private DubboUserService userService;
    @Autowired
    private JedisCluster jedisCluster;

    //通用的页面跳转方法  （注册、登录）
    @RequestMapping("/{moduleName}")
    public String moduleName(@PathVariable String moduleName){
        return moduleName;
    }

    //注册
    @RequestMapping("/doRegister")
    @ResponseBody
    public SysResult doRegister(User user){
        userService.saveUser(user);
        return SysResult.success();
    }

    //登录
    @RequestMapping("/doLogin")
    @ResponseBody
    public SysResult doLogin(User user, HttpServletResponse response, HttpServletRequest request){
        //获取用户IP
        String IP = IPUtil.getIpAddr(request);
        System.out.println("登录IP = " + IP);
        //验证用户名、密码是否正确，并返回ticket
        String ticket = userService.doLogin(user,IP);
        if (StringUtils.isEmpty(ticket)){
            //如果为null，说明用户名或密码错误
            return SysResult.fail();
        }
        //将ticket密匙和用户名分别存入cookie中
        CookieUtil.addCookie(response,"JT_TICKET",ticket,7*24*3600,"jt.com");
        CookieUtil.addCookie(response,"JT_USERNAME",user.getUsername(),7*24*3600,"jt.com");

        return SysResult.success();
    }

    //用户退出
    @RequestMapping("/logout")
    public String doLogout(HttpServletRequest request,HttpServletResponse response){
        //查询用户名
        String username = CookieUtil.getCookies(request, "JT_USERNAME");

        if (!StringUtils.isEmpty(username)){
            //删除key为username的hash
            jedisCluster.del(username);
            //删除cookie
            CookieUtil.delCookie(response,"JT_TICKET","jt.com");
            CookieUtil.delCookie(response,"JT_USERNAME","jt.com");
        }
        return "redirect:/";
    }


}
