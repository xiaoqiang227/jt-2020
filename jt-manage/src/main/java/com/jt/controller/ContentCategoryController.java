package com.jt.controller;

import com.jt.pojo.ContentCategory;
import com.jt.serviceImpl.ContentCategoryService;
import com.jt.vo.EasyUITree;
import com.jt.vo.SysResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/content/category")
public class ContentCategoryController {
    @Autowired
    private ContentCategoryService contentCategoryService;
    //查询网站内容分类目录
    @RequestMapping("/list")
    public List<EasyUITree> findContentList(@RequestParam(value = "id",defaultValue = "0") Long parentId){
        List<EasyUITree> categoryTree = contentCategoryService.findCategoryTree(parentId);
        return categoryTree;
    }

    //新增网站内容分类目录
    @RequestMapping("/create")
    public SysResult ContentCreate(ContentCategory contentCategory){
        contentCategoryService.ContentCreate(contentCategory);
        return SysResult.success();
    }

    //修改网站内容分类目录名称
    @RequestMapping("/update")
    public SysResult ContentUpdate(ContentCategory contentCategory){
        contentCategoryService.ContentUpdate(contentCategory);
        return SysResult.success();
    }


    //删除网站内容分类目录
    @RequestMapping("/delete")
    public SysResult ContentDelete(Long id){
        contentCategoryService.CategoryDelete(id);
        System.out.println("执行了删除类目操作！  id： "+id);
        return SysResult.success();

    }
}
