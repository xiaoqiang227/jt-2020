package com.jt.anno;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
@Target(ElementType.METHOD)//注解修饰的范围：方法
@Retention(RetentionPolicy.RUNTIME) //作用范围：运行时
public @interface Cache_Delete {
        //key=null 自动生成一个动态的key，!=null就使用用户自己定义的
        String key() default ""; //default:表示默认值

}
