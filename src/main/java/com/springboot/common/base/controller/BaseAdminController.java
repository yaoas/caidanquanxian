package com.springboot.common.base.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.springboot.common.base.DataBaseConstant;
import com.springboot.common.base.entity.DataEntity;
import com.springboot.common.base.service.ICommonService;
import com.springboot.common.utils.ObjectUtils;
import com.springboot.common.utils.R;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.*;

import java.io.Serializable;
import java.util.List;

public abstract class BaseAdminController <Entity extends DataEntity<ID> & Serializable,ID extends Serializable>
        extends BaseBeanController<Entity>{

    private ICommonService<Entity> commonService;

    /**
     * 设置基础service
     *
     * @param commonService
     */
    @Autowired
    public void setCommonService(ICommonService<Entity> commonService) {
        this.commonService = commonService;
    }

    public Entity get(ID id) {
        if (!ObjectUtils.isNullOrEmpty(id)) {
            return commonService.getById(id);
        } else {
            return newModel();
        }
    }

    @ApiOperation(value = "查询")
    @GetMapping(value = "/get/{id}")
    public Object getOne(@RequestBody @ApiParam(name="id",value="id",required=true) @PathVariable Long id) {
        Assert.notNull(id,"id不能为空");
        return R.ok(commonService.getById(id));
    }

    @ApiOperation(value = "列表")
    @GetMapping(value = "/list")
    public List<Entity> list() {

        return commonService.list(new QueryWrapper<Entity>());
    }

    @ApiOperation(value = "分页列表")
    @GetMapping(value = "/page/list/{currentPage}")
    public Object pageList(@ApiParam(name="currentPage",value="currentPage",required=true) @PathVariable Integer currentPage) {

        return R.ok(commonService.page(new Page<Entity>(currentPage, DataBaseConstant.PAGE_SIZE), new QueryWrapper<Entity>()));
    }



    @ApiOperation(value = "新增")
    @PostMapping(value = "/add")
    public Object add(@RequestBody @ApiParam(name="实体对象",value="传入json格式",required=true) Entity entity) {
        Assert.notNull(entity,"数据不能为空");

        if(commonService.save(entity)){
            return R.ok("新增成功");
        }
        else {
            return R.ok("新增失败");

        }

    }

    @ApiOperation(value = "修改")
    @PutMapping(value = "/update")
    public Object update(@RequestBody @ApiParam(name="实体对象",value="传入json格式",required=true) Entity entity) {
        Assert.notNull(entity,"entity不能为空");
        if(commonService.save(entity)){
            return R.ok("修改成功");
        }
        else {
            return R.ok("修改失败");

        }
    }

    @ApiOperation(value = "删除")
    @DeleteMapping(value = "/delete")
    public Object delete(@RequestBody @ApiParam(name="实体对象",value="传入json格式",required=true) Entity entity) {
        Assert.notNull(entity,"数据不能为空");

        if(commonService.removeById(entity)){
            return R.ok("删除成功");
        }
        else {
            return R.ok("删除失败");

        }
    }

    @ApiOperation(value = "通过id删除")
    @DeleteMapping(value = "/delete/{id}")
    public Object delete(@ApiParam(name="id",value="id",required=true) @PathVariable Long id) {
        Assert.notNull(id,"id不能为空");
        if(commonService.removeById(id)){
            return R.ok("删除成功");
        }
        else {
            return R.ok("删除失败");

        }

    }

    @ApiOperation(value = "通过id逻辑删除")
    @DeleteMapping(value = "/logic/delete/{id}")
    public Object logicDelete(@ApiParam(name="id",value="id",required=true) @PathVariable Long id) {
        Assert.notNull(id,"id不能为空");
        Entity entity = commonService.getById(id);
        entity.setDelFlag(DataBaseConstant.DEL_FLAG_DELETE);
        if(commonService.updateById(entity)){
            return R.ok("删除成功");
        }
        else {
            return R.ok("删除失败");

        }
    }

    @ApiOperation(value = "逻辑删除")
    @DeleteMapping(value = "/logic/delete")
    public Object logicDelete(@RequestBody @ApiParam(name="实体对象",value="传入json格式",required=true) Entity entity) {
        Assert.notNull(entity,"数据不能为空");
        entity.setDelFlag(DataBaseConstant.DEL_FLAG_DELETE);
        if(commonService.updateById(entity)){
            return R.ok("删除成功");
        }
        else {
            return R.ok("删除失败");

        }
    }
}
