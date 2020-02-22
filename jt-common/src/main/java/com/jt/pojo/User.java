package com.jt.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.experimental.Accessors;

@Data
@TableName("tb_user")
@Accessors(chain = true)
public class User extends BasePojo{
    @TableId(type = IdType.AUTO)
    private Long id;            //用户id
    private String username;    //用户名
    private String password;    //密码
    private String phone;       //手机号
    private String email;       //邮箱
}
