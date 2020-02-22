package com.jt.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.jt.dubbo.serviceImpl.service.DubboCartService;
import com.jt.dubbo.serviceImpl.service.DubboOrderService;
import com.jt.pojo.Cart;
import com.jt.pojo.Order;
import com.jt.pojo.OrderItem;
import com.jt.util.ThreadLocalUtil;
import com.jt.vo.SysResult;
import org.omg.PortableInterceptor.LOCATION_FORWARD;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
@RequestMapping("/order")
public class DubboOrderController {
    @Reference(check = false)
    private DubboCartService cartService;
    @Reference(check = false)
    private DubboOrderService orderService;

    ///查询当前用户下的所以商品，访问订单结算页面
    @RequestMapping("/create")
    public String orderCreate(Model model){
        //通过ThreadLocal获取当前用户的id
        Long userId = ThreadLocalUtil.getUser().getId();
        //查询用户下的所有商品
        List<Cart> carts = cartService.selectItemByUserId(userId);
        model.addAttribute("carts",carts);

        //计算商品总金额，返回给前端页面
        Long totalFee = getTotalFee(carts);
        model.addAttribute("totalPrice",totalFee);
        return "order-cart";
    }

    //计算商品总金额
    private Long getTotalFee(List<Cart> carts) {
        Long totalFee = 0l;
        for (Cart cart : carts) {
            //当商品总额
            Long itemPrice = cart.getItemPrice();
            Integer num = cart.getNum();
            long itemFee = itemPrice * num;
            totalFee += itemFee;
        }
        return totalFee;
    }

    //提交订单
    @RequestMapping("/submit")
    @ResponseBody
    @Transactional
    public SysResult subOrder(Order order){
        //通过ThreadLocal获取当前用户的id
        Long userId = ThreadLocalUtil.getUser().getId();
        order.setUserId(userId);
        String orderId = orderService.saveOrder(order);
        //提交订单后，删除购物车的已提交所有商品
        List<OrderItem> orderItems = order.getOrderItems();
        cartService.deleteOrderItems(order.getUserId(),orderItems);
        return SysResult.success(orderId);
    }

    //查询订单
    @RequestMapping("/success")
    public String findOrder(String id,Model model){
        //访问了查询订单方法
        Order order = orderService.findOrderById(id);
        model.addAttribute("order",order);
        //计算送达时间  （未完成）
        String date = "5天";
        model.addAttribute("date",date);
        return "success";
    }

    //我的订单页面
    @RequestMapping("/myOrder")
    public String myOrder(Model model){
        System.out.println("访问了我的订单方法！");
        //通过ThreadLocal获取当前用户的id
        Long userId = ThreadLocalUtil.getUser().getId();
        List<Order> orderList = orderService.findOrderAll(userId);
        model.addAttribute("orderList",orderList);
        return "my-orders";
    }
}
