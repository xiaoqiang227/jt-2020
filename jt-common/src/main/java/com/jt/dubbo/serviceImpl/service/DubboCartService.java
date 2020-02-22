package com.jt.dubbo.serviceImpl.service;

import com.jt.pojo.Cart;
import com.jt.pojo.OrderItem;

import java.util.List;

public interface DubboCartService {
    void saveCart(Cart cart);

    List<Cart> selectItemByUserId(Long userId);

    void updateNumberByItemId(Cart cart);

    void delCart(Cart cart);

    void deleteOrderItems(Long userId,List<OrderItem> orderItems);
}
