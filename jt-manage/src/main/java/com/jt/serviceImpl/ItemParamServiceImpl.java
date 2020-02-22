package com.jt.serviceImpl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.jt.mapper.ItemCatMapper;
import com.jt.mapper.ItemParamMapper;
import com.jt.pojo.ItemParam;
import com.jt.vo.EasyUITable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

@Service
public class ItemParamServiceImpl implements ItemParamService{
    @Autowired
    private ItemParamMapper itemParamMapper;

    @Autowired
    private ItemCatMapper itemCatMapper;

    //查询规格参数
    @Override
    public EasyUITable findParamByPage(Integer page, Integer rows) {
        //查询总记录数
        int total = itemParamMapper.selectCount(null);
        Integer start = (page-1) * rows;
        //分页查询
        List<ItemParam> params = itemParamMapper.findItemParamByPage(start,rows);

        //查询商品类目名称，并set到itemParam
        String itemCatName;
        for (ItemParam param : params) {
            //查询商品类目名称
            itemCatName = itemCatMapper.findItemCatName(param.getItemCatId());
            param.setItemCatName(itemCatName);
        }
        return new EasyUITable(total,params);
    }

    //添加规格参数
    @Override
    public void itemParamSave(Long itemCatId, ItemParam itemParam) {
        itemParam.setItemCatId(itemCatId).setCreated(new Date()).setUpdated(itemParam.getCreated());
        itemParamMapper.insert(itemParam);
    }


    //查询商品类目是否已添加？
    @Override
    public ItemParam findParamByItemCatId(Long itemCatId) {
        QueryWrapper<ItemParam> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("item_cat_id",itemCatId );
        ItemParam itemParam = itemParamMapper.selectOne(queryWrapper);
        return itemParam;
    }

    //删除规格参数
    @Override
    public void itemParamDelete(Long[] ids) {
        List<Long> idList = Arrays.asList(ids);
        itemParamMapper.deleteBatchIds(idList);
    }

    //修改规格参数
    @Override
    public void itemParamUpdate(ItemParam itemParam) {
        itemParamMapper.updateById(itemParam);
    }
}
