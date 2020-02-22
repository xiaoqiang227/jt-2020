package com.jt.controller;

import com.jt.pojo.Content;
import com.jt.serviceImpl.ContentService;
import com.jt.vo.EasyUITable;
import com.jt.vo.SysResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("content")
public class ContentController {

    @Autowired
    private ContentService contentService;

    //查询网站内容
    @RequestMapping("/query/list")
    public EasyUITable findContentByPage(Long categoryId, Long page, Long rows) {
        EasyUITable contentList = contentService.findContentList(categoryId, page, rows);
        System.out.println(contentList);
        return contentList;
    }

    //新增网站内容
    @RequestMapping("/save")
    public SysResult contentSave(Content content) {
        System.out.println(content);
        contentService.contentSave(content);
        return SysResult.success();
    }

    //编辑网站内容
    @RequestMapping("/edit")
    public SysResult contentEdit(Content content) {
        System.out.println(content);
        contentService.contentEdit(content);
        return SysResult.success();
    }

    //删除网站内容
    @RequestMapping("/delete")
    public SysResult contentDelete(Long... ids) {
        contentService.contentDelete(ids);
        return SysResult.success();
    }

}
