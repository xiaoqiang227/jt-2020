package com.jt.aop;

import com.jt.anno.Cache_Delete;
import com.jt.anno.Cache_File;
import com.jt.util.ObjectToJsonUtil;
import jdk.nashorn.internal.scripts.JD;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import redis.clients.jedis.JedisCluster;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

@Aspect //Redis缓存切面
@Component
public class CacheAspect {
    //当前切面位于common中，必须添加required = false
    @Autowired(required = false)  //表示只有别人引用时才创建
    private JedisCluster jedisCluster; //redis集群

    //添加缓存
    @Around("@annotation(cacheFile)")
    public Object around(ProceedingJoinPoint joinPoint, Cache_File cacheFile){
        String className = joinPoint.getSignature().getDeclaringTypeName();
        //1.动态生成key，——>包名.类名.方法名::第一个参数（parenId）
        String key = getKey(joinPoint,cacheFile);
        //从Redis缓存取数据
        String result = jedisCluster.hget(className,key);
        Object data = null;
        try {
            //如果缓存数据，则查询数据
            if (StringUtils.isEmpty(result)){
                //执行Cache_file下的方法查询数据
                data = joinPoint.proceed();
                //对象转json,利用Redis的hash进行存储，为的是方便删除的时候通过key批量删除,
                // key为类的全路径，例如：com.jt.controller.ItemCatController。将下面的json数据赋值给key
                String value = ObjectToJsonUtil.toJSON(data);
                //将数据存储到hash
                System.out.println("className = " + className);
                 jedisCluster.hset(className,key,value);
                //有无生命周期
                if (cacheFile.seconds() == 0){
                    jedisCluster.hset(className,key,value);
                }else {
                    jedisCluster.hset(className,key,value);
                    jedisCluster.expire(className,cacheFile.seconds());
                }
                System.out.println("AOP查询数据库!");
            }else {
                //表示缓存中有数据
                Class returnClass = getClass(joinPoint);
                data = ObjectToJsonUtil.toObject(result,returnClass);
                System.out.println("AOP查询缓存!");
            }
        } catch (Throwable e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
        return data;
    }

    //获取返回值类型
    private Class getClass(ProceedingJoinPoint joinPoint) {
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        return  signature.getReturnType();
    }

    //获取key
    private String getKey(ProceedingJoinPoint joinPoint, Cache_File cacheFile) {
        //1.判断用户是否定义key值
        String key = cacheFile.key();
        if(!StringUtils.isEmpty(key)){
            return key;  //返回用户自己定义的key
        }
        //否则自动生成key,包名.类名.方法名::parenId
        String className = joinPoint.getSignature().getDeclaringTypeName();
        String methodName = joinPoint.getSignature().getName();
        Object[] args = joinPoint.getArgs();
        key = className+"."+methodName+"."+args[0];
        System.out.println("cacheFile key= "+key);
        return key;
    }


    //当缓存数据发生增、删、改时，删除缓存数据
    @Before("@annotation(cacheDelete)")
    public void aroundRM(JoinPoint joinPoint, Cache_Delete cacheDelete){
        System.out.println("执行了Redis缓存删除注解方法！");
        //获取hash的key
        String key = cacheDelete.key();
        String[] keys = key.split(",");
        for (String k : keys) {
            System.out.println("cacheDelete key = " + k);
            Long del = jedisCluster.del(k);
            System.out.println("del = " + del);
        }

    }
}
