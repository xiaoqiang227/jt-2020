package com.jt.serviceImpl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.jt.mapper.ContentCategoryMapper;
import com.jt.pojo.ContentCategory;
import com.jt.vo.EasyUITree;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class ContentCategoryServiceImpl implements ContentCategoryService {

    @Autowired
    private ContentCategoryMapper contentCategoryMapper;

    //网站内容分类目录
    @Override
    public List<EasyUITree> findCategoryTree(Long parentId) {
        //根据父级id查询出所有的网站内容分类目录
        List<ContentCategory> contentCategoryList = findItemCatList(parentId);

        List<EasyUITree> treeList = new ArrayList<>();

        for (ContentCategory contentCategory : contentCategoryList) {
            //获取该商品目录的id，名称，状态，并存入集合中
            Long id = contentCategory.getId();
            String name = contentCategory.getName();
            //如果该网站内容分类是父级则closed，不是则open
            String state = contentCategory.getIsParent()?"closed":"open";
            EasyUITree easyUITree = new EasyUITree(id, name, state);
            treeList.add(easyUITree);
        }
        return treeList;
    }

    //根据父级id查询出所有的商品分类目录

    private List<ContentCategory> findItemCatList(Long parentId) {
        QueryWrapper<ContentCategory> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("parent_id",parentId);
        //根据传入的商品父级id查询出所有的商品分类目录信息
        List<ContentCategory> contentCategoryList = contentCategoryMapper.selectList(queryWrapper);
        return contentCategoryList;
    }

    //新增网站内容分类目录
    @Override
    public void ContentCreate(ContentCategory contentCategory) {
        // 求出当前父类类目下的目录之和
        QueryWrapper<ContentCategory> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("parent_id",contentCategory.getParentId());
        int total = contentCategoryMapper.selectCount(queryWrapper);
        contentCategory
                .setStatus(1)    //状态
                .setSortOrder(++total)  //在同级的排序号（目录之和+1）
                .setIsParent(false)  //是否为父级
                .setCreated(new Date())  //新增时间
                .setUpdated(contentCategory.getCreated());  //更新时间
        contentCategoryMapper.insert(contentCategory);
    }

    //修改网站内容分类目录名称
    @Override
    public void ContentUpdate(ContentCategory contentCategory) {
        contentCategory.setUpdated(new Date());
        contentCategoryMapper.updateById(contentCategory);
    }

    //删除网站内容分类目录
    @Override
    public void CategoryDelete(Long id) {
        contentCategoryMapper.deleteById(id);
    }

}
