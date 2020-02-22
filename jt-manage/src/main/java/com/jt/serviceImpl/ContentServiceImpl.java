package com.jt.serviceImpl;

import com.jt.mapper.ContentMapper;
import com.jt.pojo.Content;
import com.jt.vo.EasyUITable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

@Service
public class ContentServiceImpl implements ContentService{

    @Autowired
    private ContentMapper contentMapper;

    // 网站内容分页查询
    @Override
    public EasyUITable findContentList(Long categoryId, Long page, Long rows) {
        Integer total = contentMapper.selectCount(null);
        Long start = (page-1) * rows;
        List<Content> contents = contentMapper.findContentList(categoryId,start,rows);
        return new EasyUITable(total,contents);
    }



    //新增网站内容
    @Override
    public void contentSave(Content content) {
        contentMapper.insert(content);
    }

    //编辑网站内容
    @Override
    public void contentEdit(Content content) {
        contentMapper.updateById(content);
    }

    //删除网站内容
    @Override
    public void contentDelete(Long[] ids) {
        List<Long> idList = Arrays.asList(ids);
        contentMapper.deleteBatchIds(idList);
    }
}
