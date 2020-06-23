package com.springboot.admin.sys.sysuserrole.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.springboot.admin.sys.sysuserrole.entity.SysUserRoleEntity;
import com.springboot.admin.sys.sysuserrole.entity.swagger.SysUserRoleSwaggerListEntity;
import com.springboot.admin.sys.sysuserrole.entity.swagger.SysUserRoleSwaggerPageBaseEntity;
import com.springboot.admin.sys.sysuserrole.service.SysUserRoleService;
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
@Api(value = "用户角色关联",tags = {"用户角色关联模块"})
@RequestMapping(value="/sysUserRole")
public class SysUserRoleController extends BaseController {

    @Autowired
    private SysUserRoleService sysUserRoleService;

    /**
    * 描述:   分页查询方法
    * author: Larry
    * date: 2020-05-27
    */
    @RequestMapping(value="/page/list",method = RequestMethod.POST)
    @ApiOperation(value="分页查询")
    @ApiOperationSupport(
        responses = @DynamicResponseParameters(properties = {
                 @DynamicParameter(value = "查询结果",name = "responsePageEnity",dataTypeClass = SysUserRoleSwaggerPageBaseEntity.class)
         })
    )
    public R pageList(@ApiParam(name ="sysUserRole",required = true,value = "根据传入的实体类查询") @RequestBody SysUserRoleEntity sysUserRole){
        QueryWrapper<SysUserRoleEntity> queryWrapper = new QueryWrapper(sysUserRole);
        queryWrapper.orderByDesc("id");
        IPage<SysUserRoleEntity> page = sysUserRoleService.page(new Page(sysUserRole.getCurrentPage(),sysUserRole.getPageSize()),queryWrapper);

        return R.ok(page);
     }


    /**
    * 描述:   查询list集合方法
    * author: Larry
    * date: 2020-05-27
    */
    @ApiOperation(value="集合查询",response = SysUserRoleEntity.class)
    @RequestMapping(value="/list",method = RequestMethod.POST)
    @ApiOperationSupport(
        responses = @DynamicResponseParameters(properties = {
                @DynamicParameter(value = "查询结果",name = "responseListEnity",dataTypeClass = SysUserRoleSwaggerListEntity.class)
        })
    )
    public R list(@ApiParam(name ="sysUserRole",required = true,value = "根据传入的实体类查询") @RequestBody SysUserRoleEntity sysUserRole){
        QueryWrapper<SysUserRoleEntity> queryWrapper = new QueryWrapper(sysUserRole);
        queryWrapper.orderByDesc("id");
        List list = sysUserRoleService.list(queryWrapper);
        return R.ok(list);
     }


    /**
    * 描述: 根据id查询方法
    * author: Larry
    * date: 2020-05-27
    */
    @ApiOperation(value="根据id查询",response = SysUserRoleEntity.class)
    @ApiOperationSupport(
        responses = @DynamicResponseParameters(properties = {
            @DynamicParameter(value = "响应信息,出错显示错误的具体信息",name = "msg"),
            @DynamicParameter(value = "响应状态值,200为成功",name = "code"),
            @DynamicParameter(value = "实体类",name = "data",dataTypeClass = SysUserRoleEntity.class),
            @DynamicParameter(value = "是否失败",name = "error"),
        })
    )
    @RequestMapping(value="/queryById",method = RequestMethod.POST)
    public R selectById(@ApiParam(name ="sysUserRole",required = true,value = "根据类中id查询") @RequestBody SysUserRoleEntity sysUserRole){
        sysUserRole = sysUserRoleService.getById(sysUserRole.getId());
        return R.ok(sysUserRole);
    }

    /**
    * 描述:  保存方法
    * author: Larry
    * date: 2020-05-27
    */
    @ApiOperation(value="保存",response = CommonOkResponseEntity.class)
    @RequestMapping(value="/save",method = RequestMethod.POST)
    public R save(@ApiParam(name ="sysUserRole",required = true,value = "要保存的实体类") @RequestBody SysUserRoleEntity sysUserRole){

        boolean result = sysUserRoleService.save(sysUserRole);
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
    public R update(@ApiParam(name ="sysUserRole",required = true,value = "修改后的实体类") @RequestBody SysUserRoleEntity sysUserRole)throws Exception{

            boolean result = sysUserRoleService.updateById(sysUserRole);
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
    public R delete(@ApiParam(name ="sysUserRole",required = true,value = "根据类中id删除") @RequestBody SysUserRoleEntity sysUserRole){
            boolean result = sysUserRoleService.removeById(sysUserRole);
            if(result){
                return R.ok("删除成功");
            }else{
                return R.fail("删除失败");
            }
    }

}
