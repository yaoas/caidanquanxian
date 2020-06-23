package com.springboot.admin.sys.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.springboot.admin.sys.entity.Dict;

import java.util.List;

/**
 * 字典表
 *
 * @author hs
 * @email 457030599@qq.com
 * @date 2018-10-10 21:39:07
 */
public interface IDictService extends IService<Dict> {
    List<Dict> getByType(String type);
    //通过字典id得到名称
    String byIdGetName(Integer id);
}
