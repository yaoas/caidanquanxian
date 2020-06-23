package com.springboot.common.quartzJob;

import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.stereotype.Component;


/***
 * 自定义定时任务
 */
@Component
public class TestQuartz2 implements BaseTaskJob {
    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {
        System.out.println("job>>>>>>>  " + 2);
    }
}
