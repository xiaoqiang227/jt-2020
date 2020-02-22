package com.jt.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.jt.pojo.ItemCat;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.PathVariable;

@Repository
public interface ItemCatMapper extends BaseMapper<ItemCat> {
    @Select("select name from tb_item_cat where id=#{itemCatId}")
    String findItemCatName(@PathVariable Long itemCatId);
}
