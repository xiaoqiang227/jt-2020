package com.jt.util;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class CookieUtil {

    //添加Cookie
    public static void addCookie(HttpServletResponse response, String ticketName, String ticketValue, int seconds, String domain){
        //将ticket保存到客户端的cookie中
        Cookie ticketCookie = new Cookie(ticketName, ticketValue);
        //7天有效
        ticketCookie.setMaxAge(seconds);
        //cookie的权限设定(路径)
        ticketCookie.setPath("/");
        //cookie实现共享
        ticketCookie.setDomain(domain);
        response.addCookie(ticketCookie);
    }

    //获取Cookie的值
    public static String getCookies(HttpServletRequest request,String cookieName){
        //获取所有cookie
        Cookie[] cookies = request.getCookies();
        if (cookies == null || cookies.length == 0){
            //如果为null或长度=0，说明没有cookie，返回null
            return null;
        }

        String cookieValue = "";
        for (Cookie cookie : cookies) {
            if (cookie.getName().equals(cookieName)){
                cookieValue = cookie.getValue();
                break;
            }
        }
        return cookieValue;
    }

    //删除cookie
    public static void delCookie(HttpServletResponse response,String cookieName, String domain){
        Cookie cookie = new Cookie(cookieName,"");
        cookie.setMaxAge(0);
        cookie.setPath("/");
        cookie.setDomain(domain);
        response.addCookie(cookie);
    }
}
