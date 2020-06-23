package com.springboot.admin.sys.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.springboot.common.base.entity.DataEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.List;

//import com.hsshy.beam.common.base.entity.DataEntity;

/**
 * 菜单管理
 * 
 * @author hs
 * @email 457030599@qq.com
 * @date 2018-10-08 16:33:17
 */
@Data
@NoArgsConstructor
@TableName("sys_menu")
@ApiModel(value = "菜单实体类")
public class Menu extends DataEntity<Long> {
	private static final long serialVersionUID = 1L;

	/**
	 * 
	 */
	@TableId
	private Long id;
	/**
	 * 父菜单ID，一级菜单为0
	 */
	@TableField(value = "parent_id")

	private Long parentId;
	/**
	 * 菜单名称
	 */
    @NotBlank(message = "菜单名称不能为空")
	@ApiModelProperty(value = "菜单名称")
	private String name;
	/**
	 * 菜单URL
	 */
    @NotBlank(message = "菜单URL不能为空")
    @ApiModelProperty(value = "菜单路径")
	private String url;
	/**
	 * 授权(多个用逗号分隔，如：business:list,business:create)
	 */
    @ApiModelProperty(value = "菜单授权")
	private String perms;
	/**
	 * 类型   0：目录   1：菜单   2：按钮
	 */
	private Integer type;
	/**
	 * 菜单图标
	 */
    @ApiModelProperty(value = "菜单图标")
	private String icon;
	/**
	 * 排序
	 */
	@TableField(value = "order_num")
    @NotNull(message = "菜单排序不能为空")
	private Integer orderNum;



	@TableField(exist=false)
	private List<?> list;

	@TableField(exist=false)
	private List<Menu> children;

	@TableField(exist = false)
	private String pname;

	@Override
	protected Serializable pkVal() {
        return this.id;
	}
}
