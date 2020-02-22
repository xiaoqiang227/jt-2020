package com.jt.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.experimental.Accessors;
//商品规格参数
@Data
@Accessors(chain = true)
@TableName("tb_item_param_item")
public class ItemParamItem extends BasePojo{
    @TableId(type = IdType.AUTO)
    private Long id;
    private Long itemId;
    private String paramData;

}
