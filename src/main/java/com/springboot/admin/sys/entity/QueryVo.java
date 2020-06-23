package com.springboot.admin.sys.entity;

import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.util.Date;

/**
 * @author Larry
 * @date 2020/5/18 0018 9:21
 * @description  用来查询趋势概览的实体类
 * 需要段 1起始时间 2结束时间 3类型
 */
@Data
public class QueryVo implements Serializable {
    private static final long serialVersionUID = 123456789L;
    //起始时间
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date startTime;
    //结束时间
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date endTime;
}
