package com.jt.dubbo.serviceImpl;

import com.alibaba.dubbo.config.annotation.Service;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.jt.dubbo.serviceImpl.service.DubboUserService;
import com.jt.mapper.UserMapper;
import com.jt.pojo.User;
import com.jt.util.ObjectToJsonUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.DigestUtils;
import org.springframework.util.StringUtils;
import redis.clients.jedis.JedisCluster;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Service
public class DubboUserServiceImpl implements DubboUserService {
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private JedisCluster jedisCluster;

    //注册
    @Override
    public void saveUser(User user) {
        //密码加密
        String md5Pass = DigestUtils.md5DigestAsHex(user.getPassword().getBytes());
        user.setPassword(md5Pass)
            .setEmail(user.getPhone()+"@qq.com")  //把手机号拼接成邮箱，防止报错
            .setCreated(new Date())
            .setUpdated(user.getCreated());
        userMapper.insert(user);
    }

    //验证邮箱或手机号是否可用  1:邮箱  2:手机号
    @Override
    public boolean selectUserByEmailByPhone(String emailRoPhone, int type) {
        String column = "";
        switch (type){
            case 1:column = "email";break;
            case 2:column = "phone";break;
            default: System.out.println("参数异常："+type);
        }
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq(column,emailRoPhone);
        User user = userMapper.selectOne(queryWrapper);
        return user != null;
    }

    //登录
    @Override
    public String doLogin(User user, String IP) {
        //生成ticket密匙
        String uuid = UUID.randomUUID().toString();
        String ticket = DigestUtils.md5DigestAsHex(uuid.getBytes());

        //密码加密
        String md5Pass = DigestUtils.md5DigestAsHex(user.getPassword().getBytes());
        user.setPassword(md5Pass);
        //验证用户名、密码是否正确
        QueryWrapper<User> queryWrapper = new QueryWrapper<>(user);
        User users = userMapper.selectOne(queryWrapper);
        if (StringUtils.isEmpty(users)){
            //如果为null，说明用户名或密码错误
            return null;
        }
        user.setPassword("*********");
        //将用户转换为json格式
        String userJSON = ObjectToJsonUtil.toJSON(users);

        Map<String,String> hash = new HashMap<>();
        hash.put("JT_TICKET",ticket);
        hash.put("JT_USERJSON",userJSON);
        hash.put("JT_IP",IP);

        //将密匙、用户信息、IP存入Redis中
        jedisCluster.hmset(user.getUsername(),hash);
        jedisCluster.expire(user.getUsername(),7*24*3600);

        return ticket;
    }


}
