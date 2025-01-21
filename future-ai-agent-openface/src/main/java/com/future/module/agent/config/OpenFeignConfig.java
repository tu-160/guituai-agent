package com.future.module.agent.config;

import com.future.common.util.UserProvider;
import com.future.reids.util.RedisUtil;
import feign.RequestInterceptor;
import feign.RequestTemplate;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Set;

@Configuration
@EnableFeignClients(basePackages = "com.future.module.agent")
public class OpenFeignConfig {

    private static RedisUtil redisUtil;

    public OpenFeignConfig(RedisUtil redisUtil ) {
        OpenFeignConfig.redisUtil = redisUtil;
    }

    @Bean
    public RequestInterceptor authorizationRequestInterceptor() {
        return template -> {
            // 移除前端带入的Authorization
            template.removeHeader("Authorization");
            String redisKey = "agent_"+UserProvider.getLoginUserId();
            Object token = redisUtil.getString(redisKey);
            // 拿缓存里面的api_token
            template.header("Authorization", "Bearer " + token);
        };
    }
}