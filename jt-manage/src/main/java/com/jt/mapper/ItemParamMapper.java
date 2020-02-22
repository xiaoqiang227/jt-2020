package com.jt.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.jt.pojo.ItemParam;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ItemParamMapper extends BaseMapper<ItemParam> {
    @Select("select * from tb_item_param  limit #{start},#{rows}")
    List<ItemParam> findItemParamByPage(Integer start, Integer rows);

}
