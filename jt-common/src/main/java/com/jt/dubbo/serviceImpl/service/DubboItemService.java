package com.jt.dubbo.serviceImpl.service;

import com.jt.pojo.Item;

public interface DubboItemService {
    Item findItemById(Long itemId);
}
