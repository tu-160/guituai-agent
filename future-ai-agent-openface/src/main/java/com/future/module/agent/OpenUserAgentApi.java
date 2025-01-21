package com.future.module.agent;

import com.alibaba.fastjson.JSONObject;
import com.future.module.agent.utils.AgentApiResult;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;


/**
 * 获取用户信息Api
 *
 * @author Future Platform Group
 * @version V4.0.0
 * @copyright 直方信息科技有限公司
 * @date 2021-03-24
 */
@FeignClient(name = "userAgentApi", url = "${ai.agent.host.url}")
public interface OpenUserAgentApi {

    /**
     * 用户注册
     * @param nickname
     * @return
     */
    @PostMapping("/api/v1/user/register")
    AgentApiResult register(@RequestBody JSONObject jsonObject);


    @PostMapping("/api/v1/user/emailIsExist")
    AgentApiResult emailIsExist(@RequestBody JSONObject jsonObject);

}
