package com.jt.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.jt.dubbo.serviceImpl.service.DubboItemDescService;
import com.jt.dubbo.serviceImpl.service.DubboItemService;
import com.jt.pojo.Item;
import com.jt.pojo.ItemDesc;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/items")
public class DubboItemController {
    @Reference(check = false)
    private DubboItemService itemService;
    @Reference(check = false)
    private DubboItemDescService itemDescService;

    //查询商品和商品详情，跳转到商品页面
    @RequestMapping("/{itemId}")
    private String findItem(@PathVariable Long itemId, Model model){
        Item item = itemService.findItemById(itemId);
        ItemDesc itemDesc = itemDescService.findItemDescById(item.getId());
        model.addAttribute("item",item);
        model.addAttribute("itemDesc",itemDesc);
        return "item";
    }


}
