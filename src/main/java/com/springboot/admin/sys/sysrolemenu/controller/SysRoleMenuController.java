package com.springboot.admin.sys.sysrolemenu.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.springboot.admin.sys.sysrolemenu.entity.SysRoleMenuEntity;
import com.springboot.admin.sys.sysrolemenu.entity.swagger.SysRoleMenuSwaggerListEntity;
import com.springboot.admin.sys.sysrolemenu.entity.swagger.SysRoleMenuSwaggerPageBaseEntity;
import com.springboot.admin.sys.sysrolemenu.service.SysRoleMenuService;
import com.springboot.common.base.controller.BaseController;
import com.springboot.common.comment.CommonOkResponseEntity;
import com.springboot.common.utils.R;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * 描述:
 * author: Larry
 * date: 2020-05-27
 */
@RestController
@Api(value = "角色菜单关联",tags = {"角色菜单关联模块"})
@RequestMapping(value="/sysRoleMenu")
public class SysRoleMenuController extends BaseController {

    @Autowired
    private SysRoleMenuService sysRoleMenuService;

    /**
    * 描述:   分页查询方法
    * author: Larry
    * date: 2020-05-27
    */
    @RequestMapping(value="/page/list",method = RequestMethod.POST)
    @ApiOperation(value="分页查询")
    @ApiOperationSupport(
        responses = @DynamicResponseParameters(properties = {
                 @DynamicParameter(value = "查询结果",name = "responsePageEnity",dataTypeClass = SysRoleMenuSwaggerPageBaseEntity.class)
         })
    )
    public R pageList(@ApiParam(name ="sysRoleMenu",required = true,value = "根据传入的实体类查询") @RequestBody SysRoleMenuEntity sysRoleMenu){
        QueryWrapper<SysRoleMenuEntity> queryWrapper = new QueryWrapper(sysRoleMenu);
        queryWrapper.orderByDesc("id");
        IPage<SysRoleMenuEntity> page = sysRoleMenuService.page(new Page(sysRoleMenu.getCurrentPage(),sysRoleMenu.getPageSize()),queryWrapper);

        return R.ok(page);
     }


    /**
    * 描述:   查询list集合方法
    * author: Larry
    * date: 2020-05-27
    */
    @ApiOperation(value="集合查询",response = SysRoleMenuEntity.class)
    @RequestMapping(value="/list",method = RequestMethod.POST)
    @ApiOperationSupport(
        responses = @DynamicResponseParameters(properties = {
                @DynamicParameter(value = "查询结果",name = "responseListEnity",dataTypeClass = SysRoleMenuSwaggerListEntity.class)
        })
    )
    public R list(@ApiParam(name ="sysRoleMenu",required = true,value = "根据传入的实体类查询") @RequestBody SysRoleMenuEntity sysRoleMenu){
        QueryWrapper<SysRoleMenuEntity> queryWrapper = new QueryWrapper(sysRoleMenu);
        queryWrapper.orderByDesc("id");
        List list = sysRoleMenuService.list(queryWrapper);
        return R.ok(list);
     }


    /**
    * 描述: 根据id查询方法
    * author: Larry
    * date: 2020-05-27
    */
    @ApiOperation(value="根据id查询",response = SysRoleMenuEntity.class)
    @ApiOperationSupport(
        responses = @DynamicResponseParameters(properties = {
            @DynamicParameter(value = "响应信息,出错显示错误的具体信息",name = "msg"),
            @DynamicParameter(value = "响应状态值,200为成功",name = "code"),
            @DynamicParameter(value = "实体类",name = "data",dataTypeClass = SysRoleMenuEntity.class),
            @DynamicParameter(value = "是否失败",name = "error"),
        })
    )
    @RequestMapping(value="/queryById",method = RequestMethod.POST)
    public R selectById(@ApiParam(name ="sysRoleMenu",required = true,value = "根据类中id查询") @RequestBody SysRoleMenuEntity sysRoleMenu){
        sysRoleMenu = sysRoleMenuService.getById(sysRoleMenu.getId());
        return R.ok(sysRoleMenu);
    }

    /**
    * 描述:  保存方法
    * author: Larry
    * date: 2020-05-27
    */
    @ApiOperation(value="保存",response = CommonOkResponseEntity.class)
    @RequestMapping(value="/save",method = RequestMethod.POST)
    public R save(@ApiParam(name ="sysRoleMenu",required = true,value = "要保存的实体类") @RequestBody SysRoleMenuEntity sysRoleMenu){

        boolean result = sysRoleMenuService.save(sysRoleMenu);
        if(result){
            return R.ok("添加成功！");
        } else {
            return R.fail("添加失败！");
        }
    }
    /**
    * 描述:  更新方法
    * author: Larry
    * date: 2020-05-27
    */
    @ApiOperation(value="更新",response = CommonOkResponseEntity.class)
    @RequestMapping(value="/update",method = RequestMethod.POST)
    public R update(@ApiParam(name ="sysRoleMenu",required = true,value = "修改后的实体类") @RequestBody SysRoleMenuEntity sysRoleMenu)throws Exception{

            boolean result = sysRoleMenuService.updateById(sysRoleMenu);
            if(result){
                return R.ok("修改成功");
            }else{
                return R.fail("修改失败");
            }
    }

    /**
    * 描述:   删除方法
    * author: Larry
    * date: 2020-05-27
    */
    @ApiOperation(value="删除",response = CommonOkResponseEntity.class)
    @RequestMapping(value="/delete", method=RequestMethod.POST)
    public R delete(@ApiParam(name ="sysRoleMenu",required = true,value = "根据类中id删除") @RequestBody SysRoleMenuEntity sysRoleMenu){
            boolean result = sysRoleMenuService.removeById(sysRoleMenu);
            if(result){
                return R.ok("删除成功");
            }else{
                return R.fail("删除失败");
            }
    }

}
