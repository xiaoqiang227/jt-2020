package com.jt.serviceImpl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.jt.mapper.ItemCatMapper;
import com.jt.pojo.ItemCat;
import com.jt.vo.EasyUITree;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ItemCatServiceImpl implements ItemCatService{

    @Autowired
    private ItemCatMapper itemCatMapper;

    //查询子类目录名称
    @Override
    public String itemCatById(Long itemCatId) {
        //查询商品子类目信息
        String itemCatName = itemCatMapper.findItemCatName(itemCatId);
        return itemCatName;
    }

    //查询所有分类目录
    @Override
    public List<EasyUITree> findItemCatByParentId(Long parentId) {
        //根据父级id查询出所有的商品分类目录
        List<ItemCat> itemCatList = findItemCatList(parentId);

        List<EasyUITree> treeList = new ArrayList<>();

        for (ItemCat itemCat : itemCatList) {
            //获取该商品目录的id，名称，状态，并存入集合中
            Long id = itemCat.getId();
            String name = itemCat.getName();
            //如果该商品分类是父级则closed，不是则open
            String state = itemCat.getIsParent()?"closed":"open";
            EasyUITree easyUITree = new EasyUITree(id, name, state);
            treeList.add(easyUITree);
        }
        return treeList;
    }

    //根据父级id查询出所有的商品分类目录
    private List<ItemCat> findItemCatList(Long parentId) {
        QueryWrapper<ItemCat> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("parent_id",parentId);
         //根据传入的商品父级id查询出所有的商品分类目录信息
        List<ItemCat> itemCatList = itemCatMapper.selectList(queryWrapper);
        return itemCatList;
    }
}
