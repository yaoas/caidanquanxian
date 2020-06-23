package com.springboot.admin.sys.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.springboot.admin.sys.entity.User;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

/**
 * 管理员表
 * 
 * @author hs
 * @email 457030599@qq.com
 * @date 2018-10-07 18:03:20
 */
@Repository
@Mapper
public interface UserMapper extends BaseMapper<User> {

}
