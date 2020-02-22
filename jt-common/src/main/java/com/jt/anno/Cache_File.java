package com.jt.anno;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.METHOD)//注解修饰的范围：方法
@Retention(RetentionPolicy.RUNTIME) //作用范围：运行时
public @interface Cache_File {
    //key=null 自动生成一个动态的key，!=null就使用用户自己定义的
    String key() default ""; //default:表示默认值
    int seconds() default 0; //seconds：生命周期  默认为0，表示数据不过期

}
