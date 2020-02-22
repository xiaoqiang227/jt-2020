package com.jt.dubbo.serviceImpl.service;

import com.jt.pojo.User;

public interface DubboUserService {
    void saveUser(User user);

    boolean selectUserByEmailByPhone(String email,int type);

    String doLogin(User user, String IP);

}
