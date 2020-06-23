package com.springboot.admin.sys.controller;

import com.springboot.admin.sys.entity.Dict;
import com.springboot.admin.sys.service.IDictService;
import com.springboot.common.utils.R;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

/**
 * 字典表
 *
 * @author hs
 * @email 457030599@qq.com
 * @date 2018-10-10 21:39:07
 */
@Api(value="DictController",tags={"Dict接口"})
@RestController
@RequestMapping("/sys/dict")
public class DictController {

    @Autowired
    private IDictService dictService;

    /**
     * 根据字典类型获取字典值
     */
    @RequestMapping("/getByType")
    public Object getByType(@RequestBody Map<String,String> map) {
        String type = map.get("type");
        List<Dict> dicts = dictService.getByType(type);
        return R.ok(dicts);
    }






}