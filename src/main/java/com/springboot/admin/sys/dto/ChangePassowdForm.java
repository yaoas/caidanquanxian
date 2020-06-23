package com.springboot.admin.sys.dto;

import lombok.Data;

import javax.validation.constraints.NotBlank;

/**
 * @description: 登陆实体类
 * @author: hs
 * @create: 2018-10-31 15:45:10
 **/
@Data
public class ChangePassowdForm {

    @NotBlank(message = "旧密码不能为空")
    private String oldPwd;
    @NotBlank(message = "新密码不能为空")
    private String newPwd;
    @NotBlank(message = "再次输入的新密码不能为空")
    private String password_confirm;
    //头像路径
    private String avatarUrl;
    //头像本地路径
    private String avatarRealUrl;
}
