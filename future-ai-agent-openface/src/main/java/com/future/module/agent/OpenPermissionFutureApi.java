package com.future.module.agent;

import com.future.common.base.ActionResult;
import com.future.feign.utils.FeignName;
import com.future.module.agent.model.user.UserCrForm;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


/**
 * Future用户权限信息
 *
 * @author Future Platform Group
 * @version V4.0.0
 * @copyright 直方信息科技有限公司
 * @date 2021-03-24
 */
@FeignClient(name = FeignName.PERMISSION_SERVER_NAME)
public interface OpenPermissionFutureApi
{
    /**
     * 用户注册
     * @return
     */
    @PostMapping("/Users")
    ActionResult<String> register(@RequestBody UserCrForm userCrForm);

    @GetMapping("/Users/getAccountIsExist/{account}")
    Boolean getAccountIsExist(@PathVariable("account") String account);
}
