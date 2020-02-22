package com.jt.serviceImpl;

import com.jt.pojo.Content;
import com.jt.vo.EasyUITable;

public interface ContentService {
    EasyUITable findContentList(Long categoryId, Long page, Long rows);

    void contentSave(Content content);

    void contentEdit(Content content);

    void contentDelete(Long[] ids);
}
