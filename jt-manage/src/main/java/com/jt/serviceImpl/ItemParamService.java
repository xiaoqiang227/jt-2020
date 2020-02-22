package com.jt.serviceImpl;

import com.jt.pojo.ItemParam;
import com.jt.vo.EasyUITable;

public interface ItemParamService {
    EasyUITable findParamByPage(Integer page, Integer rows);

    void itemParamSave(Long itemCatId, ItemParam itemParam);

    ItemParam findParamByItemCatId(Long itemCatId);

    void itemParamDelete(Long[] ids);

    void itemParamUpdate(ItemParam itemParam);
}
