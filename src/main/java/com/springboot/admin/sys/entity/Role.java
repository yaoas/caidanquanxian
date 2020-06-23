package com.springboot.admin.sys.entity;

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
import java.util.List;

/**
 * 描述: 
 * author: Larry
 * date: 2020-05-26
 */
@TableName("sys_role")
@Data
@NoArgsConstructor
@AllArgsConstructor
@ApiModel("角色实体类")
public class Role extends DataEntity<Long> {

    @TableId(value="id", type= IdType.AUTO)
    @ApiModelProperty(value="",name="id")
    private Long id;

    @TableField(value = "role_name")
    @ApiModelProperty(value="角色名称")

    private String roleName;

    @TableField(value = "remark")
    @ApiModelProperty(value="备注")
    private String remark;

    @TableField(value = "open_status")
    @ApiModelProperty(value="是否启用")
    private Integer openStatus;

    @TableField(exist = false)
    private List<Long> menuIds;


    @TableField(exist = false)
    private String menuIdsStr;

    @TableField(exist = false)
    @ApiModelProperty(value="菜单名称集合")
    private String menuNames;
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