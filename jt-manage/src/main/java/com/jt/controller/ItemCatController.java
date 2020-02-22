package com.jt.controller;

import com.jt.anno.Cache_File;
import com.jt.serviceImpl.ItemCatService;
import com.jt.vo.EasyUITree;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/item/cat")
public class ItemCatController {
    @Autowired
    private ItemCatService itemCatService;

    //查询子类目录名称
    @RequestMapping("/queryItemName")
    @Cache_File
    public  String queryItemName(Long itemCatId){
        String itemCatName = itemCatService.itemCatById(itemCatId);
        return itemCatName;
    }

    //查询所有分类目录
    @RequestMapping("/list")                       //首次访问，parentId没有值时，赋初始值 “0”
    @Cache_File
    public List<EasyUITree> findItemCatByParentId(@RequestParam(value = "id",defaultValue = "0") Long parentId){

        List<EasyUITree> itemCatByParentId = itemCatService.findItemCatByParentId(parentId);
        System.out.println("itemCatByParentId: "+itemCatByParentId);
        return itemCatByParentId;
    }

}
