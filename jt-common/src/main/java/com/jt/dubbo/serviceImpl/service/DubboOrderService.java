package com.jt.dubbo.serviceImpl.service;

import com.jt.pojo.Order;

import java.util.List;

public interface DubboOrderService {
    String saveOrder(Order order);

    Order findOrderById(String id);

    List<Order> findOrderAll(Long userId);
}
