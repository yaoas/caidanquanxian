package com.springboot.admin.sys.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.springboot.admin.sys.dao.DictMapper;
import com.springboot.admin.sys.entity.Dict;
import com.springboot.admin.sys.service.IDictService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 字典表
 *
 * @author hs
 * @email 457030599@qq.com
 * @date 2018-10-10 21:39:07
 */
@Service
public class DictServiceImpl extends ServiceImpl<DictMapper, Dict> implements IDictService {
    @Autowired
    private DictMapper dictMapper;

    @Override
    public List<Dict> getByType(String type) {
        List<Dict> dicts = dictMapper.getByType(type);
        return dicts;
    }

    @Override
    public String byIdGetName(Integer id) {

        return dictMapper.byIdGetName(id);
    }
}
