package com.springboot.admin.sys.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.springboot.admin.sys.entity.Menu;
import com.springboot.admin.sys.service.IMenuService;
import com.springboot.common.base.controller.BaseController;
import com.springboot.common.utils.R;
import com.springboot.common.utils.ToolUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;
import java.util.Map;

/**
 * 菜单管理
 *
 * @author hs
 * @email 457030599@qq.com
 * @date 2018-10-08 16:33:17
 */
@Api(value="MenuController",tags={"菜单接口"})
@RestController
@RequestMapping("/sys/menu")
public class MenuController extends BaseController {

    @Autowired
    private IMenuService menuService;

    /**
     * 导航菜单
     */
    @ApiOperation(value = "导航菜单")
    @RequestMapping(value = "/nav",method = RequestMethod.POST)
    public R nav(){
        List<Menu> menuList = menuService.getUserMenuList(1L);
        return R.ok(menuList);
    }

    //分页
    @ApiOperation("分页列表")
    @RequestMapping(value = "/page/list",method = RequestMethod.POST)
    public R pageList(@ApiParam("菜单实体类") @RequestBody Menu menu){
        QueryWrapper<Menu> menuQueryWrapper = new QueryWrapper<>();
        if (menu.getName()!=null){
            menuQueryWrapper.lambda().like(Menu::getName,menu.getName());
        }
        IPage page = menuService.page(new Page(menu.getCurrentPage(),menu.getPageSize()),menuQueryWrapper);
        return R.ok(page);
    }
    @ApiOperation("列表")
    @RequestMapping(value = "/list",method = RequestMethod.POST)
    public R list(@RequestBody Menu menu){
        QueryWrapper qw = new QueryWrapper<Menu>();
        List<Menu> menuList = menuService.list(qw);
        return R.ok(menuList);
    }
    @ApiOperation("保存或修改操作")
    @RequestMapping(value = "/save",method = RequestMethod.POST)
    public R save(@Valid @RequestBody Menu menu){
        if(ToolUtil.isEmpty(menu.getParentId())){
            menu.setParentId(0L);
        }
        if(menuService.saveOrUpdate(menu)){
            return R.ok();
        }else {
            return R.fail();
        }
    }

    @ApiOperation("详情")
    @RequestMapping(value = "/info",method = RequestMethod.POST)
    public R info(@RequestBody Map<String,Long> map){
        Long menuId = map.get("menuId");
        Menu menu = menuService.getById(menuId);
        if(ToolUtil.isEmpty(menu)){
            return R.fail("找不到该菜单");
        }
        if(menu.getParentId()!=0){
            Menu pmenu = menuService.getById(menu.getParentId());
            menu.setPname(pmenu.getName());
        }
        else {
            menu.setPname("顶级");
        }
        return R.ok(menu);
    }
    @ApiOperation("删除")
    @RequestMapping(value = "/delete",method = RequestMethod.POST)
    public R delete(@RequestBody Map<String,Long> map){
        Long menuIds=map.get("id");
        if (menuIds==null){
            return  R.fail("删除失败");
        }
            Integer count = menuService.count(new QueryWrapper<Menu>().lambda().eq(Menu::getParentId,menuIds));
            if(count>0){
                return R.fail("删除失败，请先删除菜单关联的子菜单");
            }
        //清除缓存
//        redisUtil.clearCache();
        menuService.removeById(menuIds);
        return R.ok();
    }

    /**
     * 树形菜单
     */
    @ApiOperation(value = "树形菜单")
    @RequestMapping(value = "/tree/menu",method = RequestMethod.POST)
    public R treeMenu(@RequestBody Menu menu){
        return R.ok(menuService.treeMenuList(0L,menu));
    }

}