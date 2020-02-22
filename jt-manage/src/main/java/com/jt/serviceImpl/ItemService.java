package com.jt.serviceImpl;

import com.jt.pojo.Item;
import com.jt.pojo.ItemDesc;
import com.jt.vo.EasyUITable;

public interface ItemService {

    EasyUITable findItemByPage(Integer page, Integer rows);


    void itemSave(Item item, ItemDesc itemDesc);

    void itemDeletes(Long... ids);

    void itemUpdate(Item item, ItemDesc itemDesc);


    void updateStatus(Long[] ids, int status);

    ItemDesc itemDescById(Long itemId);
}
