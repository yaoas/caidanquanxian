package com.springboot.admin.sys.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.springboot.admin.sys.entity.Role;
import com.springboot.admin.sys.entity.User;
import com.springboot.admin.sys.service.IRoleService;
import com.springboot.admin.sys.service.IUserService;
import com.springboot.admin.sys.sysuserrole.entity.SysUserRoleEntity;
import com.springboot.admin.sys.sysuserrole.service.SysUserRoleService;
import com.springboot.common.base.controller.BaseController;
import com.springboot.common.utils.R;
import io.swagger.annotations.*;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 管理员表
 *
 * @author hs
 * @email 457030599@qq.com
 * @date 2018-10-07 18:03:20
 */
@Api(value = "UserController", tags = {"用户接口"})
@RequestMapping("/sys/user")
@RestController
public class UserController extends BaseController {

    @Autowired
    private IUserService userService;
    @Autowired
    private SysUserRoleService sysUserRoleService;
    @Autowired
    private IRoleService roleService;

    @Transactional
    @ApiOperation(value = "分页列表")
    @RequestMapping(value = "/page/list", method = RequestMethod.POST)
    public R pageList(@RequestBody User user) {
        List<Long> roleIds = user.getRoleIds();
        QueryWrapper qw = new QueryWrapper<User>();
        if (roleIds != null && roleIds.size() > 0) {
            Long roleId = roleIds.get(0);
            List<SysUserRoleEntity> list = sysUserRoleService.list(new QueryWrapper<SysUserRoleEntity>().lambda().eq(SysUserRoleEntity::getRoleId, roleId));
            List<Long> collect = list.stream().map(item -> item.getUserId()).collect(Collectors.toList());
            if (collect.size() > 0) {
                qw.in("id", collect);
            }else{
                IPage<User> page=new Page<>();
                return   R.ok(page);
            }
        }
        if (StringUtils.isNotBlank(user.getAccount())) {
            qw.like("account", user.getAccount());
        }
        if (StringUtils.isNotBlank(user.getName())) {
            qw.like("name", URLEncoder.encode(user.getName()));
        }
        if (StringUtils.isNotBlank(user.getPhone())) {
            qw.like("phone", user.getPhone());
        }
        if (StringUtils.isNotBlank(user.getUserType())) {
            qw.like("user_type", user.getUserType());
        }
        qw.select("id", "account", "name", "email", "phone", "status", "user_type", "money_element", "last_login_time", "create_time");
        qw.notIn("id", "1");
        qw.orderByDesc("create_time");
        IPage<User> page = userService.page(new Page(user.getCurrentPage(), user.getPageSize()), qw);
        if (page.getRecords().size()==0&&page.getTotal()!=0){
            page = userService.page(new Page(user.getCurrentPage()-1, user.getPageSize()), qw);
        }
        page.getRecords().stream().forEach(item -> {
            List<Long> collect = sysUserRoleService.
                    list(new QueryWrapper<SysUserRoleEntity>().lambda().eq(SysUserRoleEntity::getUserId, item.getId())).
                    stream().map(a -> a.getRoleId()).collect(Collectors.toList());
            if (collect.size() != 0) {
                List<Role> list = roleService.list(new QueryWrapper<Role>().lambda().in(Role::getId, collect).select(Role::getRoleName, Role::getId));
                item.setRoleNameString(list.stream().map(b -> b.getRoleName()).collect(Collectors.joining(",")));
                item.setRoleIds(list.stream().map(b -> b.getId()).collect(Collectors.toList()));
            }
        });
        return R.ok(page);
    }

    @ApiOperation("保存用户")
    @RequestMapping(value = "/save", method = RequestMethod.POST)
    public Object save(@Valid @RequestBody User user) throws UnsupportedEncodingException {
        boolean save = userService.save(user);
        if(save){
            return R.ok();
        }else {
            return R.fail();
        }
    }

    @RequestMapping(value = "/delete", method = RequestMethod.POST)
    @ApiOperation("删除用户")
    @ApiOperationSupport(params = @DynamicParameters(name = "queryMap", properties = {
            @DynamicParameter(name = "userId", value = "要删除的用户id", example = "112", required = true, dataTypeClass = Long.class)
    }))
    public R delete(@RequestBody Map<String, Long> queryMap) {
        Long userId = queryMap.get("userId");
        boolean result = userService.removeById(userId);
        sysUserRoleService.remove(new QueryWrapper<SysUserRoleEntity>().lambda().eq(SysUserRoleEntity::getUserId, userId));
        if (result) {
            return R.ok("删除成功");
        } else {
            return R.fail("删除失败");
        }
    }

    @ApiOperation("用户详情")
    @RequestMapping(value = "/info", method = RequestMethod.POST)
    public R info(@RequestBody User user) {
        User byId = userService.getById(user.getId());
        return R.ok(byId);
    }

    /**
     * @return com.pm.background.common.utils.R
     * @author Larry
     * @date 2020/5/14 0014 9:52
     * @description 图片上传方法
     **/
    @ApiOperation("上传头像图片接口")
    @ApiOperationSupport(
            responses = @DynamicResponseParameters(properties = {
                    @DynamicParameter(value = "图片本地存储路径", name = "oldPath"),
                    @DynamicParameter(value = "图片访问路径", name = "newPath")
            })
    )
    @RequestMapping(value = "/upload", method = RequestMethod.POST)
    public R upload(MultipartFile file) {
        return savePic(file);
    }


}