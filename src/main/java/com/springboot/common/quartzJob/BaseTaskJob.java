package com.springboot.common.quartzJob;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

/**
 * @author xujingyang
 * @Description: 基础任务调度taskJob接口
 * @date 2018/5/30
 * <p>
 */
public interface BaseTaskJob extends Job {
    void execute(JobExecutionContext context)
            throws JobExecutionException;
}
