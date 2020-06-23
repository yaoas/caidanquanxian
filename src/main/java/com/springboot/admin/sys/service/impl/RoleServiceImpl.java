package com.springboot.admin.sys.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.springboot.admin.sys.dao.RoleMapper;
import com.springboot.admin.sys.entity.Menu;
import com.springboot.admin.sys.entity.Role;
import com.springboot.admin.sys.service.IMenuService;
import com.springboot.admin.sys.service.IRoleService;
import com.springboot.admin.sys.sysrolemenu.entity.SysRoleMenuEntity;
import com.springboot.admin.sys.sysrolemenu.service.SysRoleMenuService;
import com.springboot.common.utils.R;
import com.springboot.common.utils.ToolUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 角色
 *
 * @author hs
 * @email 457030599@qq.com
 * @date 2018-10-10 21:13:03
 */
@Service
public class RoleServiceImpl extends ServiceImpl<RoleMapper, Role> implements IRoleService {
    @Autowired
    private IMenuService menuService;
    @Autowired
    private SysRoleMenuService sysRoleMenuService;


    @Override
    public R deleteRole(Long[] roleIds) {
        if(ToolUtil.isEmpty(roleIds)||roleIds.length<=0){
            return R.fail("未选择删除的角色");
        }
        for(Long roleId:roleIds){
            Integer count = baseMapper.getCountByRoleId(roleId);
            if(count>0){
                return R.fail("当前删除的角色，还有用户关联，请先取消其关联");
            }
        }

        this.removeByIds(Arrays.asList(roleIds));
        return R.ok();
    }

    @Override
    public List<Long> getCheckMenuIds(Long roleId) {
        List<Long> checkMenuIds = baseMapper.getCheckMenuIds(roleId);
        Menu menu = new Menu();
        List<Long> checkMenuIdsChilder = new ArrayList<>();
        for(Long menuId :checkMenuIds){
           menu.setParentId(menuId);
           int count = baseMapper.selectIfHaveChildren(menu);
           if(count == 0){
               checkMenuIdsChilder.add(menuId);
           }
       }
        return checkMenuIdsChilder;
    }

    @Override
    @Transactional
    public R saveMuenPerms(Role role) {
        Role r = this.getById(role.getId());
         if(ToolUtil.isEmpty(r)){
            return R.fail("找不到该角色");
        }
        baseMapper.delMenuPermByRoleId(role.getId());
        if(role.getMenuIds().size()<=0){
            return R.ok();
        }
        //去重
        //List<Long> collect = role.getMenuIds().stream().distinct().collect(Collectors.toList());
        //全部都是子集 去寻找父集
        List<Long> collect = menuService.list(new QueryWrapper<Menu>().lambda().in(Menu::getId, role.getMenuIds())).stream().map(Menu::getParentId).distinct().collect(Collectors.toList());
        role.getMenuIds().addAll(collect);
        LinkedList<SysRoleMenuEntity> sysRoleMenuEntities = new LinkedList<>();
        for (Long menuId : role.getMenuIds()) {
            SysRoleMenuEntity sysRoleMenuEntity = new SysRoleMenuEntity();
            sysRoleMenuEntity.setRoleId(role.getId());
            sysRoleMenuEntity.setMenuId(menuId);
            sysRoleMenuEntities.add(sysRoleMenuEntity);
        }
        sysRoleMenuService.saveBatch(sysRoleMenuEntities);
        return R.ok("保存成功");
    }

}
