package com.springboot.admin.sys.sysuserrole.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.springboot.common.base.entity.DataEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
/**
 * 描述: 
 * author: Larry
 * date: 2020-05-27
 */
@TableName("sys_user_role")
@Data
@NoArgsConstructor
@AllArgsConstructor
@ApiModel("用户角色关联实体类")
public class SysUserRoleEntity extends DataEntity<Long> {

    @TableId(value="id", type= IdType.AUTO)
    @ApiModelProperty(value="")
    private Long id;

    @TableField(value = "user_id")
    @ApiModelProperty(value="用户ID")
    private Long userId;

    @TableField(value = "role_id")
    @ApiModelProperty(value="角色ID")
    private Long roleId;

    public SysUserRoleEntity(Long userId, Long roleId) {
        this.userId = userId;
        this.roleId = roleId;
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