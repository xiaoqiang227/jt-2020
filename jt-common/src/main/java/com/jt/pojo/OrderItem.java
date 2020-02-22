package com.jt.pojo;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
@TableName("tb_order_item")
public class OrderItem extends BasePojo{
    @TableId
    private String itemId;  //商品id
    @TableId
    private String orderId;  //订单id
    private Integer num;    //商品购买数量
    private String title;  //商品标题
    private Long price;  //商品单价
    private Long totalFee;  //商品总金额
    private String picPath;  //商品图片地址

}
