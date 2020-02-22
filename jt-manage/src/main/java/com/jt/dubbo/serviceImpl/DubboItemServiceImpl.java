package com.jt.dubbo.serviceImpl;

import com.alibaba.dubbo.config.annotation.Service;
import com.jt.dubbo.serviceImpl.service.DubboItemService;
import com.jt.mapper.ItemMapper;
import com.jt.pojo.Item;
import org.springframework.beans.factory.annotation.Autowired;

@Service
public class DubboItemServiceImpl implements DubboItemService {
    @Autowired
    private ItemMapper itemMapper;


    @Override
    public Item findItemById(Long itemId) {
        return itemMapper.selectById(itemId);
    }
}
