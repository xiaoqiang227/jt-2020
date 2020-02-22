package com.jt.controller;

import com.jt.pojo.ItemParamItem;
import com.jt.serviceImpl.ItemParamItemService;
import com.jt.vo.SysResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/item")
public class ItemParamItemController {

    @Autowired
    private ItemParamItemService itemParamItemService;

    //查询商品规格
    @RequestMapping("/param/item/query/{itemId}")
    public SysResult itemParamById(@PathVariable Long itemId){
        ItemParamItem itemParamItem = itemParamItemService.findItemParam(itemId);
        System.out.println("itemParamItem: "+itemParamItem);
        return SysResult.success(itemParamItem);
    }

}
