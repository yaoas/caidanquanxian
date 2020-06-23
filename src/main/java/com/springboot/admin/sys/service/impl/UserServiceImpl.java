package com.springboot.admin.sys.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.springboot.admin.sys.dao.UserMapper;
import com.springboot.admin.sys.entity.User;
import com.springboot.admin.sys.service.IUserService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * 管理员表
 *
 * @author hs
 * @email 457030599@qq.com
 * @date 2018-10-07 18:03:20
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements IUserService {

}
