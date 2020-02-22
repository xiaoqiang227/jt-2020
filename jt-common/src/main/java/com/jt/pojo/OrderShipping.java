package com.jt.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
@TableName("tb_order_shipping")
public class OrderShipping extends BasePojo{
    @TableId
    private String orderId;  //订单id
    private String receiverName;  //收货人全名
    private String receiverPhone;  //固定电话
    private String receiverMobile;  //移动电话
    private String receiverState;  //省份
    private String receiverCity;  //城市
    private String receiverDistrict;  //区/县
    private String receiverAddress;  //收货地址，如：xx路xx号
    private String receiverZip;  //邮政编码，如：310001
}
