package com.springboot.common.quartzJob;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

/**
 * 继承Application接口后项目启动时会按照执行顺序执行run方法
 * 通过设置Order的value来指定执行的顺序
 */
@Slf4j
//放开注释，开启定时
@Component
public class StartService implements CommandLineRunner {


    @Override
    public void run(String... args) throws Exception {

    }
}
