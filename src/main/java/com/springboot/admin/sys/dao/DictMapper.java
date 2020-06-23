package com.springboot.admin.sys.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.springboot.admin.sys.entity.Dict;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 字典表
 * 
 * @author hs
 * @email 457030599@qq.com
 * @date 2018-10-10 21:39:07
 */
@Repository
@Mapper
public interface DictMapper extends BaseMapper<Dict> {
    List<Dict> getByType(String type);
    String byIdGetName(Integer id);
}
