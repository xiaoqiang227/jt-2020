package com.jt.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.experimental.Accessors;
//规格参数
@Data
@Accessors(chain = true)
@TableName("tb_item_param")
public class ItemParam extends BasePojo{
    @TableId(type = IdType.AUTO)
    private Long id;
    private Long itemCatId;

    @TableField(exist=false)  //表示数据库操作时忽略该字段
    private String itemCatName;

    private String paramData;
}
