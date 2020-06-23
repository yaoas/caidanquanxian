package com.springboot.admin.sys.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.springboot.admin.sys.dao.MenuMapper;
import com.springboot.admin.sys.entity.Menu;
import com.springboot.admin.sys.service.IMenuService;
import com.springboot.admin.sys.service.IUserService;
import com.springboot.common.comment.Constant;
import com.springboot.common.utils.ToolUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 菜单管理
 *
 * @author hs
 * @email 457030599@qq.com
 * @date 2018-10-08 16:33:17
 */
@Service
public class MenuServiceImpl extends ServiceImpl<MenuMapper, Menu> implements IMenuService {

    @Autowired
    private IUserService userService;


    @Override
    public List<Menu> queryListParentId(Long parentId, List<Long> menuIdList) {
        List<Menu> menuList = queryListParentId(parentId);
        if (menuIdList == null) {
            return menuList;
        }

        List<Menu> userMenuList = new ArrayList<>();
        for (Menu menu : menuList) {
            if (menuIdList.contains(menu.getId())) {
                userMenuList.add(menu);
            }
        }
        return userMenuList;
    }


    @Override
    public List<Menu> queryListParentId(Long parentId) {
        return baseMapper.queryListParentId(parentId);
    }


    @Override
    public List<Menu> treeMenuList(Long menuId, Menu menu) {
        List<Menu> menuList;
        if (ToolUtil.isNotEmpty(menu.getName())) {
            //找所有的1级id
            //所有的一级二级
            List<Menu> all = this.list(new QueryWrapper<Menu>().lambda().like(Menu::getName, menu.getName()));
            //所有一级id
            List<Long> oneLevel = all.stream().filter(item -> item.getParentId() == 0).map(item -> item.getId()).collect(Collectors.toList());
            if (oneLevel.size()>0){
                menuList = list(new QueryWrapper<Menu>().lambda().in(Menu::getId, oneLevel));
            }else{
                menuList=new ArrayList<>();
            }
            getAllMenuTreeList(menuList);
            //所有二级id
            List<Long> twoLevel = all.stream().filter(item -> item.getParentId() != 0).map(item -> item.getId()).collect(Collectors.toList());

            //创建一个集合 存储后加入的父集合
            List<Menu> list = new LinkedList<>();
            continueOut:
            for (Long item : twoLevel) {
                LinkedList<Menu> menus = new LinkedList<>();
                //单个子集
                Menu one = getOne(new QueryWrapper<Menu>().lambda().eq(Menu::getId, item));
                Long parentId = one.getParentId();
                //如果一级中没有
                if (!oneLevel.contains(parentId)) {
                    //查询后增加的
                    List<Long> collect = list.stream().map(a -> a.getId()).collect(Collectors.toList());
                    for (Menu menu1 : list) {
                        if (menu1.getId().equals(parentId)){
                            list.get(collect.indexOf(parentId)).getChildren().add(one);
                            continue continueOut;
                        }
                    }
                    //查找这个父集
                    Menu parent = getOne(new QueryWrapper<Menu>().lambda().eq(Menu::getId, one.getParentId()));
                    menus.add(one);
                    parent.setChildren(menus);
                    list.add(parent);
                }
            }
            menuList.addAll(list);
            return menuList;
        } else {
            menuList = queryListParentId(menuId);
            return getAllMenuTreeList(menuList);
        }

    }


    @Override
//    @Cacheable(value = Cache.CONSTANT, key = "'" + CacheKey.USER_ID + "'+#userId")
    public List<Menu> getUserMenuList(Long userId) {

        //系统管理员，拥有最高权限
        if (userId == Constant.SUPER_ADMIN) {
            return getAllMenuList(null);
        }

        //用户菜单列表
        List<Long> menuIdList = new ArrayList<>();
        return getAllMenuList(menuIdList);
    }

    /**
     * 获取所有菜单列表
     */
    private List<Menu> getAllMenuList(List<Long> menuIdList) {
        //查询根菜单列表
        List<Menu> menuList = queryListParentId(0L, menuIdList);
        //递归获取子菜单
        getMenuTreeList(menuList, menuIdList);

        return menuList;
    }

    /**
     * 获取目录和菜单 递归
     */
    private List<Menu> getMenuTreeList(List<Menu> menuList, List<Long> menuIdList) {
        List<Menu> subMenuList = new ArrayList<Menu>();

        for (Menu entity : menuList) {
            //目录

            entity.setList(getMenuTreeList(queryListParentId(entity.getId(), menuIdList), menuIdList));

            subMenuList.add(entity);
        }

        return subMenuList;
    }

    /**
     * 获取所有菜单 递归
     */
    private List<Menu> getAllMenuTreeList(List<Menu> menuList) {
        List<Menu> subMenuList = new ArrayList<Menu>();

        for (Menu entity : menuList) {
            //目录
            List<Menu> allMenuTreeList = getAllMenuTreeList(queryListParentId(entity.getId()));
            allMenuTreeList.stream().forEach(item -> item.setPname(entity.getName()));
            entity.setChildren(allMenuTreeList);
            subMenuList.add(entity);
        }

        return subMenuList;
    }
}
