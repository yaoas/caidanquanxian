package com.springboot.common.base.controller;
import com.springboot.common.support.HttpKit;
import com.springboot.common.utils.ReflectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.Serializable;

public abstract class BaseBeanController<Entity extends Serializable>  {

	/**
	 * 日志对象
	 */
	protected Logger logger = LoggerFactory.getLogger(getClass());
	/**
	 * 实体类型
	 */
	protected final Class<Entity> entityClass;

	protected BaseBeanController() {
		this.entityClass = ReflectionUtils.getSuperGenericType(getClass());
	}

	protected Entity newModel() {
		try {
			return entityClass.newInstance();
		} catch (Exception e) {
			throw new IllegalStateException("can not instantiated entity : " + this.entityClass, e);
		}
	}

	protected String getPara(String name) {
		return HttpKit.getRequest().getParameter(name);
	}

	protected void setAttr(String name, Object value) {
		HttpKit.getRequest().setAttribute(name, value);
	}

	public Object getAttr(String name) {
		return HttpKit.getRequest().getAttribute(name);
	}

	/**
	 * 通过请求头中的用户utoken，获取用户id
	 */
	public Long getUserId(){
		String uid = (String) getAttr("uid");
		return Long.parseLong(uid);
	}




}
