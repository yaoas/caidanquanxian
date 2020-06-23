
package com.springboot.common.base.entity;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import com.springboot.common.base.DataBaseConstant;
import org.apache.commons.lang3.builder.ReflectionToStringBuilder;

/**
 * 抽象实体基类
 * 
 * @author hfBeam
 *
 * 
 */
@SuppressWarnings("serial")
public abstract class AbstractEntity<ID> extends Model {

	@TableField(exist = false)
	public long currentPage;
	@TableField(exist = false)
	public long pageSize = DataBaseConstant.PAGE_SIZE;

	public abstract ID getId();

	public abstract void setId(ID id);


	@Override
	public String toString() {
		return ReflectionToStringBuilder.toString(this);
	}

	public long getCurrentPage() {
		return currentPage;
	}

	public void setCurrentPage(long currentPage) {
		this.currentPage = currentPage;
	}

	public long getPageSize() {
		return pageSize;
	}

	public void setPageSize(long pageSize) {
		this.pageSize = pageSize;
	}
}
