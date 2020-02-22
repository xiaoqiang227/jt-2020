package com.jt.dubbo.serviceImpl;

import com.alibaba.dubbo.config.annotation.Service;
import com.jt.dubbo.serviceImpl.service.DubboItemDescService;
import com.jt.mapper.ItemDescMapper;
import com.jt.pojo.ItemDesc;
import org.springframework.beans.factory.annotation.Autowired;

@Service
public class DubboItemDescServiceImpl implements DubboItemDescService {
    @Autowired
    private ItemDescMapper itemDescMapper;

    @Override
    public ItemDesc findItemDescById(Long itemDescId) {

        return itemDescMapper.selectById(itemDescId);
    }
}
