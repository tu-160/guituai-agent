package com.future.module.agent;

import com.future.common.base.ActionResult;
import com.future.feign.utils.FeignName;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Map;


/**
 * Future用户信息
 *
 * @author Future Platform Group
 * @version V4.0.0
 * @copyright 直方信息科技有限公司
 * @date 2021-03-24
 */
@FeignClient(name = FeignName.OAUTH_SERVER_NAME)
public interface OpenUserFutureApi {

    @PostMapping("/api/oauth/login")
    ActionResult login(@RequestParam Map<String, String> parameters);
}
