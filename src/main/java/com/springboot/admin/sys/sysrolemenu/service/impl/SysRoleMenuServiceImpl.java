package com.springboot.admin.sys.sysrolemenu.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.springboot.admin.sys.sysrolemenu.dao.SysRoleMenuMapper;
import com.springboot.admin.sys.sysrolemenu.entity.SysRoleMenuEntity;
import com.springboot.admin.sys.sysrolemenu.service.SysRoleMenuService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * 描述:实现类
 * author: Larry
 * date: 2020-05-27
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class SysRoleMenuServiceImpl extends ServiceImpl<SysRoleMenuMapper, SysRoleMenuEntity> implements SysRoleMenuService {


}
