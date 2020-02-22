package com.jt.controller;

import com.jt.anno.Cache_Delete;
import com.jt.anno.Cache_File;
import com.jt.pojo.Item;
import com.jt.pojo.ItemDesc;
import com.jt.serviceImpl.ItemService;
import com.jt.vo.EasyUITable;
import com.jt.vo.SysResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/item")
public class ItemController {
	@Autowired
	private ItemService itemService;

	//商品查询,添加Redis缓存
	@RequestMapping("/query")
	@Cache_File
	public EasyUITable findItemByPage(Integer page,Integer rows){
		System.out.println("访问了item查询方法!");
		EasyUITable itemByPage = itemService.findItemByPage(page, rows);
		System.out.println(itemByPage);
		return itemByPage;
	}


	//商品新增
	@RequestMapping("/save")
	public SysResult itemSave(Item item, ItemDesc itemDesc){
		System.out.println("访问了商品新增方法！");
			itemService.itemSave(item,itemDesc);
			return SysResult.success();
	}

	//商品删除
	@RequestMapping("/delete")
	public SysResult itemDelete(Long... ids){
		System.out.println("访问了商品删除方法！");
		itemService.itemDeletes(ids);
		return SysResult.success();
	}

	//商品修改
	@RequestMapping("/update")
	@Cache_Delete(key = "com.jt.controller.ItemController,com.jt.controller.ItemCatController")
	public SysResult itemUpdate(Item item,ItemDesc itemDesc){
		System.out.println("访问了商品修改方法！");
		itemService.itemUpdate(item,itemDesc);
		return SysResult.success();
	}

	// 商品描述查询item/param/item/query/1474391965
	@RequestMapping("/query/item/desc/{itemId}")
	@Cache_File
	public SysResult itemDescById(@PathVariable Long itemId){
		ItemDesc itemDesc = itemService.itemDescById(itemId);
		return SysResult.success(itemDesc);
	}

	//下架
	@RequestMapping("/instock")
	public SysResult itemInstock(Long... ids){
		int status = 2;
		itemService.updateStatus(ids,status);
		return SysResult.success();
	}

	//上架
	@RequestMapping("/reshelf")
	public SysResult itemReshelf(Long... ids){
		int status = 1;
		itemService.updateStatus(ids,status);
		return SysResult.success();
	}
}
