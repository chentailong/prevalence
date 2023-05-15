package com.lon;

import lombok.extern.slf4j.Slf4j;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.transaction.annotation.Transactional;

/**
 * 应用程序启动入口
 *
 * @author ctl
 * @date 2022/09/09
 */

@Slf4j
@SpringBootApplication
@ServletComponentScan
@Transactional(rollbackFor = Exception.class)
@EnableTransactionManagement
@EnableCaching  //开启SpringCache注解
@MapperScan(basePackages = "com.lon.mapper")
@EnableConfigurationProperties
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
        log.info("项目成功起飞! 坐稳咯");
    }

}
