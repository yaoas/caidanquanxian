package com.springboot.admin.sys.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.springboot.common.base.entity.AbstractEntity;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;


/**
 * 字典表
 * 
 * @author hs
 * @email 457030599@qq.com
 * @date 2018-10-10 21:39:07
 */
@Data
@NoArgsConstructor
@TableName("sys_dict")
public class Dict extends AbstractEntity<Long> {
	private static final long serialVersionUID = 1L;

	/**
	 * 主键id
	 */
	@TableId
	private Long id;
	/**
	 * 排序
	 */
	private Integer num;
	/**
	 * 父级字典
	 */
	private Long pid;
	/**
	 * 名称
	 */
	private String name;
	/**
	 * 提示
	 */
	private String tips;


	@Override
	protected Serializable pkVal() {
        return this.id;
	}
}
