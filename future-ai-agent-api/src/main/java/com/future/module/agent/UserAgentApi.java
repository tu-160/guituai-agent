package com.future.module.agent;

import com.future.module.agent.entity.UserAgentEntity;
import com.future.module.agent.fallback.UserAgentApiFallback;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import com.future.feign.utils.FeignName;


/**
 * 获取用户信息Api
 *
 * @author Future Platform Group
 * @version V4.0.0
 * @copyright 直方信息科技有限公司
 * @date 2021-03-24
 */
@FeignClient(name = "future-agent", fallback = UserAgentApiFallback.class, path = "/api")
public interface UserAgentApi {

    /**
     * 用户注册
     * @param nickname
     * @return
     */
    @PostMapping("/v1/user/register")
    Object register(@RequestParam("nickname") String nickname,@RequestParam("password") String password,@RequestParam("email") String email);
}
