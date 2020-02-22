package com.jt.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.jt.dubbo.serviceImpl.service.DubboCartService;
import com.jt.pojo.Cart;
import com.jt.util.ThreadLocalUtil;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
@RequestMapping("/cart")
public class DubboCartController {
    @Reference(check = false)
    private DubboCartService cartService;

    //查询当前用户下的所以商品，去到购物车结算
    @RequestMapping("/show")
    public String orderCreate(Model model){
        //通过ThreadLocal获取当前用户的id
        Long userId = ThreadLocalUtil.getUser().getId();
        //查询当前用户下的所有商品
        List<Cart> cartList = cartService.selectItemByUserId(userId);
        //将当前用户的所以商品信息添加的model中
        model.addAttribute("cartList",cartList);
        return "cart";
    }

    //将商品添加到购物车
    @RequestMapping("/add/{itemId}")
    @Transactional
    public String saveCart(Cart cart, Model model){
        //通过ThreadLocal获取当前用户的id
        Long userId = ThreadLocalUtil.getUser().getId();
        //将商品添加到购物车
        cart.setUserId(userId);
        cartService.saveCart(cart);

        return "redirect:/cart/show.html";
    }

    // 修改商品数量
    @RequestMapping("/update/num/{itemId}/{num}")
    @ResponseBody
    public void aa(Cart cart){
        //通过ThreadLocal获取当前用户的id
        Long userId = ThreadLocalUtil.getUser().getId();
        cart.setUserId(userId);
        //修改商品数量
        cartService.updateNumberByItemId(cart);
    }

    //删除商品  cart/delete/562379
    @RequestMapping("/delete/{itemId}")
    public String deleteCart(Cart cart){
        //通过ThreadLocal获取当前用户的id
        Long userId = ThreadLocalUtil.getUser().getId();
        cart.setUserId(userId);

        cartService.delCart(cart);
        return "redirect:/cart/show.html";
    }



}
