package com.springboot.admin.sys.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.springboot.admin.sys.entity.Menu;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 菜单管理
 * 
 * @author hs
 * @email 457030599@qq.com
 * @date 2018-10-08 16:33:17
 */
@Repository
@Mapper
public interface MenuMapper extends BaseMapper<Menu> {

    /**
     * 根据父菜单，查询子菜单
     * @param parentId 父菜单ID
     */
    List<Menu> queryListParentId(Long parentId);

    /**
     * 获取不包含按钮的菜单列表
     */
    List<Menu> queryNotButtonList();
	
}
