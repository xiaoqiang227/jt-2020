package com.jt.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
@TableName("tb_cart")
public class Cart extends BasePojo {
    @TableId(type = IdType.AUTO)
    private Long id;  //购物车id
    private Long userId;  //用户id
    private Long itemId;  //商品id
    private String itemTitle;  //商品标题
    private String itemImage;  //图片url
    private Long itemPrice;  //商品单价
    private Integer num;  //数量
}
