package com.jt.controller;

import com.jt.pojo.ItemParam;
import com.jt.serviceImpl.ItemParamService;
import com.jt.vo.EasyUITable;
import com.jt.vo.SysResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/item")
public class ItemParamController {
    @Autowired
    private ItemParamService itemParamService;

    //查询所有规格参数
    @RequestMapping("/param/list")
    public EasyUITable findParam(Integer page,Integer rows){
        return itemParamService.findParamByPage(page, rows);
    }

    //查询商品分类目录是否已添加？
    @RequestMapping("/param/query/itemcatid/{itemCatId}")
    public SysResult findItemCatName(@PathVariable Long itemCatId){
        ItemParam itemParam = itemParamService.findParamByItemCatId(itemCatId);
        System.out.println("itemCatId:"+itemCatId+";itemParam:"+itemParam);
        if (itemParam == null){
            return SysResult.success();
        }else {
            return SysResult.success("该类目已添加！");
        }
    }

    //添加规格参数
    @RequestMapping("/param/save/{itemCatId}")
    public SysResult itemParamSave(@PathVariable Long itemCatId, ItemParam itemParam){
        System.out.println("itemCatId:"+itemCatId+",itemParam:"+itemParam);
        itemParamService.itemParamSave(itemCatId,itemParam);
        return SysResult.success();
    }

    //修改规格参数
    @RequestMapping("/param/update")
    public SysResult itemParamUpdate(ItemParam itemParam){
        itemParamService.itemParamUpdate(itemParam);
        return SysResult.success();
    }

    //删除规格参数
    @RequestMapping("/param/delete")
    public SysResult itemParamDelete(Long... ids){
        itemParamService.itemParamDelete(ids);
        return SysResult.success();
    }





}
