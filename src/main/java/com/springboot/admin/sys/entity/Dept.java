package com.springboot.admin.sys.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.springboot.common.base.entity.DataEntity;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;


/**
 * 部门管理
 *
 * @author hs
 */
@Data
@NoArgsConstructor
@TableName("sys_dept")
public class Dept extends DataEntity<Long> {

    @TableId(value="id", type= IdType.AUTO)
    @ApiModelProperty(value="部门主键",name="id")
    private Long id;

    @TableField(value = "parent_id")
    @ApiModelProperty(value="上级部门ID，一级部门为0",name="parent_id")
    private Long parentId;

    @TableField(value = "name")
    @ApiModelProperty(value="部门名称",name="name")
    private String name;

    @TableField(value = "order_num")
    @ApiModelProperty(value="排序",name="order_num")
    private Integer orderNum;


    @TableField(exist = false)
    private List<?> children;

    @TableField(exist = false)
    private String label;

    @TableField(exist = false)
    private String pname;


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
        return this.id;
    }
}
