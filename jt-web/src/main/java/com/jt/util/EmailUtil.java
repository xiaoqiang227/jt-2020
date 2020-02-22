package com.jt.util;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.mail.EmailException;
import org.apache.commons.mail.HtmlEmail;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Repository;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
@Repository
@Slf4j
public class EmailUtil {
    //发送邮件
    @Async
    public  String emailFS(String emailIp){
        //生成验证码
        String code = vCode();
        //设置发送内容
        String content = "<html><head></head><body><h1>这是一封激活邮件,激活请点击以下链接</h1><h3><a href='http://localhost:8080/RegisterDemo/ActiveServlet?code="
                       + code + "'>http://localhost:8080/RegisterDemo/ActiveServlet?code=" + code
                       + "</href></h3></body></html>";

        HtmlEmail email = new HtmlEmail();//创建一个HtmlEmail实例对象
        email.setHostName("smtp.qq.com");//邮箱的SMTP服务器，一般123邮箱的是smtp.123.com,qq邮箱为smtp.qq.com
        email.setCharset("utf-8");//设置发送的字符类型
        try {
            email.addTo(emailIp);//设置收件人
            email.setFrom("1711530475@qq.com","小强");//发送人的邮箱，用户名可以随便填也可以不写
            email.setAuthentication("1711530475@qq.com","nkbwuvhvorkudaie");//发送人的邮箱和授权码
            email.setSubject("京淘注册验证码");//设置发送主题
            //email.setMsg(content);//设置发送内容
            email.setContent(content, "text/html;charset=UTF-8");//设置发送内容  HTML格式
            email.send();//进行发送

        }catch (EmailException e){
            e.printStackTrace();
            throw new RuntimeException();
        }
        log.info("=====邮件工具类执行完毕！=====");
        return code;
    }

    // 随机验证码
    public static String vCode() {  //由于数字 1 、 0 和字母 O 、l 有时分不清楚，所以，没有数字 1 、 0
        String[] beforeShuffle= new String[] { "2", "3", "4", "5", "6", "7", "8", "9", "A", "B", "C", "D", "E", "F",
                "G", "H", "I", "J", "K", "L", "M", "N", "O", "P", "Q", "R", "S", "T", "U", "V", "W", "X", "Y", "Z", "a",
                "b", "c", "d", "e", "f", "g", "h", "i", "j", "k", "l", "m", "n", "o", "p", "q", "r", "s", "t", "u", "v",
                "w", "x", "y", "z" };
        List list = Arrays.asList(beforeShuffle);//将数组转换为集合
        Collections.shuffle(list);  //打乱集合顺序
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < list.size(); i++) {
            sb.append(list.get(i)); //将集合转化为字符串
        }
        return sb.toString().substring(3, 8);  //截取字符串第4到8
    }
}
