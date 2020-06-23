package com.springboot.admin.sys.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.springboot.admin.sys.entity.Menu;
import com.springboot.admin.sys.entity.Role;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;


/**
 * 角色
 * 
 * @author hs
 * @email 457030599@qq.com
 * @date 2018-10-10 21:13:03
 */
@Mapper
@Repository
public interface RoleMapper extends BaseMapper<Role> {

    IPage<Role> selectPageList(Page page, @Param("role") Role role);

    Integer getCountByRoleId(Long roleId);

    List<Long> getCheckMenuIds(Long roleId);

    void saveMenuPerms(@Param("param") Map<String, Long> map);

    void delMenuPermByRoleId(Long roleId);
    /**
     * 查詢是否有子機
     */
   int selectIfHaveChildren(Menu menu);
}
