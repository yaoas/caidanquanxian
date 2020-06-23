package com.springboot.common.base.entity;
import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;

import java.util.Date;

/**
 * 自定义数据Entity类
 *
 *
 */
public abstract class CustomEntity<ID> extends AbstractEntity<ID> {


	@TableField(value = "create_time", fill = FieldFill.INSERT)
	private Date createTime; // 创建日期

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}


}
