package com.springboot.admin.sys.sysrolemenu.entity;

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
@TableName("sys_role_menu")
@Data
@NoArgsConstructor
@AllArgsConstructor
@ApiModel("角色菜单关联实体类")
public class SysRoleMenuEntity extends DataEntity<Long> {

    @TableId(value="id", type= IdType.AUTO)
    @ApiModelProperty(value="")
    private Long id;

    @TableField(value = "role_id")
    @ApiModelProperty(value="角色ID")
    private Long roleId;

    @TableField(value = "menu_id")
    @ApiModelProperty(value="菜单ID")
    private Long menuId;

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