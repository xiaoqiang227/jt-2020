package com.jt.serviceImpl;

import com.jt.mapper.ItemDescMapper;
import com.jt.mapper.ItemMapper;
import com.jt.pojo.Item;
import com.jt.pojo.ItemDesc;
import com.jt.vo.EasyUITable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

@Service
public class ItemServiceImpl implements ItemService {
	
	@Autowired
	private ItemMapper itemMapper;

	@Autowired
	private ItemDescMapper itemDescMapper;

	//分页查询
	@Override
	public EasyUITable findItemByPage(Integer page, Integer rows) {
		//获取item总记录数
		Integer total = itemMapper.selectCount(null);
		//分页查询
		//sql : select * from tb_item limit 起始页下标,查询多少条；
		//起始位置
		Integer start = (page-1) * rows;
		//分页查询
		List<Item> itemList = itemMapper.findItemByPage(start,rows);
		//返回总页数和分页查询的商品
		return new EasyUITable(total,itemList);
	}

	//商品新增
	@Override
	@Transactional  //事务控制
	public void itemSave(Item item, ItemDesc itemDesc) {
		//添加商品状态（1正常，2下架），创建时间和更新时间
		item.setStatus(1).setCreated(new Date()).setUpdated(item.getCreated());
		itemMapper.insert(item);

		//添加商品详情
		itemDesc.setItemId(item.getId()).setCreated(item.getCreated()).setUpdated(item.getCreated());
		itemDescMapper.insert(itemDesc);

	}

	//商品删除
	@Override
	public void itemDeletes(Long... ids) {
		//将数组转化为List集合
		List<Long> idList = Arrays.asList(ids);
		itemMapper.deleteBatchIds(idList);
	}

	//商品修改
	@Override
	@Transactional  //事务控制
	public void itemUpdate(Item item, ItemDesc itemDesc) {
		item.setUpdated(new Date());
		itemDesc.setItemId(item.getId()).setUpdated(item.getUpdated());
		itemMapper.updateById(item);
		itemDescMapper.updateById(itemDesc);
	}

	//上、下架
	@Override
	public void updateStatus(Long[] ids, int status) {
		Item item = new Item();
		for (Long id : ids) {
			item.setId(id).setStatus(status).setUpdated(new Date());
			itemMapper.updateById(item);
		}
	}

	//查询商品描述
	@Override
	public ItemDesc itemDescById(Long itemId) {
		return itemDescMapper.selectById(itemId);
	}


}
