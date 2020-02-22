package com.jt.serviceImpl;

import com.alibaba.dubbo.config.annotation.Service;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.jt.dubbo.serviceImpl.service.DubboCartService;
import com.jt.mapper.CartMapper;
import com.jt.pojo.Cart;
import com.jt.pojo.OrderItem;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Date;
import java.util.List;

@Service
public class DubboCartServiceImpl implements DubboCartService {
    @Autowired
    private CartMapper cartMapper;

    //将商品添加到购物车
    @Override
    public void saveCart(Cart cart) {
        //查询出当前用户下是否有该商品
        QueryWrapper<Cart> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("user_id",cart.getUserId())
                    .eq("item_id",cart.getItemId());
        Cart cartItem = cartMapper.selectOne(queryWrapper);

        if (cartItem == null){
            //说明该用户下没有该商品，直接新增
            cart.setCreated(new Date())
                .setUpdated(cart.getCreated());
            cartMapper.insert(cart);
        }else {
            //说明该用户下已有该商品，则修改商品数量
            Integer num = cartItem.getNum() + cart.getNum();

            Cart cart1 = new Cart();
            cart1.setId(cartItem.getId())
                 .setNum(num)
                 .setUpdated(new Date());
            cartMapper.updateById(cart1);
        }

    }

    //查询当前用户下的所有商品
    @Override
    public List<Cart> selectItemByUserId(Long userId) {
        QueryWrapper<Cart> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("user_id",userId);
        List<Cart> cartList = cartMapper.selectList(queryWrapper);

        return cartList;
    }

    //修改商品数量
    @Override
    public void updateNumberByItemId(Cart cart) {
        Cart cartTamp = new Cart();
        cartTamp.setNum(cart.getNum()).setUpdated(new Date());
        QueryWrapper<Cart> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("user_id",cart.getUserId())
                    .eq("item_id",cart.getItemId());
        //修改商品数量
        cartMapper.update(cartTamp,queryWrapper);
    }

    //删除商品
    @Override
    public void delCart(Cart cart) {
        QueryWrapper<Cart> queryWrapper = new QueryWrapper<>(cart);
        cartMapper.delete(queryWrapper);
    }

    //批量删除已提交的商品
    @Override
    public void deleteOrderItems(Long userId, List<OrderItem> orderItems) {
        for (OrderItem orderItem : orderItems) {
            QueryWrapper<Cart> queryWrapper = new QueryWrapper<>();
            queryWrapper.eq("user_id",userId)
                        .eq("item_id",orderItem.getItemId());
            cartMapper.delete(queryWrapper);
        }
    }


}
