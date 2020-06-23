package com.springboot.admin.sys.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.springboot.common.base.entity.DataEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.net.URLDecoder;
import java.util.Date;
import java.util.List;

/**
 * 管理员表
 * 
 * @author hs
 * @email 457030599@qq.com
 * @date 2018-10-07 18:03:20
 */
@NoArgsConstructor
@AllArgsConstructor
@Data
@TableName("sys_user")
@ApiModel("用户实体类")
public class User extends DataEntity<Long> {

	/**
	 * 主键id
	 */
	@TableId
    @ApiModelProperty(value="主键id",name="id")
	private Long id;
	/**
	 * 头像路径
	 */
    @ApiModelProperty(value="头像路径",name="avatar_url")
	private String avatarUrl;


    @ApiModelProperty(value="头像本地路径",name="avatar_real_url")
	private String avatarRealUrl;
	/**
	 * 账号
	 */

    @TableField(value = "account")
    @NotBlank(message = "账号不能为空")
    @ApiModelProperty(value="账号",name="account")
	private String account;
	/**
	 * 密码
	 */
    @TableField(value = "password")
    @ApiModelProperty(value="密码",name="password")
	private String password;
	/**
	 * md5密码盐
	 */
	private String salt;
	/**
	 * 名字
	 */
    @TableField(value = "name")
    @NotBlank(message = "姓名不能为空")
    @ApiModelProperty(value="名字/昵称",name="name")
	private String name;

    @TableField(value = "dept_id")
    @ApiModelProperty(value="部门ID",name="deptId")
	private Long deptId;

    @TableField(value = "birthday")
    @ApiModelProperty(value="生日",name="birthday")
	private Date birthday;

    @TableField(value = "sex")
    @ApiModelProperty(value="性别（1：男 2：女）",name="sex")
	private Integer sex;

    @TableField(value = "email")
    @ApiModelProperty(value="电子邮件",name="email")
	private String email;

    @TableField(value = "phone")
    @ApiModelProperty(value="电话",name="phone")
	private String phone;

    @TableField(value = "status")
    @ApiModelProperty(value="状态(1：启用  2：冻结  3：删除）",name="status")
	private Integer status;


	@TableField(exist = false)
	private String deptName;

	//查询
	@TableField(exist = false)
	private String deptIds;

	@TableField(exist = false)
	private List<Long> roleIds;
	@TableField("openid")
	private String openId;
	/**
	 * 角色名称
	 */
	@TableField(exist = false)
    @ApiModelProperty(value="拥有角色，字符串",name="roleNameString")
	private String roleNameString;

    @TableField(value = "user_type")
    @ApiModelProperty(value="用户类型（1PC端用户2小程序用户）",name="userType")
	private String userType;

    @TableField(value = "money_element")
    @ApiModelProperty(value="余额,单位为元",name="moneyElement")
    private BigDecimal moneyElement;

    @TableField(value = "last_login_time")
    @ApiModelProperty(value="最后一次登录时间",name="lastLoginTime")
    private Date lastLoginTime;

    @TableField(exist = false)
    @ApiModelProperty(value="用户解码后的名称",name="userNickNameUTF8")
    private String userNickNameUTF8;

    public String getUserNickNameUTF8() {
        try {
            //UTF-8解码后的字符
            return URLDecoder.decode(name, "utf-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return "";
    }

    public void setUserNickNameUTF8(String userNickNameUTF8) {
        this.userNickNameUTF8 = userNickNameUTF8;
    }


    @Override
    public Long getId() {
        return id;
    }

    @Override
    public void setId(Long id) {
        this.id = id;
    }

    @Override
    protected Serializable pkVal() {
        return null;
    }

}
