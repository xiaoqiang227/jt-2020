package com.jt.serviceImpl;

import com.alibaba.dubbo.config.annotation.Service;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.jt.dubbo.serviceImpl.service.DubboOrderService;
import com.jt.mapper.OrderItemMapper;
import com.jt.mapper.OrderMapper;
import com.jt.mapper.OrderShippingMapper;
import com.jt.pojo.Order;
import com.jt.pojo.OrderItem;
import com.jt.pojo.OrderShipping;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

@Service
public class DubboOrderServiceImpl implements DubboOrderService {
    @Autowired
    private OrderMapper orderMapper;
    @Autowired
    private OrderItemMapper orderItemMapper;
    @Autowired
    private OrderShippingMapper orderShippingMapper;

    /**
     * 1.利用mybatis-plus  效率低
     * 2.手写sql
     * 订单号：登录用户id+当前时间戳'
     */

    //添加订单
    @Override
    public String saveOrder(Order order) {
        //生成订单号：登录用户id+当前时间戳'
        String orderId = ""+order.getUserId()+System.currentTimeMillis();
        // 生成订单创建时间
        Date date = new Date();
        //设置状态 1；未付款
        order.setOrderId(orderId)
             .setStatus(1)
             .setCreated(date)
             .setUpdated(date);
        //手写SQL，效率比mybatis-plus要高
        orderMapper.insert(order);
        System.out.println("订单入库成功！");

        OrderShipping orderShipping = order.getOrderShipping();
        orderShipping.setOrderId(orderId)
                     .setCreated(date)
                     .setUpdated(date);
        orderShippingMapper.insert(orderShipping);
        System.out.println("订单物流入库成功！");

        List<OrderItem> orderItems = order.getOrderItems();
        for (OrderItem orderItem : orderItems) {
            orderItem.setOrderId(orderId)
                     .setCreated(date)
                     .setUpdated(date);
            orderItemMapper.insert(orderItem);
        }
        System.out.println("订单商品入库成功！");
        return orderId;
    }

    //查询订单
    @Override
    public Order findOrderById(String id) {
        //查询订单信息
        Order order = orderMapper.selectById(id);
        //查询订单物流信息
        OrderShipping orderShipping = findOrderShipping(id);
        //查询订单商品信息
        List<OrderItem> orderItems = findOrderItems(id);
        //将订单商品信息和订单物流信息存入订单信息中
        order.setOrderItems(orderItems)
             .setOrderShipping(orderShipping);
        return order;
    }

    //查询所有订单
    @Override
    public List<Order> findOrderAll(Long userId) {
        QueryWrapper<Order> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("user_id",userId).orderByDesc("updated");
        List<Order> orderList = orderMapper.selectList(queryWrapper);
        for (Order order : orderList) {
            //获取用户订单id
            String orderId = order.getOrderId();
            //查询订单物流信息
            OrderShipping orderShipping = findOrderShipping(orderId);
            //查询订单商品信息
            List<OrderItem> orderItems = findOrderItems(orderId);
            //将订单商品信息和订单物流信息存入订单信息中
            order.setOrderItems(orderItems)
                    .setOrderShipping(orderShipping);
        }
        return orderList;
    }

    //查询订单物流信息
    private OrderShipping findOrderShipping(String orderId){
        return orderShippingMapper.selectById(orderId);
    }

    //查询订单商品信息
    private List<OrderItem> findOrderItems(String orderId){
        QueryWrapper<OrderItem> itemQueryWrapper = new QueryWrapper<>();
        itemQueryWrapper.eq("order_id",orderId);
        return orderItemMapper.selectList(itemQueryWrapper);
    }
}
