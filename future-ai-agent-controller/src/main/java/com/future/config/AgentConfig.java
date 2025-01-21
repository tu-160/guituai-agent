package com.future.config;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

@Data
@RefreshScope
@Component
public class AgentConfig {


    @Value("${ai.agent.reasoning}")
    private String reasoning_host;
}
