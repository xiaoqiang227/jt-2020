package com.jt.config;

import com.jt.interceptor.UserInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.PathMatchConfigurer;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@EnableAsync
public class MvcConfigurer implements WebMvcConfigurer{

    //开启匹配后缀型配置
    @Override
    public void configurePathMatch(PathMatchConfigurer configurer) {

        configurer.setUseSuffixPatternMatch(true);
    }

    @Autowired
    private UserInterceptor userInterceptor;
    /*配置拦截器*/
    @Override
    public void addInterceptors(InterceptorRegistry registry) {

        registry.addInterceptor(userInterceptor)
                .addPathPatterns("/cart/**","/order/**");//**表示cart下多级目录，*表示cart下一级目录
    }

}
