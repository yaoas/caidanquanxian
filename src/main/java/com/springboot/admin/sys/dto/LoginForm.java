package com.springboot.admin.sys.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @description: 登陆实体类
 * @author: hs
 * @create: 2018-10-31 15:45:10
 **/
@Data
@ApiModel("登陆实体")
public class LoginForm {
    @ApiModelProperty(value =  "账号",required = true)
    private String username;
    @ApiModelProperty(value = "密码",required = true)
    private String password;
    @ApiModelProperty(value = "验证码",required = false)
    private String captcha;
}
