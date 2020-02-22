package com.jt.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.experimental.Accessors;
//网站内容
@Data
@Accessors(chain = true)
@TableName("tb_content")
public class Content extends BasePojo{
    @TableId(type = IdType.AUTO)
    private Long id;
    private Long categoryId;  //内容类目ID
    private String title; //内容标题
    private String subTitle;  //子标题
    private String titleDesc;  //标题描述
    private String url;  //链接
    private String pic;  //图片绝对路径
    private String pic2;  // 图片2
    private String content;  //内容
}
