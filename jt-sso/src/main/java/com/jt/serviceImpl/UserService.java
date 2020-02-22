package com.jt.serviceImpl;

public interface UserService {
    boolean findCheckUser(String param, Integer type);

    String findQueryUser(String ticket, String userName, String IP);
}
