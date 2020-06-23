package com.springboot.common.comment;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author Larry
 * @date 2020/5/22 0022 10:21
 * @description
 */
@ApiModel("通用返回实体")
@Data
public class CommonOkResponseEntity {
    @ApiModelProperty(value = "响应状态值,200为成功",example = "200")
    private Integer code;
    @ApiModelProperty(value = "响应信息,出错显示错误的具体信息",example = "成功")
    private String msg;
    @ApiModelProperty(value = "响应结果",example = "{}")
    private String data;
    @ApiModelProperty(value = "是否失败",example = "false")
    private boolean error;
}
