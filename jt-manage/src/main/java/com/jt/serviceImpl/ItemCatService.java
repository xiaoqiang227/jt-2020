package com.jt.serviceImpl;

import com.jt.vo.EasyUITree;

import java.util.List;

public interface ItemCatService {

    String itemCatById(Long itemCatId);

    List<EasyUITree> findItemCatByParentId(Long parentId);

}
