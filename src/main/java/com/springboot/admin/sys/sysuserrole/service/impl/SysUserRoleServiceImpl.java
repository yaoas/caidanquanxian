package com.springboot.admin.sys.sysuserrole.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.springboot.admin.sys.sysuserrole.dao.SysUserRoleMapper;
import com.springboot.admin.sys.sysuserrole.entity.SysUserRoleEntity;
import com.springboot.admin.sys.sysuserrole.service.SysUserRoleService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * 描述:实现类
 * author: Larry
 * date: 2020-05-27
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class SysUserRoleServiceImpl extends ServiceImpl<SysUserRoleMapper, SysUserRoleEntity> implements SysUserRoleService {


}
