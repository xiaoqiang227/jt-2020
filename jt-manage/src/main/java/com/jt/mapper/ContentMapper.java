package com.jt.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.jt.pojo.Content;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ContentMapper extends BaseMapper<Content> {
    @Select("select * from tb_content where category_id=#{categoryId} limit #{start},#{rows}")
    List<Content> findContentList(Long categoryId, Long start, Long rows);
}
