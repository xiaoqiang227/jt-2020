package com.jt.serviceImpl;

import com.jt.pojo.ContentCategory;
import com.jt.vo.EasyUITree;

import java.util.List;

public interface ContentCategoryService {

    List<EasyUITree> findCategoryTree(Long parentId);

    void CategoryDelete(Long id);

    void ContentCreate(ContentCategory contentCategory);

    void ContentUpdate(ContentCategory contentCategory);
}
