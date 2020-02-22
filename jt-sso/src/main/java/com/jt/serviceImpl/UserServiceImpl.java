package com.jt.serviceImpl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.jt.mapper.UserMapper;
import com.jt.pojo.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import redis.clients.jedis.JedisCluster;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private JedisCluster jedisCluster;
    
    //验证用户名、手机号、邮箱是否已经注册
    @Override
    public boolean findCheckUser(String param, Integer type) {
        String column = "";
        switch (type){
            case 1:column = "username";break;
            case 2:column = "phone";break;
            case 3:column = "email";break;
            default: System.out.println("参数异常："+type);
        }
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq(column,param);
        User user = userMapper.selectOne(queryWrapper);
        return user != null;
    }

    //用户信息回显  用户名
    @Override
    public String findQueryUser(String ticket, String userName,String IP) {
        //验证用户IP
        String jt_ip = jedisCluster.hget(userName, "JT_IP");
        if (!jt_ip.equalsIgnoreCase(IP)){
            return null;
        }

        //验证用户密匙ticket
        String jt_ticket = jedisCluster.hget(userName, "JT_TICKET");
        if (!jt_ticket.equalsIgnoreCase(ticket)){
            return null;
        }

        //返回用户信息
        String jt_userjson = jedisCluster.hget(userName, "JT_USERJSON");
        return jt_userjson;
    }
}
