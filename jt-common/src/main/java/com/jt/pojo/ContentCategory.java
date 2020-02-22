package com.jt.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.experimental.Accessors;
//网站内容分类
@Data
@Accessors(chain = true)
@TableName("tb_content_category")
public class ContentCategory extends BasePojo{
    @TableId(type = IdType.AUTO)
    private Long id;
    private Long parentId;  //父类ID
    private String name;  //分类名称
    private Integer status;  //状态（1正常，2删除）
    private Integer sortOrder;  //排列序号，表示同级类目的展现次序，如数值相等则按名称次序排列。取值范围:大于零的整数
    private Boolean isParent;  //该类目是否为父类目，1为true，0为false
}
