package com.jt.pojo;


import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.experimental.Accessors;

import java.util.Date;
import java.util.List;

@Data
@Accessors(chain = true)
@TableName("tb_order")
public class Order extends BasePojo{
    @TableField(exist = false)  //入库时忽略该字段
    private List<OrderItem> orderItems;
    @TableField(exist = false)  //入库时忽略该字段
    private OrderShipping orderShipping;

    @TableId
    private String orderId;  //订单id
    private String payment;  //实付金额，精确到2位小数
    private Integer paymentType; //支付类型 1、在线支付， 2、货到付款
    private String postFee;  //邮费，精确到2位小数
    private Integer status;      //状态：1、未付款，2、已付款，3、未发货，4、已发货，5、交易成功，6、交易关闭
    private Date paymentTime;//付款时间
    private Date consignTime;//发货时间
    private Date endTime;    //交易完成时间
    private Date closeTime;  //交易关闭时间
    private String shippingName;  //物流名称
    private String shippingCode;  //物流单号
    private Long userId;  // 用户id
    private String buyerMessage;  //买家留言
    private String buyerNick;  //买家昵称
    private Integer buyerRate;  //是否已评价  1:是  0：否

}
