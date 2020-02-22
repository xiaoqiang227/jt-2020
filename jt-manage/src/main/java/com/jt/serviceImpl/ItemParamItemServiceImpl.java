package com.jt.serviceImpl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.jt.mapper.ItemParamItemMapper;
import com.jt.pojo.ItemParamItem;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ItemParamItemServiceImpl implements ItemParamItemService{
    @Autowired
    private ItemParamItemMapper itemParamItemMapper;

    //查询商品规格
    @Override
    public ItemParamItem findItemParam(Long itemId) {
        QueryWrapper<ItemParamItem> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("item_id",itemId);
        ItemParamItem itemParamItem = itemParamItemMapper.selectOne(queryWrapper);
        return itemParamItem;
    }
}
