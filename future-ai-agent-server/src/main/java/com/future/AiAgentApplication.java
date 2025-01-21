package com.future;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

/**
 * ai agent服务启动类
 * @author Future Platform Group
 * @version V4.2.1
 * @copyright 直方信息科技有限公司
 * @date 2024-11-16
 */
@SpringBootApplication(scanBasePackages = {"com.future"})
@EnableFeignClients
@MapperScan(basePackages = { "com.future.module.agent.mapper", "com.future.*.mapper"})
public class AiAgentApplication {

    public static void main(String[] args) {
        SpringApplication.run(AiAgentApplication.class, args);
        System.out.println("AgentApplication启动成功");
    }

}
