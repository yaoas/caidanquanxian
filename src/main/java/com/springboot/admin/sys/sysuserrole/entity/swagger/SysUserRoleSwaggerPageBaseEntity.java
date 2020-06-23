package com.springboot.admin.sys.sysuserrole.entity.swagger;

import com.springboot.admin.sys.sysuserrole.entity.SysUserRoleEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;
/**
* 描述: 作为swagger参数的类
* @author: Larry
* date: 2020-05-27
*/

@Data
@ApiModel("分页实体类")
public class SysUserRoleSwaggerPageBaseEntity {
    @ApiModelProperty(value = "响应状态值,200为成功",example = "200")
    private Integer code;
    @ApiModelProperty(value = "响应信息,出错显示错误的具体信息",example = "成功")
    private String msg;
    @ApiModelProperty(value = "分页实体类",example = "{}")
    private SysUserRoleSwaggerPageEntity data;
    @ApiModelProperty(value = "是否失败",example = "false")
    private boolean error;
}

@ApiModel("订单分页实体类")
@Data
class SysUserRoleSwaggerPageEntity {
    @ApiModelProperty(value = "当前页码数",example = "1")
    String curent;
    @ApiModelProperty(value = "总页码数",example = "4")
    String pages;
    @ApiModelProperty(value = "每页显示记录数",example = "10")
    String size;
    @ApiModelProperty(value = "总记录数",example = "40")
    String total;
    @ApiModelProperty(value = "所有记录信息")
    List<SysUserRoleEntity> records;
}