package com.springboot.admin.sys.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.springboot.admin.sys.entity.Menu;

import java.util.List;

/**
 * 菜单管理
 *
 * @author hs
 * @email 457030599@qq.com
 * @date 2018-10-08 16:33:17
 */
public interface IMenuService extends IService<Menu> {

    /**
     * 根据父菜单，查询子菜单
     * @param parentId 父菜单ID
     * @param menuIdList  用户菜单ID
     */
    List<Menu> queryListParentId(Long parentId, List<Long> menuIdList);

    /**
     * 获取该角色的菜单列表
     */
    List<Menu> getUserMenuList(Long userId);

    /**
     * 根据父菜单，查询子菜单
     * @param parentId 父菜单ID
     */
    List<Menu> queryListParentId(Long parentId);

    /**
     * 获取树形菜单列表
     */
    List<Menu> treeMenuList(Long menuId, Menu menu);


}
