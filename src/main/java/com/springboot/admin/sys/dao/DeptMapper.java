package com.springboot.admin.sys.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.springboot.admin.sys.entity.Dept;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 部门管理
 * 
 * @author hs
 * @email 457030599@qq.com
 * @date 2018-12-17 15:21:00
 */
@Repository
@Mapper
public interface DeptMapper extends BaseMapper<Dept> {

    IPage<Dept> selectPageList(Page page, @Param("dept") Dept dept);

    List<Dept> queryListParentId(Long parentId);



}
