package com.springboot.admin.sys.dto;

import lombok.Data;

/**
 * @description: 微信登陆实体类
 * @author: hs
 * @create: 2018-10-31 15:45:10
 **/
@Data
public class WeChatLoginForm {

    private String openId;
    private String account;
    private String username;
    private  String avatar;
    private  String jsCode;
    private String password = "123456";
    private String weiXinNumber;
    private String sex;
}
